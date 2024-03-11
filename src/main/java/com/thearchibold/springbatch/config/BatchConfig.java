package com.thearchibold.springbatch.config;


import com.thearchibold.springbatch.dto.CommentDto;
import com.thearchibold.springbatch.models.CommentModel;
import com.thearchibold.springbatch.models.repos.CommentModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

	@Autowired
	CommentModelRepository commentModelRepository;

	@Autowired
	PlatformTransactionManager platformTransactionManager;



	@Autowired
	JobRepository jobRepository;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ItemReader<CommentDto> itemReader() {
		RestTemplate restTemplate = restTemplate();
		return new CommentItemReader("https://jsonplaceholder.typicode.com/comments", restTemplate);
	}

	@Bean
	public CommentItemProcessor commentItemProcessor() {
		return new CommentItemProcessor();
	}

	@Bean
	public RepositoryItemWriter<CommentModel> writer() {
		RepositoryItemWriter<CommentModel> writer = new RepositoryItemWriter<>();
		writer.setRepository(commentModelRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step processComment() {
		return new StepBuilder("fetchComment", jobRepository)
				.<CommentDto, CommentModel>chunk(10, platformTransactionManager)
				.reader(itemReader())
				.processor(commentItemProcessor())
				.writer(writer())
				.build();
	}


	@Bean
	public Job runJob() {
		return new JobBuilder("commentRunnerJob", jobRepository)
				.start(processComment())
				.build();
	}
}
