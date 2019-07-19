package com.javarticles.camel.filter;

import static org.apache.camel.builder.PredicateBuilder.and;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class FilterByCompoundPredicateExample {
	
    public static void main(String[] args) throws Exception {
    	
        CamelContext camelContext = new DefaultCamelContext();
        
        try {
        	
        	// =================================================
        	// 이 예에서는 술어를 결합합니다. 
        	// 복합 술어를 만듭니다. 
        	// body는 'Camel'로 시작해야하며 'Components'텍스트가 있어야합니다. 
        	// 두 술어가 모두 유효해야합니다. 
        	// 예제는 조금 지저분해 보이지만, 이제는 복합 술어를 사용하는 방법을 알게되었습니다.
        	// =================================================
        	
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .log("Allow if '${body}' is a camel component")
                    .filter(and(body().startsWith("Camel"), method(new CamelArticles(), "filterOnlyCamelComponents")))
                    .to("stream:out");
                }
            });
            camelContext.start();
            
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            
            template.sendBody("direct:start", "Camel Multicast");
            template.sendBody("direct:start", "Camel Component: Stream");
            template.sendBody("direct:start", "Spring Integration");
            
            Thread.sleep(5000);            
        } finally {
            camelContext.stop();
        }
    }

}
