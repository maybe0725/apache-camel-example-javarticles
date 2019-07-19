package com.javarticles.camel.recipient;

public class Employee {
    private boolean isResigning;
    private boolean isNew;
    private boolean isOnLeave;
    private boolean isPromoted;
    private String msg;
    private String name;
    
    public Employee(String name) {
        this.name = name;
    }
    
    public void setResigning(boolean isResigning) {
        this.isResigning = isResigning;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setOnLeave(boolean isOnLeave) {
        this.isOnLeave = isOnLeave;
    }

    public void setPromoted(boolean isPromoted) {
        this.isPromoted = isPromoted;
    }

    public boolean isResigning() {
        return isResigning;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isOnLeave() {
        return isOnLeave;
    }

    public boolean isPromoted() {
        return isPromoted;
    }
    
    public void setMessage(String msg) {
        this.msg = msg;
    }
    
    public String toString() {
        return "Employee " + name + " " + msg;
    }
}
