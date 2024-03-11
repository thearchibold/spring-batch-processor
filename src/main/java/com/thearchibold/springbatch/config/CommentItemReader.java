package com.thearchibold.springbatch.config;

import com.thearchibold.springbatch.dto.CommentDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CommentItemReader implements ItemReader<CommentDto> {

	private final String endpoint;
	private final RestTemplate restTemplate;
	private int nextCommentIndex;
	private List<CommentDto> commentData;
	private boolean commentDataInit = false;

	CommentItemReader(String endpoint, RestTemplate restTemplate){
		this.endpoint = endpoint;
		this.restTemplate = restTemplate;
		this.nextCommentIndex = 0;
	}
	@Override
	public CommentDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(!this.commentDataInit){
			this.commentData = fetchCommentsFromApi();
		}
		CommentDto nextComment = null;
		if(this.nextCommentIndex < this.commentData.size()){
			nextComment = this.commentData.get(this.nextCommentIndex);
			this.nextCommentIndex +=1;
		}else {
			this.nextCommentIndex = 0;
			this.commentData = null;
		}
		return nextComment;
	}

	private List<CommentDto> fetchCommentsFromApi(){
		ResponseEntity<CommentDto[]> response = restTemplate.getForEntity(this.endpoint, CommentDto[].class);
		CommentDto[] commentDtos = response.getBody();
		this.commentDataInit = true;
		return Arrays.asList(commentDtos);
	}
}
