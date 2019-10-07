package com.bouygtel.devfest.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

@Configuration
public class AWSConfig {

	@Bean
	public LambdaService initAWSLambdaClient() {
		AWSLambda awsLambda = AWSLambdaClientBuilder.standard()//
				.withRegion("eu-west-3")//
				.build();
		return LambdaInvokerFactory.builder()//
				.lambdaClient(awsLambda)//
				.build(LambdaService.class);
	}

	public interface LambdaService {
		@LambdaFunction(functionName = "devfest-2019")
		Object midiEventReceiver();
	}

}
