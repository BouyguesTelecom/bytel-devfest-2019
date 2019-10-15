package com.bouygtel.devfest.ressources.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.bouygtel.devfest.ressources.Ressources;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	private final Ressources ressources;

	public SchedulerConfig(Ressources ressources) {
		this.ressources = ressources;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Scheduled(fixedDelay = 500)
	public void sendSpeed() {
		ressources.sendStats();
	}
}
