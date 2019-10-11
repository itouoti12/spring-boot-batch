package com.example.demoBoot.batch.job;

import com.example.demoBoot.batch.component.Demo;
import com.example.demoBoot.batch.component.DemoComponent;
import com.example.demoBoot.batch.component.DemoComponentWriter;
import com.example.demoBoot.batch.entity.DemoReader;
import com.example.demoBoot.batch.listener.JobListener;
import com.example.demoBoot.batch.tasklet.DemoTasklet;
import com.example.demoBoot.batch.tasklet.DemoTasklet2;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
//@RequierdArgsConstructor
@RequiredArgsConstructor
public class DemoChunkJob {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DemoComponentWriter writer;

    @Autowired
    DemoComponent component;

//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return (SqlSessionFactory) sessionFactory.getObject();
    }

    @Bean
    public MyBatisCursorItemReader<DemoReader> reader() throws Exception {

//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("status","A");

        return new MyBatisCursorItemReaderBuilder<DemoReader>()
            .sqlSessionFactory(sqlSessionFactory())
//            .parameterValues(parameter)
            .queryId("co.jp.mapper.select")
            .build();

    }

    @Bean
    private ItemWriter writer() {
       return new DemoComponentWriter<String>(component::execute);
    }


    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
            .chunk(1)
            .reader(reader())
            .writer(writer())
            .build();

    }



    @Bean
    public Job job1(Step step1) throws Exception {
        return jobBuilderFactory.get("job1")
            .incrementer(new RunIdIncrementer())
            .listener(listener())
            .start(step1)
            .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }

}
