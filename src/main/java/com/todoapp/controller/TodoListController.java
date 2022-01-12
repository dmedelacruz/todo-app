package com.todoapp.controller;

import com.todoapp.dao.request.TodoListRequest;
import com.todoapp.dao.response.BaseResponse;
import com.todoapp.dao.response.TodoListResponse;
import com.todoapp.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo-lists")
@PreAuthorize("isAuthenticated()")
public class TodoListController {

    private final TodoListService todoListService;

    @GetMapping
    public BaseResponse<List<TodoListResponse>> getAllTodoLists(@RequestParam(value = "owner", defaultValue = "") List<String> owners) {
        List<TodoListResponse> allTodoLists = todoListService.getAllTodoLists(owners);
        return new BaseResponse<>(allTodoLists);
    }

    @GetMapping("/{todo-list-id}")
    public BaseResponse<TodoListResponse> getTodoListById(@PathVariable(value = "todo-list-id") String todoListId) {
        TodoListResponse todoList = todoListService.getTodoListById(todoListId);
        return new BaseResponse<>(todoList);
    }

    @PostMapping
    public BaseResponse<TodoListResponse> createTodoList(@RequestBody TodoListRequest todoListRequest) {
        TodoListResponse todoListResponse = todoListService.createTodoList(todoListRequest);
        return new BaseResponse<>(todoListResponse);
    }

    @PutMapping("/{todo-list-id}")
    public BaseResponse<TodoListResponse> updateTodoList(
            @PathVariable(value = "todo-list-id") String todoListId,
            @RequestBody TodoListRequest todoListRequest
    ) {
        TodoListResponse todoListResponse = todoListService.updateTodoList(todoListId, todoListRequest);
        return new BaseResponse<>(todoListResponse);
    }

    @DeleteMapping("/{todo-list-id}")
    public BaseResponse<Map<String, Object>> deleteTodoList(@PathVariable(value = "todo-list-id") String todoListId) {
        todoListService.deleteTodoListById(todoListId);
        return new BaseResponse<>(Map.of("success", true));
    }

}
