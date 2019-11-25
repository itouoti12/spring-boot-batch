package com.example.demoBoot.batch.job;

import com.example.demoBoot.batch.component.DemoComponent;
import com.example.demoBoot.batch.component.DemoComponentWriter;
import com.example.demoBoot.batch.component.HogeReader;
import com.example.demoBoot.batch.component.HogeWriter;
import com.example.demoBoot.batch.entity.DemoEntity;
import com.example.demoBoot.batch.listener.JobListener;
import lombok.Builder;
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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
//@RequiredArgsConstructor
public class DemoChunkJob {


    @Autowired
    private  JobBuilderFactory jobBuilderFactory;

    @Autowired
    private  StepBuilderFactory stepBuilderFactory;

    @Autowired
    private  DataSource dataSource;

    @Autowired
    private  DemoComponent component;

    @Autowired
    private DemoComponentWriter writer;



//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return (SqlSessionFactory) sessionFactory.getObject();
    }

    @Bean
    public MyBatisCursorItemReader<DemoEntity> reader() throws Exception {

//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("status","A");

        return new MyBatisCursorItemReaderBuilder<DemoEntity>()
            .sqlSessionFactory(sqlSessionFactory())
//            .parameterValues(parameter)
            .queryId("co.jp.mapper.select")
            .build();

    }

    @Bean
    public ItemReader reader2() {
        return new HogeReader();
    }

//    @Bean
//    public ItemWriter writer() {
//        return new DemoComponentWriter<String>(component::execute);
//        return writer.write();
//    }

    @Bean
    public void writer2(List<?> items) throws Exception {
         new HogeWriter().write((List<? extends DemoEntity>) items);
    }


    @Bean
    public Step step3() throws Exception {
        return stepBuilderFactory.get("step3")
            .chunk(1)
            .reader(this::reader2)
            .writer(this::writer2)
            .build();

    }



    @Bean
    public Job job3(Step step3) throws Exception {
        return jobBuilderFactory.get("job3")
            .incrementer(new RunIdIncrementer())
            .listener(listener2())
            .start(step3)
            .build();
    }

    @Bean
    public JobExecutionListener listener2() {
        return new JobListener();
    }

}
