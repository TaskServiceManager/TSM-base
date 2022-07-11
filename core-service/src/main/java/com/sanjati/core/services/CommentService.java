package com.sanjati.core.services;

import com.sanjati.core.entities.Comment;
import com.sanjati.core.entities.Executor;
import com.sanjati.core.entities.Task;
import com.sanjati.core.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public void leaveComment(Executor executor, Executor manager, String message, Task task){
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setDescription(manager.getName() + message + executor.getName());
        commentRepository.save(comment);



    }
}
