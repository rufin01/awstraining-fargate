package com.awstraining.backend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class TranslatorConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKey:#{null}}")
    private String snsAccessKey;

    @Value("${aws.secretKey:#{null}}")
    private String snsSecretKey;
    
    // TODO: lab2
    //  0. Uncomment @Bean section.
    //  1. Configure AmazonTranslator which will be used by fargate within AWS.
    //  2. Make sure that your task role has access to call translateText action (ecs-task-role-policy).
    //  3. Think how to connect with AWS Service from your local pc.
    @Bean
    AmazonTranslate configureTranslatorClient() {
        if (!StringUtils.isBlank(snsAccessKey) && !StringUtils.isBlank(snsSecretKey)) {
            return AmazonTranslateClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(snsAccessKey, snsSecretKey)))
                .build();
        } else {
            return AmazonTranslateClientBuilder
                .standard()
                .build();
        }
    }
}
