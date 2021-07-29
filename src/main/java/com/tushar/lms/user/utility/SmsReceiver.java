package com.tushar.lms.user.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class SmsReceiver {

	Logger logger = LoggerFactory.getLogger(SmsReceiver.class);

	@StreamListener("input")
	public void recieveMessage(SMS sms) {

		logger.info("Inside SmsReceiver ---------------> recieveMessage");
		logger.info(sms.toString());

	}

}
