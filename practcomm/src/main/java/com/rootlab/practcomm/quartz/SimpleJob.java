package com.rootlab.practcomm.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Runnable task = (Runnable) context.getJobDetail().getJobDataMap().get("task");
        if (task != null) {
            task.run();
        }
    }
}