package com.example.demoBoot.batch.component;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoComponentWriter<T> implements ItemWriter<T> {

    ComponentExecuter<T> component;

    public DemoComponentWriter(ComponentExecuter<T> component) {

        this.component = component;
    }


    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach(i -> component.execute(i));
    }


    public static interface ComponentExecuter<T>{
        void execute(T value);
    }
}
