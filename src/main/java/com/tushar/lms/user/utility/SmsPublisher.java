package com.tushar.lms.user.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@EnableBinding(Source.class)
@Service
public class SmsPublisher {

	@Autowired
	private MessageChannel output;

	Logger logger = LoggerFactory.getLogger(SmsPublisher.class);

	public void sendMessage(SMS sms) {

		logger.info("Inside SmsPublisher ---------------> sendMessage");

		output.send(MessageBuilder.withPayload(sms).build());
		logger.info("SMS sent");

	}

}
