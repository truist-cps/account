package com.truist.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.truist.account.model.TransferRequest;

@Service
public class Outbound {
	    private static final Logger logger = LoggerFactory.getLogger(Outbound.class);
	    private static final String TOPIC = "truist-account";

	    @Autowired
	    private KafkaTemplate<String, TransferRequest> kafkaTemplate;

	    public void sendMessage(TransferRequest transactionRequest) {
	        logger.info(String.format("#### -> Producing message -> %s", transactionRequest.toString()));
	        this.kafkaTemplate.send(TOPIC, new TransferRequest(transactionRequest));
	    }
}
