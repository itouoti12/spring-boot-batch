package com.example.demoBoot.batch.component;

import com.example.demoBoot.batch.entity.DemoEntity;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class HogeWriter implements ItemWriter<DemoEntity> {


    @Override
    public void write(List<? extends DemoEntity> items) throws Exception {

        items.forEach(
            item -> System.out.println("item = " + item.getMessage())
        );

    }
}
