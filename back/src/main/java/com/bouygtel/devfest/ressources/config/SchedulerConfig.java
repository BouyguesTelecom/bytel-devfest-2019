package com.bouygtel.devfest.ressources.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.bouygtel.devfest.ressources.Ressources;
import com.bouygtel.devfest.ressources.Session;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

	private final Ressources ressources;
	private final AmazonDynamoDB clientDynamo;

	public SchedulerConfig(Ressources ressources, AmazonDynamoDB clientDynamo) {
		this.ressources = ressources;
		this.clientDynamo = clientDynamo;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
		return threadPoolTaskScheduler;
	}

	@Scheduled(fixedDelay = 50)
	public void sendSpeed() {
		ressources.sendStats();
	}

	@Scheduled(fixedDelay = 500)
	public void rechercherNombreInstancesLambda() {
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		Session session = this.ressources.getStats().getSession();
		expressionAttributeValues.put(":id_session", new AttributeValue().withS(session.getUiidSession()));

		ScanRequest scanRequest = new ScanRequest().withTableName("lambda-executions")
				.withFilterExpression("id_session = :id_session")
				.withExpressionAttributeValues(expressionAttributeValues);

		ScanResult result = clientDynamo.scan(scanRequest);
		this.ressources.getStats().setLambdasUtilisees(result.getItems().size());
	}
}
