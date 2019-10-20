package com.bouygtel.devfest.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

@Configuration
public class AWSConfig {

	private static ClientConfiguration config;

	static {
		config = new ClientConfiguration();
		config.setRequestTimeout(30000);
		config.setClientExecutionTimeout(30000);
	}

	@Bean
	public LambdaService initAWSLambdaClient() {
		AWSLambda awsLambda = AWSLambdaClientBuilder.standard()//
				.withRegion("eu-west-3")//
				.withClientConfiguration(config)//
				.build();
		return LambdaInvokerFactory.builder()//
				.lambdaClient(awsLambda)//
				.build(LambdaService.class);
	}

	public interface LambdaService {
		@LambdaFunction(functionName = "devfest")
		Object call(LambdaIn in);
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDBClient() {
		return AmazonDynamoDBClientBuilder.standard()//
				.withRegion(Regions.EU_WEST_3)//
				.withClientConfiguration(config)//
				.build();
	}

}
