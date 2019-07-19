package com.javarticles.camel.filter;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FilterStreamExample {
	
    public static void main(String[] args) throws Exception {
    	
        deleteDir("target/stream");
        createDir("target/stream");
        
        File file = new File("target/stream/articlesStream.txt");
        file.createNewFile();
        
        FileOutputStream fos = new FileOutputStream(file);
        fos.write("spring core\n".getBytes());
        fos.write("spring batch\n".getBytes());
        fos.write("camel components\n".getBytes());
        fos.write("spring integration\n".getBytes());
        fos.write("camel dsl\n".getBytes());
        fos.write("java 8\n".getBytes());
        fos.close();
        
        CamelContext camelContext = new DefaultCamelContext();
        
        try {
        	
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("stream:file?fileName=target/stream/articlesStream.txt&scanStream=true&scanStreamDelay=100")
                    .filter(body().startsWith("camel"))
                    .to("stream:out");
                }
            });
            camelContext.start();
            
            Thread.sleep(1000);
        } finally {
            camelContext.stop();
        }
    }    
    
    public static void createDir(String fileName) {
        File dir = new File(fileName);
        dir.mkdirs();
    }    
    
    private static void deleteDir(String fileName) {
        File file = new File(fileName);
        deleteDir(file);
    }
    
    private static void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
        
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File child : files) {
                deleteDir(child);
            }
        }
        file.delete();
    }
}
