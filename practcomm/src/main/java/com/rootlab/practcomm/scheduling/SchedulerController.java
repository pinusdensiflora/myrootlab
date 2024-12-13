package com.rootlab.practcomm.scheduling;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {

	private final DynamicSchedulerService schedulerService;

    public SchedulerController(DynamicSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    //http://localhost:8080/community/scheduler/add?taskId=testTask&cronExpression=*/10 * * * * *
    //http://localhost:8080/community/scheduler/add?taskId=testTask 로 접속 또는 curl 을 사용해도됨
//    @GetMapping("/add")
//    @ResponseBody //일단 post에서 get으로 변경했는데 반환 불편해서 일단 추가
//    //public String addTask(@RequestParam String taskId, @RequestParam String cronExpression) {
//    public String addTask(@RequestParam("taskId") String taskId, @RequestParam("cronExpression") String cronExpression) {
//    	//String cronExpression = "*/5 * * * * *";//5초마다 실행 나중에 파라미터로 받음
//    	//System.out.println("크론식 : "+cronExpression);
//        schedulerService.addTask(taskId, () -> System.out.println("Task " + taskId + " 실행 중"), cronExpression);
//        return "Task " + taskId + "가 추가되었습니다.";
//    }
    
    
    
    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestParam(value = "taskId") String taskId, @RequestParam(value = "cronExpression") String cronExpression) {
        System.out.println(taskId + cronExpression);
    	
    	schedulerService.addTask(taskId, () -> System.out.println("Task " + taskId + " 실행 중"), cronExpression);
        return ResponseEntity.ok("ok");
    }


    //@DeleteMapping("/remove")
    @GetMapping("/remove")
    @ResponseBody//나중에 지우기
    public String removeTask(@RequestParam("taskId") String taskId) {
        schedulerService.removeTask(taskId);
        System.out.println(taskId + "제거");
        return "Task " + taskId + "가 제거되었습니다.";
    }
}
