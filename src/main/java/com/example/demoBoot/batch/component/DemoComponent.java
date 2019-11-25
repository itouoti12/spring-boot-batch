package com.example.demoBoot.batch.component;

import org.springframework.stereotype.Component;

@Component
public class DemoComponent {

    public void execute(String item){
        System.out.println("hello" + item);
    }
}
