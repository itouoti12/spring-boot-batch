package com.example.demoBoot.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("job1")
    Job job1;

    @Autowired
    @Qualifier("job2")
    Job job2;

    @GetMapping(value = "/batchjob1")
    public void handle1() throws Exception {

        // 引数を定義する
        jobLauncher.run(job1, new JobParameters());
    }

    @GetMapping(value = "/batchjob2")
    public void handle2() throws Exception {

        // 引数を定義する
        jobLauncher.run(job2, new JobParameters());
    }
}
