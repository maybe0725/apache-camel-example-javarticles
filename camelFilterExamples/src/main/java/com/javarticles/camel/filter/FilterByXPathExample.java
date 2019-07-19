package com.javarticles.camel.filter;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class FilterByXPathExample {
	
    public static void main(String[] args) throws Exception {
        
        StringBuffer articlesXmlSb = new StringBuffer();
        articlesXmlSb.append("  <blog>                                             ").append("\n");  
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>spring integration</category>    ").append("\n");
		articlesXmlSb.append("          <title>SpringInt Splitter</title>          ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>java</category>                  ").append("\n");
		articlesXmlSb.append("          <title>Lambda</title>                      ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>camel</category>                 ").append("\n");
		articlesXmlSb.append("          <title>Camel Multicast</title>             ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>camel</category>                 ").append("\n");
		articlesXmlSb.append("          <title>Camel Component: ActiveMQ </title>  ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>camel</category>                 ").append("\n");
		articlesXmlSb.append("          <title>Camel Component: Timer</title>      ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>camel</category>                 ").append("\n");
		articlesXmlSb.append("          <title>Camel Component: Logger</title>     ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("      <article>                                      ").append("\n");
		articlesXmlSb.append("          <category>camel</category>                 ").append("\n");
		articlesXmlSb.append("          <title>Camel DSL</title>                   ").append("\n");
		articlesXmlSb.append("      </article>                                     ").append("\n");
		articlesXmlSb.append("  </blog>                                            ").append("\n");
        
        CamelContext camelContext = new DefaultCamelContext();
        
        try {
        	
            camelContext.addRoutes(new RouteBuilder() {
            	
                public void configure() {
                	
                    XPathBuilder splitter = new XPathBuilder("//blog/article");
                    
                    from("direct:xpath")
                    .split(splitter)
                    .filter().xquery("//article[category='camel']")
                    .to("direct:camel");
                    
                    from("direct:camel")
                    .filter().xpath("//article/title[contains(.,'Camel Component')]")
                    .to("stream:out");

                }
            });
            camelContext.start();
            
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            
            template.sendBody("direct:xpath", articlesXmlSb.toString());
            
            Thread.sleep(1000);
        } finally {
            camelContext.stop();
        }
    }    
}
