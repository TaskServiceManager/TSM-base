package com.sanjati.core.services;

import com.sanjati.api.core.CommentRq;
import com.sanjati.core.entities.Comment;
import com.sanjati.core.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void leaveComment(Long taskId, Long authorId, String message){
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setAuthorId(authorId);
        comment.setDescription(message);
        commentRepository.save(comment);
    }

    public void leaveComment(CommentRq commentRq, Long authorId) {
        leaveComment(commentRq.getTaskId(), authorId, commentRq.getDescription());
    }

    public List<Comment> findAllByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }
}
