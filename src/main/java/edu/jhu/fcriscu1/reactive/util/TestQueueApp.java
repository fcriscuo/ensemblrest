package edu.jhu.fcriscu1.reactive.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by fcriscuolo on 6/29/16.
 */
public class TestQueueApp {

    public static void main(String[] args) {
        try {
            // initialize the Consumer
            TestObservableQueueConsumer consumer = new TestObservableQueueConsumer();
            // initialize the Producer
           Thread t1 = new Thread (new Runnable (){

                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2L);
                        new TestObservableQueueProducer1().generateTestMessages();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread t2 = new Thread (new Runnable (){

                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                        new TestObservableQueueProducer2().generateTestMessages();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
            t2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
