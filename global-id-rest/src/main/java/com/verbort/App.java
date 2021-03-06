package com.verbort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ImportResource(locations = {"spring-db.xml"})
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
