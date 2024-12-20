package com.rootlab.practcomm.reservation;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DynamicTask implements Runnable {

	
	
	private String taskType;


	@Override
	public void run() {
		switch (taskType) {
		case "w":
			break;
		case "v":
			break;
		case "i":
			break;
		default:
			break;

		}

	}

	private void saveWeb() {

	}

	private void saveVideo() {

	}

	private void saveImage() {

	}

}
