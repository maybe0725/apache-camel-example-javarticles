package com.javarticles.camel.recipient;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelRecipientListHeaderProcessorExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            String recipients = "direct:hr";
                            String employeeAction = exchange.getIn().getHeader("employee_action", String.class);
                            if (employeeAction.equals("new")) {
                                recipients += ",direct:account,direct:manager";
                            } else if (employeeAction.equals("resigns")) {
                                 recipients += ",direct:account";
                            }
                             exchange.getIn().setHeader("departments", recipients);
                        }
                    })
                    .recipientList(header("departments"));
                    
                    from("direct:account")
                    .log("Account department notified '${body}'");
                    
                    from("direct:hr")
                    .log("HR department notified '${body}'");
                    
                    from("direct:manager")
                    .log("Manager notified '${body}'");
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            System.out.println("************************");
            template.sendBodyAndHeader("direct:start", "Sam Joined", "employee_action", "new");
            System.out.println("************************");
            template.sendBodyAndHeader("direct:start", "John Resigned", "employee_action", "resigns");
            System.out.println("************************");
        } finally {
            camelContext.stop();
        }
    }
}
