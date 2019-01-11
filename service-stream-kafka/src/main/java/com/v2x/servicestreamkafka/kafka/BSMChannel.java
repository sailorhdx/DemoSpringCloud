package com.v2x.servicestreamkafka.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BSMChannel {
    //接收队列1
    String input_bsm = "input_bsm";

    @Input(BSMChannel.input_bsm)
    SubscribableChannel inputBsm();
}
