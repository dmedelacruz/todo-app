package com.todoapp.controller;

import com.todoapp.dao.request.TodoRequest;
import com.todoapp.dao.response.BaseResponse;
import com.todoapp.dao.response.TodoResponse;
import com.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
@PreAuthorize("isAuthenticated()")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public BaseResponse<TodoResponse> createTodo(
            @RequestParam(value = "todo_list_id") String todoListId,
            @RequestBody TodoRequest todoRequest
    ) {
        TodoResponse todoResponse = todoService.createTodo(todoListId, todoRequest);
        return new BaseResponse<>(todoResponse);
    }

    @PutMapping("/{todo-id}")
    public BaseResponse<TodoResponse> updateTodoById(
            @PathVariable(value = "todo-id") String todoId,
            @RequestBody TodoRequest todoRequest
    ) {
        TodoResponse todoResponse = todoService.updateTodo(todoId, todoRequest);
        return new BaseResponse<>(todoResponse);
    }

    @PutMapping("/{todo-id}/status")
    public BaseResponse<TodoResponse> setTodoStatus(
            @PathVariable(value = "todo-id") String todoId,
            @RequestParam(value = "is_done", defaultValue = "false") boolean status
    ) {
        TodoResponse todoResponse = todoService.updateTodoStatus(todoId, status);
        return new BaseResponse<>(todoResponse);
    }

    @DeleteMapping("/{todo-id}")
    public BaseResponse<Map<String, Object>> deleteTodoById(@PathVariable(value = "todo-id") String todoId) {
        todoService.deleteTodo(todoId);
        return new BaseResponse<>(Map.of("success", true));
    }

}
