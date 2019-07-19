package com.javarticles.camel.log;

import org.apache.camel.Exchange;
import org.apache.camel.spi.ExchangeFormatter;

public class MyExchangeFormatter implements ExchangeFormatter {

    public String format(Exchange exchange) {
        return exchange.getIn().getBody(String.class);
    }

}
