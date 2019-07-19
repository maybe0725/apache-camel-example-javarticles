package com.javarticles.camel.split.xml.tokenize;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlTokenizeNamespaceExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
        	// ===============================================================================
        	// 루트 태그 또는 부모 태그가 네임 스페이스에 속한다고 가정합니다.
        	// 이 예에서 <articles>는 네임 스페이스 http://www.javarticles.com/schema/articles에 속해 있습니다. 
        	// 하위 요소는 root/parent 태그에서 네임 스페이스를 상속받을 수 있습니다.
        	// 
        	// root/parent 태그의 이름을 사용하여 토큰 요소가 부모의 네임 스페이스를 상속하도록 만들 수 있습니다.
        	// ===============================================================================
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:article")
                    .log("Split by article Element")
                    .split().tokenizeXML("c:article", "c:articles")
                    .log("${body}")                    
                    .end();                    
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articlesNs.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:article", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
