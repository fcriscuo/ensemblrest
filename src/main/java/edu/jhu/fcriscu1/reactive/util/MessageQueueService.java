package edu.jhu.fcriscu1.reactive.util;

/**
 * Created by fcriscuolo on 6/29/16.
 */
public enum MessageQueueService {
    INSTANCE;

    ObservableQueue<TestMessage> messageObservableQueue =
            new ObservableQueue<>();

    public ObservableQueue<TestMessage> getMessageObservableQueue() {
        return this.messageObservableQueue;
    }
}
