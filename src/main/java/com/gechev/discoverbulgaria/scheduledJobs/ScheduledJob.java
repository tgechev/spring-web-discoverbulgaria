package com.gechev.discoverbulgaria.scheduledJobs;

import org.springframework.scheduling.annotation.Async;

public interface ScheduledJob {
    @Async
    void scheduledJob();
}
