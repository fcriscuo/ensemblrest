package edu.jhu.fcriscu1.reactive.util;

import lombok.extern.log4j.Log4j;

/**
 * Created by fcriscuolo on 6/29/16.
 */
@Log4j
public class TestMessage{
    private final String message;
    private final Long tom;
    public TestMessage(String aMessage) {
        this.message = aMessage;
        this.tom = System.currentTimeMillis();
    }

    public String message() { return this.message;}
    public Long tom() { return this.tom;}
}
