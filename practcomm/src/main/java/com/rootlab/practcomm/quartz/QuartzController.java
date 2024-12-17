package com.rootlab.practcomm.quartz;

import org.springframework.web.bind.annotation.*;

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
    public String removeJob(@RequestParam String jobName, 
                            @RequestParam int id) throws Exception {
        schedulerService.removeJob(jobName, jobName.charAt(0)+"");
        return "Job removed successfully.";
    }
    
    
    // 작업 일시 중지
    @PostMapping("/pause")
    public String pauseJob(@RequestParam String jobName,
                           @RequestParam String groupName) throws Exception {
        schedulerService.pauseJob(jobName, groupName);
        return "Job paused successfully.";
    }

    // 작업 재실행
    @PostMapping("/resume")
    public String resumeJob(@RequestParam String jobName,
                            @RequestParam String groupName) throws Exception {
        schedulerService.resumeJob(jobName, groupName);
        Reservation r = basicSetting(jobName);
        //r.setCron(cronExpression);
        
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