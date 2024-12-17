package com.rootlab.practcomm.quartz;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rootlab.practcomm.reservation.Reservation;
import com.rootlab.practcomm.reservation.service.ReservationService;

@RestController
@RequestMapping("/quartz")
public class QuartzController {

    private final QuartzSchedulerService schedulerService;
    private final ReservationService reservationService;
    
    
    public QuartzController(QuartzSchedulerService schedulerService, ReservationService reservationService) {
        this.schedulerService = schedulerService;
        this.reservationService = reservationService;
    }

    //http://localhost:8080/community/quartz/add?jobName=테스트용&cronExpression=*/10 * * * * ? *
    @GetMapping("/add")
    public String addJobget(@RequestParam(value = "jobName") String jobName,
    						@RequestParam(value = "cronExpression") String cronExpression) 
    								throws Exception {
    	
    	String groupName = jobName.charAt(0) + "";
    	System.out.println(jobName + cronExpression);
//        schedulerService.addJob(jobName, groupName, cronExpression, 
//                () -> System.out.println("Task " + jobName + " is running."));
        return "Job added successfully.";
    }
    
    @PostMapping("/add")
    public String addJob(@RequestParam(value = "jobName") String jobName,
			@RequestParam(value = "cronExpression") String cronExpression) throws Exception {
    	
    	String groupName = jobName.charAt(0) + "";
    	System.out.println(jobName + cronExpression);
    	
        schedulerService.addJob(jobName, groupName, cronExpression, 
                () -> System.out.println("Task " + jobName + " is running."));
        
        
        
        String keyword = jobName.substring(jobName.indexOf('|')+1);
        
        Reservation r = basicSetting(jobName);
        r.setCron(cronExpression);
        reservationService.save(r);
        
        return "Job added successfully.";
    }

    @DeleteMapping("/remove")
    public String removeJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "id") int id) throws Exception {
   
        schedulerService.removeJob(jobName, jobName.charAt(0)+"");
        Reservation r = reservationService.selectById(id);
        r.setUse_yn('n'+"");
        reservationService.update(r);
        return "Job removed successfully.";
    }
    
    
    // 작업 일시 중지
    @PostMapping("/pause")
    public String pauseJob(@RequestParam(value = "jobName") String jobName, 
    					   @RequestParam(value = "id") int id) throws Exception {
    	
    	String groupName = jobName.charAt(0)+"";
        schedulerService.pauseJob(jobName, groupName);
        Reservation r = reservationService.selectById(id);
        r.setStatus("중지 됨");
        reservationService.update(r);
        
        
        return "Job paused successfully.";
    }

    // 작업 재실행
    @PostMapping("/resume")
    public String resumeJob(@RequestParam(value = "jobName") String jobName, 
			   				@RequestParam(value = "id") int id) throws Exception {
    	String groupName = jobName.charAt(0)+"";
        schedulerService.resumeJob(jobName, groupName);
        Reservation r = reservationService.selectById(id);
        r.setStatus("실행 중");
        reservationService.update(r);
        
        return "Job resumed successfully.";
    }
    
    
    private Reservation basicSetting(String jobName) {
    	
    	
    	String keyword = jobName.substring(jobName.indexOf('|')+1);
        Reservation r = new Reservation();
        switch(jobName.charAt(0)) {
        case 'w' : r.setType("웹 검색"); break;
        case 'v' : r.setType("비디오 검색"); break;
        case 'i' : r.setType("이미지 검색"); break;
        default :  r.setType("웹 검색"); break;
        }
        switch(jobName.charAt(1)) {
        case 'a' : r.setSort("정확도 순"); break;
        case 'r' : r.setSort("최신 순"); break;
        default : r.setSort("정확도 순"); break;
        }
        r.setKeyword(keyword);
        r.setJobname(jobName);
        
    	return r;
    }
    
}