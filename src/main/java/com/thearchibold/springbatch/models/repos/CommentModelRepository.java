package com.thearchibold.springbatch.models.repos;

import com.thearchibold.springbatch.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentModelRepository extends CrudRepository<CommentModel, Integer> {
}
