package com.javarticles.camel.recipient;

public class EmployeeRouter {
    public String[] routeEmployee(Employee emp) {
        if (emp.isResigning()) {
            return new String[]{"direct:account", "direct:hr"};
        }
        if (emp.isNew()) {
            return new String[]{"direct:manager", "direct:account", "direct:hr"};
        }
        if (emp.isOnLeave()) {
            return new String[]{"direct:manager", "direct:hr"};
        }
        if (emp.isPromoted()) {
            return new String[]{"direct:hr", "direct:account"};
        }
        
        return new String[]{"direct:hr"};
    }
}
