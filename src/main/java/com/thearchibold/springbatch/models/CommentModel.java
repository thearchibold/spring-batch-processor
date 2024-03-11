package com.thearchibold.springbatch.models;


import com.thearchibold.springbatch.dto.CommentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity()
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {

	@Id
	public int id;
	public int postId;
	public String name;
	public String email;
	public String body;

	public CommentModel(CommentDto commentDto){
		this.id=commentDto.getId();
		this.postId=commentDto.getPostId();
		this.name=commentDto.getName();
		this.email= commentDto.getEmail();
		this.body= commentDto.getBody();
	}
}
