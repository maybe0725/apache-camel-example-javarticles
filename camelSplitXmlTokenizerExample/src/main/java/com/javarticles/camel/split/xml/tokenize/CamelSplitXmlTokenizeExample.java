package com.javarticles.camel.split.xml.tokenize;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlTokenizeExample {
	
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
        	// =========================================================
        	// tokenizeXML () 메서드를 사용하여 자식 노드의 태그 이름을 사용하여 파일을 분할합니다.
        	// 이 경우 우리는 <article>으로 분할합니다.
        	// Header header.CamelSplitSize는 분할 된 Exchange의 총 수를 보유합니다.
        	// =========================================================
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:author")
                    .log("Find all the authors")
                    .split().tokenizeXML("author")
                    .log("${body} split size: ${header.CamelSplitSize}")
                    .end();      
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articles.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:author", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
