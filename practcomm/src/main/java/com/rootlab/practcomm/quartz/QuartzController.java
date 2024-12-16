package com.rootlab.practcomm.quartz;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartz")
public class QuartzController {

    private final QuartzSchedulerService schedulerService;

    public QuartzController(QuartzSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
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
        
        return "Job added successfully.";
    }

    @DeleteMapping("/remove")
    public String removeJob(@RequestParam String jobName, 
                            @RequestParam String groupName) throws Exception {
        schedulerService.removeJob(jobName, groupName);
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
        return "Job resumed successfully.";
    }
    
    
}