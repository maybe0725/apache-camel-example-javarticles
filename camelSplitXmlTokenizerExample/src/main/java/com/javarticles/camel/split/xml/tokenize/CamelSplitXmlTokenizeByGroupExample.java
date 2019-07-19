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
        	// ���� �� XML ûũ�� ���� �����Ϸ��� N �ɼ��� �Բ� �׷�ȭ �� ���ִ� group �ɼ��� ����� �� �ֽ��ϴ�. 
        	// �� �������� XML�� �� �׷����� ������ �ϳ��� �׷쿡 <articles> �� ��Ʈ�� ǥ���մϴ�.
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
