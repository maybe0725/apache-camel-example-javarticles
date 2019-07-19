package com.javarticles.camel.split.xml.tokenize;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlTokenizeByGroupExample {
	
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
        	// =================================================================
        	// 분할 된 XML 청크의 수를 제어하려면 N 옵션을 함께 그룹화 할 수있는 group 옵션을 사용할 수 있습니다. 
        	// 이 예에서는 XML을 두 그룹으로 나눠서 하나의 그룹에 <articles> 두 세트를 표시합니다.
        	// =================================================================
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:article")
                    .log("Find all the articles in group of 2")
                    .split().tokenizeXML("article", 2)
                    .log("${body}")
                    .end();                                        
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articles.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:article", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
