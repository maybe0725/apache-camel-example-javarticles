package com.javarticles.camel.mina.component;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelMinaServer {
	
	public static void main(String[] args) throws Exception {		
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {			
			public void configure() {
				from("mina:tcp://localhost:8999").process(new Processor() {					
					public void process(Exchange exchange) throws Exception {
						String body = exchange.getIn().getBody(String.class);
						exchange.getOut().setBody(body + "\nMina");
					}
				});
			}
		});		
		camelContext.start();		
		for (;;) {
			Thread.sleep(1000);
		}
	}
	
}
