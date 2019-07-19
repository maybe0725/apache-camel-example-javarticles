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
    		// �� ���������� Predicate ��ü�� ����Ͽ� ���͸��մϴ�. 
    		// Predicate�� matches(Exchange exchange) �޼ҵ尡 �ִ� �������̽��Դϴ�.
    		// ���� �޽����� �츮�� ���ɻ��� true, �ƴϸ� false�� ��ȯ �մϴ�.
    		// �츮�� body ������ �����Ͽ� ��� ���θ� �����մϴ�. 
    		// ���� body �� 'Camel'�� �����ϸ� �޽����� ���޵˴ϴ�.
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
