package com.tushar.lms.user.utility;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class SmsReceiver {

	@StreamListener(Sink.INPUT)
	public void handle(SMS sms) {
		System.out.println(sms.toString());
	}

}
