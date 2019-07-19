package com.javarticles.camel.log;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelLogExchangeFormatterExample {
	
	public static final void main(String[] args) throws Exception {
		
		JndiContext jndiContext = new JndiContext();
		jndiContext.bind("stringUtils", new StringUtils());
		jndiContext.bind("logFormatter", new MyExchangeFormatter());
		
		CamelContext camelContext = new DefaultCamelContext(jndiContext);
		
		try {
			
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					from("direct:logExample")
					.log("Before converting to uppercase")
					.to("log:?level=INFO")
					.to("bean:stringUtils?method=upperCase")
					.log("After converting to uppercase")
					.to("log:com.javarticles?level=INFO");
				}
			});
			camelContext.start();
			
			ProducerTemplate template = camelContext.createProducerTemplate();
			template.sendBody("direct:logExample", "Log me!");
		} finally {
			camelContext.stop();
		}
	}
}
