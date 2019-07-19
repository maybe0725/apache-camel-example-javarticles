package com.javarticles.camel.loadbalance;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelLoadBalanceRoundRobinUsingSpring {
	
    public static final void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("loadBalancingRoundRobinCamelContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {            
            camelContext.start();            
            Thread.sleep(5000);
        } finally {
            camelContext.stop();
        }
    }
}
