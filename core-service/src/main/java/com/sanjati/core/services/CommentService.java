package com.sanjati.core.services;

import com.sanjati.core.entities.Comment;
import com.sanjati.core.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public void leaveComment(Long taskId, Long authorId, String message ){
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setAuthorId(authorId);
        comment.setDescription(message);
        commentRepository.save(comment);

    }
}
