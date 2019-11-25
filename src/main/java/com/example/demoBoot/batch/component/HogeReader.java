package com.example.demoBoot.batch.component;

import com.example.demoBoot.batch.entity.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Scope("step")
public class HogeReader implements ItemReader<DemoEntity> {

    private List<DemoEntity> entity;

    private int count = 0;

    @Override
    public DemoEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


        entity = List.of(
            DemoEntity.builder().message("hello").build(),
            DemoEntity.builder().message("goodAfternoon").build(),
            DemoEntity.builder().message("goodEvening").build()
        );


        return entity.get(count++);
    }
}
