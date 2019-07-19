package com.javarticles.camel.jdbc.component.insert.xpath;

import org.apache.camel.Exchange;
import org.apache.camel.language.XPath;

public class XPathRowPopulator {

    public void process(@XPath("article/category") String category,
            @XPath("article/title") String title,
            @XPath("article/tags") String tags, Exchange exchange) throws Exception {
        exchange.getIn().setHeader("name", title);
        exchange.getIn().setHeader("category", category);
        exchange.getIn().setHeader("tags", tags);
    }
}
