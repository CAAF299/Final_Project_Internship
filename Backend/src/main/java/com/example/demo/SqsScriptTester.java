package com.example.demo;


import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;


public class SqsScriptTester {

    public static void main(String[] args) {
        String queueUrl = "https://sqs.us-east-2.amazonaws.com/599729677340/api-queue"; 

        SqsClient sqs = SqsClient.builder().build();


        System.out.println("Sending message");
        sqs.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("AWS SQS Message")
                .build());

  
        System.out.println("Receiving message from SQS...");
        ReceiveMessageResponse response = sqs.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .build());

        for (Message msg : response.messages()) {
            System.out.println("Received: " + msg.body());
        }
    }

    
}
