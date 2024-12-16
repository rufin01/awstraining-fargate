package com.awstraining.backend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ComprehendSentimentConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKey:#{null}}")
    private String snsAccessKey;

    @Value("${aws.secretKey:#{null}}")
    private String snsSecretKey;

    // TODO: lab3
    //  0. Uncomment @Bean section.
    //  1. Configure AmazonComprehend which will be used by fargate within AWS.
    //  2. Make sure that your task role has access to detect sentiment action (ecs-task-role-policy).
    //  3. Think how to connect with AWS Service from your local pc.
    @Bean
    AmazonComprehend configureComprehendClient() {
        if (!StringUtils.isBlank(snsAccessKey) && !StringUtils.isBlank(snsSecretKey)) {
            return AmazonComprehendClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(snsAccessKey, snsSecretKey)))
                .build();
        } else {
            return AmazonComprehendClient
                .builder()
                .build();
        }
    }
}
