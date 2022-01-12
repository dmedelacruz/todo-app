package com.todoapp.service;

import com.todoapp.dao.request.TodoRequest;
import com.todoapp.dao.response.TodoResponse;
import com.todoapp.database.entity.Todo;
import com.todoapp.database.entity.TodoList;
import com.todoapp.database.repository.TodoListRepository;
import com.todoapp.database.repository.TodoRepository;
import com.todoapp.exception.IllegalOperationException;
import com.todoapp.exception.RecordNotFoundException;
import com.todoapp.utils.MapperService;
import com.todoapp.utils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final MapperService mapperService;
    private final UserService userService;

    @Transactional
    public TodoResponse createTodo(String todoListId, TodoRequest todoRequest) {

        TodoList todoList = todoListRepository.findOneById(todoListId);

        if(todoList == null) {
            throw new RecordNotFoundException("Todo List not found");
        }

        if(userService.isLoggedInUser(todoList.getOwner())) {

            Todo todo = new Todo();
            todo.setDescription(todoRequest.getDescription());
            todo.setDone(false);
            todo.setTodoList(todoList);

            Todo persistedTodo = todoRepository.save(todo);
            return mapperService.mapTodoResponse(persistedTodo);

        } else {
            throw new IllegalOperationException("Only owner can create todo");
        }

    }

    @Transactional
    public TodoResponse updateTodo(String todoId, TodoRequest todoRequest) {

        Todo todo = todoRepository.findOneById(todoId);

        if(todo == null) {
            throw new RecordNotFoundException("Todo not found");
        }

        TodoList todoList = todo.getTodoList();

        if(userService.isLoggedInUser(todoList.getOwner())) {

            todo.setDescription(todoRequest.getDescription());

            Todo updatedTodo = todoRepository.save(todo);
            return mapperService.mapTodoResponse(updatedTodo);

        } else {
            throw new IllegalOperationException("Only owner can edit todo");
        }
    }

    @Transactional
    public TodoResponse updateTodoStatus(String todoId, boolean isDone) {

        Todo todo = todoRepository.findOneById(todoId);

        if(todo == null) {
            throw new RecordNotFoundException("Todo not found");
        }

        TodoList todoList = todo.getTodoList();

        if(userService.isLoggedInUser(todoList.getOwner())) {

            todo.setDone(isDone);

            Todo updatedTodo = todoRepository.save(todo);
            return mapperService.mapTodoResponse(updatedTodo);

        } else {
            throw new IllegalOperationException("Only owner can update todo status");
        }

    }

    @Transactional
    public void deleteTodo(String todoId) {

        Todo todo = todoRepository.findOneById(todoId);

        if(todo == null) {
            throw new RecordNotFoundException("Todo not found");
        }

        TodoList todoList = todo.getTodoList();

        if(userService.isLoggedInUser(todoList.getOwner())) {
            todoRepository.delete(todo);
        } else {
            throw new IllegalOperationException("Only owner can update todo status");
        }
    }



}
