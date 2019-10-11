package com.example.demoBoot.batch.component;

import org.springframework.stereotype.Component;

@Component
public class DemoComponent implements Demo {

    public void execute(String item){
        System.out.println("hello" + item);
    }
}
