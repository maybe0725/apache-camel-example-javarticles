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
        	// tokenizeXML () �޼��带 ����Ͽ� �ڽ� ����� �±� �̸��� ����Ͽ� ������ �����մϴ�.
        	// �� ��� �츮�� <article>���� �����մϴ�.
        	// Header header.CamelSplitSize�� ���� �� Exchange�� �� ���� �����մϴ�.
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
