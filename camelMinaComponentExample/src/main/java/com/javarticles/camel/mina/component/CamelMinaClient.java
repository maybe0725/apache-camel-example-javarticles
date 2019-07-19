package com.javarticles.camel.mina.component;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelMinaClient {
	
	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					from("direct:start")
					.to("mina:tcp://localhost:8999?sync=true");
				}
			});
			camelContext.start();
			ProducerTemplate template = camelContext.createProducerTemplate();
			System.out.println(template.requestBody("direct:start", "Hello"));
			Thread.sleep(5000);
		} finally {
			camelContext.stop();
		}
	}
	
}
