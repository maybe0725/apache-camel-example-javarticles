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
        	// �� �������� ��� �����մϴ�. 
        	// ���� ��� ����ϴ�. 
        	// body�� 'Camel'�� �����ؾ��ϸ� 'Components'�ؽ�Ʈ�� �־���մϴ�. 
        	// �� ��� ��� ��ȿ�ؾ��մϴ�. 
        	// ������ ���� �������� ��������, ������ ���� ��� ����ϴ� ����� �˰ԵǾ����ϴ�.
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
