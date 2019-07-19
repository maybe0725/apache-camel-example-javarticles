package com.javarticles.camel.recipient;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelRecipientListRouterExample {
    public static final void main(String[] args) throws Exception {
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("empRouter", new EmployeeRouter());
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .recipientList(method("empRouter", "routeEmployee"));
                    
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
            Employee sam = new Employee("Sam");
            sam.setNew(true);
            sam.setMessage("Joined");
            
            template.sendBody("direct:start", sam);
            
            System.out.println("************************");
            
            Employee john = new Employee("John");
            john.setOnLeave(true);
            john.setMessage("On Leave");
            template.sendBody("direct:start", john);
            
            System.out.println("************************");
            
            Employee roy = new Employee("Roy");
            roy.setPromoted(true);
            roy.setMessage("Promoted");
            template.sendBody("direct:start", roy);
            
            System.out.println("************************");
            
            Employee ram = new Employee("Ram");
            ram.setResigning(true);
            ram.setMessage("Resigning");
            template.sendBody("direct:start", ram);
            
            System.out.println("************************");
        } finally {
            camelContext.stop();
        }
    }
}
