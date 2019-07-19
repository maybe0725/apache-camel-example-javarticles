package com.javarticles.camel.filter;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class FilterByPredicateExample {
	
    public static void main(String[] args) throws Exception {
    
    	CamelContext camelContext = new DefaultCamelContext();
        
    	try {
        	
    		// =========================================================
    		// 이 예제에서는 Predicate 객체를 사용하여 필터링합니다. 
    		// Predicate는 matches(Exchange exchange) 메소드가 있는 인터페이스입니다.
    		// 만일 메시지가 우리의 관심사라면 true, 아니면 false를 반환 합니다.
    		// 우리는 body 내용을 검증하여 허용 여부를 결정합니다. 
    		// 만일 body 가 'Camel'로 시작하면 메시지가 전달됩니다.
    		// =========================================================
    		
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .filter(new Predicate() {
                        public boolean matches(Exchange exchange) {
                            final String body = exchange.getIn().getBody(String.class);
                            return ((body != null) && body.startsWith("Camel"));
                        }
                    })
                    .to("stream:out");                       
                }
            });
            camelContext.start();
            
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();   
            
            template.sendBody("direct:start", "Camel Multicast");
            template.sendBody("direct:start", "Camel Components");
            template.sendBody("direct:start", "Spring Integration");
            
            Thread.sleep(5000);
        } finally {
            camelContext.stop();
        }
    }
    
}
