package org.mskcc.cbio.reactive.util;

import lombok.extern.log4j.Log4j;
import rx.functions.Action1;

/**
 * Created by fcriscuolo on 6/29/16.
 */
@Log4j
public class TestObservableQueueConsumer {

    public TestObservableQueueConsumer(){
        log.info("Consumer started");
        MessageQueueService.INSTANCE.getMessageObservableQueue().observe().forEach(new Action1<TestMessage>() {
            @Override
            public void call(TestMessage testMessage) {
                log.info("Test message received");
                log.info(testMessage.message());
            }
        });
    }
}
