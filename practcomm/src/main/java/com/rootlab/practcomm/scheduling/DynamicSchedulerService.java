package com.rootlab.practcomm.scheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DynamicSchedulerService {
	
	private final TaskScheduler taskScheduler;
	private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
	
	public DynamicSchedulerService() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.initialize();
        this.taskScheduler = threadPoolTaskScheduler;
    }

    public void addTask(String taskId, Runnable task, String cronExpression) {
        removeTask(taskId); // 기존 Task 제거
        ScheduledFuture<?> future = ((ThreadPoolTaskScheduler) taskScheduler)
                .schedule(task, new CronTrigger(cronExpression));
        scheduledTasks.put(taskId, future);
    }

    public void removeTask(String taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            future.cancel(false); // Task 취소
            scheduledTasks.remove(taskId);
        }
    }

    public boolean isTaskRunning(String taskId) {
        return scheduledTasks.containsKey(taskId);
    }
	

}
