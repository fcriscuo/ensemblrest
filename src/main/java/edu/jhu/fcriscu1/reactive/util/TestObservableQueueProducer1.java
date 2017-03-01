package edu.jhu.fcriscu1.reactive.util;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.util.Time;

import java.util.concurrent.TimeUnit;

/**
 * Test class to generate test objects and place them into an ObservableQueue implementation
 * Created by fcriscuolo on 6/29/16.
 */
@Log4j
public class TestObservableQueueProducer1 {

    public TestObservableQueueProducer1(){}
    public void generateTestMessages(){
        try {

            MessageQueueService.INSTANCE.getMessageObservableQueue().add( new TestMessage(this.getClass().getSimpleName() +" Message 1"));
            TimeUnit.SECONDS.sleep(2L);
            MessageQueueService.INSTANCE.getMessageObservableQueue().add( new TestMessage(this.getClass().getSimpleName() +" Message 2"));
            TimeUnit.SECONDS.sleep(2L);
            MessageQueueService.INSTANCE.getMessageObservableQueue().add( new TestMessage(this.getClass().getSimpleName() +" Message 3"));
            log.info(this.getClass().getSimpleName() +" Messages produced");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
