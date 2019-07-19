package com.javarticles.camel.content.routing;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelContentBasedRouterExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .choice()
                        .when(simple("${body} contains 'Java'"))
                            .to("direct:javaArticles")
                            .log("Added to 'Java Articles'")
                        .when(simple("${header.views} > 10000"))
                            .to("direct:popularArticles")
                            .log("Added to 'Popular Articles'")
                        .otherwise()
                            .to("direct:allArticles")
                            .log("Added to 'All Articles'")
                    .end()
                    .log("Processing of ${body} done");
                    
                    from("direct:javaArticles")
                    .log("Java Articles")
                    .to("stream:out");
                    
                    from("direct:popularArticles")
                    .log("Popular Articles")
                    .to("stream:out");
                    
                    from("direct:allArticles")
                    .log("All Articles")
                    .to("stream:out");
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            template.sendBody("direct:start", "Java Threads");
            template.sendBodyAndHeader("direct:start", "Camel Content Based Router", "views", 20000);
            template.sendBody("direct:start", "Spring Integration");
        } finally {
            camelContext.stop();
        }
    }
}
