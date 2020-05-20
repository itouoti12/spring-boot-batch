package com.example.demoBoot.batch.job;

import com.example.demoBoot.batch.listener.JobListener;
import com.example.demoBoot.batch.tasklet.DemoTasklet;
import com.example.demoBoot.batch.tasklet.DemoTasklet2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

@Configuration
@EnableBatchProcessing
public class DemoJob {


    @Autowired
    private DemoTasklet tasklet1;

    @Autowired
    private DemoTasklet2 tasklet2;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet(tasklet1)
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet(tasklet2)
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
    public Job job2(Step step2) throws Exception {
        return jobBuilderFactory.get("job2")
            .incrementer(new RunIdIncrementer())
            .listener(listener())
            .start(step2)
            .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }

}
