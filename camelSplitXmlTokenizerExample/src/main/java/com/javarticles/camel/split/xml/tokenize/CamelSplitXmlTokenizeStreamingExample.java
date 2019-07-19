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
        	// 페이로드가 크면 스트리밍 모드로 분할하여 입력 메시지를 여러 조각으로 나눌 수 있습니다. 
        	// 이것은 메모리 오버 헤드를 줄이는 데 유용합니다.
        	// 
        	// XPath 엔진과는 달리 전체 XML 컨텐츠를 메모리 tokenizeXML () 표현식으로로드합니다.
        	// 이 표현식은 스트리밍 방식으로 XML 페이로드를 반복합니다.
        	// 스트리밍을 사용하려면 DSL에서 streaming()을 호출해야합니다.
        	// 
        	// 스트림 기반 분할의 경우 헤더 header.CamelSplitSize는 완료된 교환에만 적용됩니다.
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
