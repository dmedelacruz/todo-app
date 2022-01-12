package com.todoapp.utils;

import com.todoapp.dao.response.TodoListResponse;
import com.todoapp.dao.response.TodoResponse;
import com.todoapp.database.entity.Todo;
import com.todoapp.database.entity.TodoList;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public final class MapperService {

    public TodoResponse mapTodoResponse(Todo todo) {
        return  new TodoResponse(
                todo.getId(),
                todo.getDescription(),
                todo.getCreatedDate(),
                todo.getModifiedDate(),
                todo.isDone()
        );
    }

    public TodoListResponse mapTodoListResponse(TodoList todoList) {
        return new TodoListResponse(
                todoList.getId(),
                todoList.getHeader(),
                todoList.getCreatedDate(),
                todoList.getModifiedDate(),
                todoList.getOwner(),
                todoList.getTodos() == null ? Collections.emptyList() : todoList.getTodos().stream().map(this::mapTodoResponse).collect(Collectors.toList())
        );
    }

}
