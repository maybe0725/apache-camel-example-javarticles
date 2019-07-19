package com.javarticles.camel.split.xml.tokenize;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlTokenizeStreamingExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
        	// ============================================================
        	// ���̷ε尡 ũ�� ��Ʈ���� ���� �����Ͽ� �Է� �޽����� ���� �������� ���� �� �ֽ��ϴ�. 
        	// �̰��� �޸� ���� ��带 ���̴� �� �����մϴ�.
        	// 
        	// XPath �������� �޸� ��ü XML �������� �޸� tokenizeXML () ǥ�������ηε��մϴ�.
        	// �� ǥ������ ��Ʈ���� ������� XML ���̷ε带 �ݺ��մϴ�.
        	// ��Ʈ������ ����Ϸ��� DSL���� streaming()�� ȣ���ؾ��մϴ�.
        	// 
        	// ��Ʈ�� ��� ������ ��� ��� header.CamelSplitSize�� �Ϸ�� ��ȯ���� ����˴ϴ�.
        	// ============================================================
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:authorStreaming")
                    .log("Find all the authors using tokenizer/streaming")
                    .split().tokenizeXML("author").streaming()
                    .log("${body} split size: ${header.CamelSplitSize}")                    
                    .end();    
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articles.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:authorStreaming", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
