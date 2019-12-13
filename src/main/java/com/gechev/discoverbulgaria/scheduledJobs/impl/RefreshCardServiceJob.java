package com.gechev.discoverbulgaria.scheduledJobs.impl;

import com.gechev.discoverbulgaria.scheduledJobs.ScheduledJob;
import com.gechev.discoverbulgaria.services.CardService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshCardServiceJob implements ScheduledJob {
    private final CardService cardService;

    public RefreshCardServiceJob(CardService cardService){
        this.cardService = cardService;
    }

    @Override
    @Scheduled(cron = "0 0 2 1/1 * ?")
    public void scheduledJob() {
        this.cardService.refreshCards();
    }
}
