package com.sanjati.core.controllers;

import com.sanjati.api.core.CommentDto;
import com.sanjati.api.core.CommentRq;
import com.sanjati.core.converters.CommentConverter;
import com.sanjati.core.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentConverter commentConverter;
    private final CommentService commentService;

    @Operation(
            summary = "Запрос на получение комментариев по заявке",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CommentDto.class))))
            }
    )
    @GetMapping
    public List<CommentDto> getAllCommentsByTaskId(@Parameter(description = "ID заявки", required = true) @RequestParam Long taskId) {
        return commentService.findAllByTaskId(taskId).stream().map(commentConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание комментария к заявке",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public void createComment(@Parameter(description = "Тело запроса", required = true)@RequestBody CommentRq commentRq,
                              @Parameter(description = "ID пользователя", required = true)@RequestHeader Long id) {
        commentService.leaveComment(commentRq, id);
    }
}
