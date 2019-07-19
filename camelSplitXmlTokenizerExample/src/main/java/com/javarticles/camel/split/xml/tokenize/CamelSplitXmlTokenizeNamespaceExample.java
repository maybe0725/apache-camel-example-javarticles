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
        	// ��Ʈ �±� �Ǵ� �θ� �±װ� ���� �����̽��� ���Ѵٰ� �����մϴ�.
        	// �� ������ <articles>�� ���� �����̽� http://www.javarticles.com/schema/articles�� ���� �ֽ��ϴ�. 
        	// ���� ��Ҵ� root/parent �±׿��� ���� �����̽��� ��ӹ��� �� �ֽ��ϴ�.
        	// 
        	// root/parent �±��� �̸��� ����Ͽ� ��ū ��Ұ� �θ��� ���� �����̽��� ����ϵ��� ���� �� �ֽ��ϴ�.
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
