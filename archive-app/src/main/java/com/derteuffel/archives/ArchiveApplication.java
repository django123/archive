package com.derteuffel.archives;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArchiveApplication implements CommandLineRunner {


    public static void main(String [] args){
        SpringApplication.run(ArchiveApplication.class, args);
    }


    public void run(String... args) throws Exception {
        System.err.println("Application run ...");

    }
}
