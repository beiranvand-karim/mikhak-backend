package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.excelReader.batch.listener.JobCompletionNotificationListener;
import com.example.transportationbackend.excelReader.batch.listener.StepCompletionListener;
import com.example.transportationbackend.excelReader.batch.processor.DataProcessor;
import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import com.example.transportationbackend.models.road.RegisteredRoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importRoadData(JobCompletionNotificationListener listener, Step step1) throws Exception {
        return jobBuilderFactory.get("importRoadData")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<ExcelRowModel, RegisteredRoad>chunk(10)
                .listener(new StepCompletionListener())
                .reader(reader(null))
                .faultTolerant().skipPolicy(createSkipPolicy())
                .processor(processor())
                .writer(writer()).build();
    }

    @Bean
    @StepScope
    public DataItemReader reader(@Value("#{jobParameters[filePath]}") String filePath) {
        DataItemReader reader = new DataItemReader();
        reader.setFilePath(filePath);
        reader.readData();
        return reader;
    }

    @Bean
    public ItemProcessor<ExcelRowModel, RegisteredRoad> processor() {
        return new DataProcessor();
    }

    @Bean
    ItemWriter<RegisteredRoad> writer() {
        return new DataItemWriter();
    }

    @Bean
    SkipPolicy createSkipPolicy() {

        final Logger logger = LoggerFactory.getLogger("badRecordLogger");

        return new SkipPolicy() {
            @Override
            public boolean shouldSkip(Throwable exception, long skipCount) throws SkipLimitExceededException {
                if (exception instanceof FileNotFoundException) {
                    return false;
                } else if (exception instanceof FlatFileParseException && skipCount <= 5) {
                    FlatFileParseException ffpe = (FlatFileParseException) exception;

                    StringBuilder errorMessage = new StringBuilder();
                    errorMessage.append("An error occured while processing the ");
                    errorMessage.append(ffpe.getLineNumber());
                    errorMessage.append(" line of the file '");

                    Pattern pattern = Pattern.compile(".*(\\W\\w+\\.xlsx).*");
                    Matcher matcher = pattern.matcher(ffpe.toString());
                    if (matcher.matches())
                        errorMessage.append(matcher.group(1));
                    else
                        errorMessage.append("unknown");
                    errorMessage.append("'. Below was the faulty input.");
                    errorMessage.append("\n");
                    errorMessage.append(ffpe.getInput());
                    errorMessage.append("\n");
                    logger.error("{}", errorMessage);
                    return true;
                } else {
                    return false;
                }
            }
        };
    }
}
