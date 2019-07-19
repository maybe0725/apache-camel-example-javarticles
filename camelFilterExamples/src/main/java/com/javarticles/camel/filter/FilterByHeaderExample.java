package com.javarticles.camel.filter;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class FilterByHeaderExample 
{
    public static void main(String[] args) throws Exception 
    {
        CamelContext camelContext = new DefaultCamelContext();
    
        try {
        
        	// =========================================================================
        	// 이 예에서는 헤더 값으로 메시지를 필터링합니다. 
        	// 우선 순위가 높은 메시지 만 경로를 통과하도록 허용하려고합니다.
        	// 아래는 헤더를 기반으로 메시지를 필터링하는 라우팅입니다. 
        	// PrioritySetter는 전송 된 페이로드 개체를 기반으로 헤더 highPriority를 true 또는 false로 설정합니다.
        	// highPriority 헤더를 true로 설정하면 필터가 필터링됩니다.
        	// =========================================================================
        	
        	camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:customer")
                    .process(new PrioritySetter())
                    .filter(header("highPriority").isEqualTo(true))
                    .to("direct:highPriority");
                    
                    from("direct:highPriority")
                    .to("stream:out");
            }});
            camelContext.start();
            
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            
            System.out.println(":: Post high priority message ::");
            template.sendBody("direct:customer", new PriorityPayload(true, "This is high priority message"));
            
            System.out.println(":: Post low priority message ::");
            template.sendBody("direct:customer", new PriorityPayload(false, "This is low priority message"));
            
            Thread.sleep(5000);
        } finally {
            camelContext.stop();
        }
    }
    
    private static class PrioritySetter implements Processor {
        public void process(Exchange exchange) throws Exception {
            PriorityPayload payload = (PriorityPayload) exchange.getIn().getBody();
            exchange.getIn().setHeader("highPriority", payload.isHighPriority);
            exchange.getIn().setBody(payload.payload);
        }
    }
    
    private static class PriorityPayload {
        private boolean isHighPriority;
        private String payload;
        public PriorityPayload(boolean isHigh, String payload) {
            isHighPriority = isHigh;
            this.payload = payload;
        }
    }
}
