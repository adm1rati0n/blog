package com.example.demo.Repo;

import com.example.demo.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByCommentContains(String comment);
}
