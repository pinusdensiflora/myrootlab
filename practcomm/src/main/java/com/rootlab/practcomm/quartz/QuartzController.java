package com.rootlab.practcomm.quartz;

import java.util.Map;

import org.quartz.CronExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rootlab.practcomm.img.KakaoImg;
import com.rootlab.practcomm.reservation.dto.Reservation;
import com.rootlab.practcomm.reservation.service.ImgDTOService;
import com.rootlab.practcomm.reservation.service.ReservationService;
import com.rootlab.practcomm.reservation.service.VideoDTOService;
import com.rootlab.practcomm.reservation.service.WebDTOService;
import com.rootlab.practcomm.searchParam.KakaoSearchParam;
import com.rootlab.practcomm.video.KakaoVideo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quartz")
@RequiredArgsConstructor //생성자 자동생성
public class QuartzController {

	private final QuartzSchedulerService schedulerService;
	private final ReservationService reservationService;

	// private final WebMetaService webMetaService;
	private final WebDTOService webDTOService;
	private final VideoDTOService videoDTOService;
	private final ImgDTOService imgDTOService;
	
	KakaoSearchParam kakaoSearch = new KakaoSearchParam();
	KakaoVideo kakaoVideo = new KakaoVideo();
	KakaoImg kakaoImg = new KakaoImg();
	
	Gson gson = new Gson(); // 기본 생성

//	public QuartzController(QuartzSchedulerService schedulerService, ReservationService reservationService,
//			WebMetaService webMetaService) {
//		this.schedulerService = schedulerService;
//		this.reservationService = reservationService;
//		this.webMetaService = webMetaService;
//	}

	private Runnable webSchedule(String jobname, int rid) {
		String sort = jobname.charAt(1)=='r'? "recency" : "accuracy";
		String keyword = jobname.substring(jobname.indexOf('|') + 1);
		return () -> {
			System.out.println("WEB Schedule " + jobname + " is running...");
			String resultString = kakaoSearch.searchForQuartz(keyword, sort);
			String prettyJson = gson.toJson(gson.fromJson(resultString, Object.class));
			prettyJson = prettyJson.replaceAll("\\\\u003cb\\\\u003e", "");// <b>
			prettyJson = prettyJson.replaceAll("\\\\u003c/b\\\\u003e", "");// </b>

			Map<String, Object> mapData = gson.fromJson(prettyJson, new TypeToken<Map<String, Object>>() {
			}.getType());

			webDTOService.r_saveList(mapData.get("documents"), rid);
		};

	}
	
	private Runnable videoSchedule(String jobname, int rid) {
		String sort = jobname.charAt(1)=='r'? "recency" : "accuracy";
		String keyword = jobname.substring(jobname.indexOf('|') + 1);
		return () -> {
			System.out.println("VIDEO Schedule " + jobname + " is running...");
			String resultString = kakaoVideo.videoForQuartz(keyword, "accuracy");
			String prettyJson = gson.toJson(gson.fromJson(resultString, Object.class));

			Map<String, Object> mapData = gson.fromJson(prettyJson, new TypeToken<Map<String, Object>>() {
			}.getType());
			
			videoDTOService.r_saveList(mapData.get("documents"),rid);
			
		};
	}
	
	private Runnable imgSchedule (String jobname, int rid) {
		String sort = jobname.charAt(1)=='r'? "recency" : "accuracy";
		String keyword = jobname.substring(jobname.indexOf('|') + 1);
		
		return () -> {
			System.out.println("IMG Schedule " + jobname + " is running...");
			String resultString = kakaoImg.imgForQuartz(keyword, "accuracy");
			String prettyJson = gson.toJson(gson.fromJson(resultString, Object.class));

			Map<String, Object> mapData = gson.fromJson(prettyJson, new TypeToken<Map<String, Object>>() {
			}.getType());
			
			imgDTOService.r_saveList(mapData.get("documents"),rid);
			
		};
		
	}

	// http://localhost:8080/community/quartz/add?jobName=테스트용&cronExpression=*/10 *
	// * * * ? *
	@GetMapping("/add")
	public String addJobget(@RequestParam(value = "jobName") String jobName,
			@RequestParam(value = "cronExpression") String cronExpression) throws Exception {

		String groupName = jobName.charAt(0) + "";
		System.out.println(jobName + cronExpression);
//        schedulerService.addJob(jobName, groupName, cronExpression, 
//                () -> System.out.println("Task " + jobName + " is running."));
		return "Job added successfully.";
	}

	@PostMapping("/add")
	public ResponseEntity<String> addJob(@RequestParam(value = "jobName") String jobName,
			@RequestParam(value = "cronExpression") String cronExpression) throws Exception {

		
//		try {
//            // 크론 표현식 검증
//            if (!CronExpression.isValidExpression(cronExpression)) {
//                throw new RuntimeException("Invalid Cron Expression: " + cronExpression);
//            } else {
//                System.out.println("Valid Cron Expression!");
//            }
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
		
		
		if (!CronExpression.isValidExpression(cronExpression)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("크론 표현식이 올바르지 않습니다.");
		} 
		
		String groupName = jobName.charAt(0) + "";
		System.out.println(jobName + cronExpression);

		String keyword = jobName.substring(jobName.indexOf('|') + 1);

	
//      if (ㅁㄴㅇㄻㄴㅇㄻㄴ) {
//          // 이미 처리된 요청
//          return ResponseEntity
//                  .status(HttpStatus.BAD_REQUEST)
//                  .body("이미 작동중인 예약입니다.");
//      }
		
		Reservation r = basicSetting(jobName);
		r.setCron(cronExpression);
		reservationService.save(r);

//        schedulerService.addJob(jobName, groupName, cronExpression, 
//                () -> System.out.println("Task " + jobName + " is running."));
		switch (groupName) {
		case "w": 
			schedulerService.addJob(jobName, groupName, cronExpression, webSchedule(jobName, r.getId()));
			break;
		case "v":
			schedulerService.addJob(jobName, groupName, cronExpression, videoSchedule(jobName, r.getId()));
			break;
		case "i":
			schedulerService.addJob(jobName, groupName, cronExpression, imgSchedule(jobName, r.getId()));
			break;
		default :
			//schedulerService.addJob(jobName, groupName, cronExpression, webSchedule(jobName, r.getId()));
			break;
			
		}
		

		// return "Job added successfully.";

		// 요청 수락 및 처리 진행
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("예약이 생성되었습니다.");
	}

	@DeleteMapping("/remove")
	public String removeJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "id") int id)
			throws Exception {

		schedulerService.removeJob(jobName, jobName.charAt(0) + "");
		Reservation r = reservationService.selectById(id);
		r.setStatus_code(200);// 중지 됨
		r.setUse_yn("n");
		reservationService.update(r);
		return "Job removed successfully.";
	}

	// 작업 일시 중지
	@PostMapping("/pause")
	public String pauseJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "id") int id)
			throws Exception {

		String groupName = jobName.charAt(0) + "";
		schedulerService.pauseJob(jobName, groupName); // # pause와 resume은 지난 시간에 대한 처리를 한다.
		// schedulerService.removeJob(jobName, jobName.charAt(0)+"");//## 아예 삭제를 한다. 하지만
		// db에서는 중지된 것 처럼

		Reservation r = reservationService.selectById(id);
		r.setStatus_code(200);// 중지 됨
		reservationService.update(r);

		return "Job paused successfully.";
	}

	// 작업 재실행
	@PostMapping("/resume")
	public String resumeJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "id") int id)
			throws Exception {
		String groupName = jobName.charAt(0) + "";
		schedulerService.resumeJob(jobName, groupName); // # pause와 resume은 지난 시간에 대한 처리를 한다.

		Reservation r = reservationService.selectById(id);
		r.setStatus_code(100);// 실행 중
		reservationService.update(r);

//        schedulerService.addJob(jobName, groupName, r.getCron(), 
//                () -> System.out.println("Task " + jobName + " is running.")); //## 아예 새로 만든다

		return "Job resumed successfully.";
	}

	private Reservation basicSetting(String jobName) {

		String keyword = jobName.substring(jobName.indexOf('|') + 1);
		Reservation r = new Reservation();
		switch (jobName.charAt(0)) {
		case 'w':
			r.setType_code(100);
			break;// 웹
		case 'v':
			r.setType_code(200);
			break;// 비디오
		case 'i':
			r.setType_code(300);
			break;// 이미지
		default:
			r.setType_code(100);
			break;
		}
		switch (jobName.charAt(1)) {
		case 'a':
			r.setSort("정확도 순");
			break;
		case 'r':
			r.setSort("최신 순");
			break;
		default:
			r.setSort("정확도 순");
			break;
		}
		r.setKeyword(keyword);
		r.setJobname(jobName);
		r.setStatus_code(100);

		return r;
	}

}