package com.thearchibold.springbatch.config;

import com.thearchibold.springbatch.dto.CommentDto;
import com.thearchibold.springbatch.models.CommentModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

public class CommentItemProcessor implements ItemProcessor<CommentDto, CommentModel> {

	@Override
	public CommentModel process(CommentDto item) throws Exception {
		// All the business logic goes here
		String desc = item.getBody();
		if(desc.length() > 100){
			item.setBody(desc.substring(0, 100));
		}
		return new CommentModel(item);
	}
}
