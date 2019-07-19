package com.javarticles.camel.multicast;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NumberPayloadValidator implements Processor {

    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();
        if (body instanceof Number) {
            return;
        }
        String bodyAsString = body.toString();
        Integer.parseInt(bodyAsString);
    }

}
