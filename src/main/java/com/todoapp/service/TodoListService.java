package com.todoapp.service;

import com.todoapp.dao.request.TodoListRequest;
import com.todoapp.dao.response.TodoListResponse;
import com.todoapp.database.entity.TodoList;
import com.todoapp.database.repository.TodoListRepository;
import com.todoapp.exception.IllegalOperationException;
import com.todoapp.exception.RecordNotFoundException;
import com.todoapp.utils.MapperService;
import com.todoapp.utils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final MapperService mapperService;
    private final UserService userService;

    public List<TodoListResponse> getAllTodoLists(List<String> owners) {
        if(owners.isEmpty()) {
            owners.add(userService.getLoggedInUserId());
        }
        List<TodoList> todoLists = todoListRepository.findAllByOwnerIn(owners);
        return todoLists.stream().map(mapperService::mapTodoListResponse).collect(Collectors.toList());
    }

    public TodoListResponse getTodoListById(String todoListId) {
        TodoList todoList = todoListRepository.findOneById(todoListId);
        return mapperService.mapTodoListResponse(todoList);
    }

    @Transactional
    public TodoListResponse createTodoList(TodoListRequest todoListRequest) {
        TodoList todoList = new TodoList();
        todoList.setHeader(todoListRequest.getHeader());
        todoList.setOwner(userService.getLoggedInUserId());

        TodoList persistedTodoList = todoListRepository.save(todoList);

        return mapperService.mapTodoListResponse(persistedTodoList);
    }

    @Transactional
    public TodoListResponse updateTodoList(String todoListId, TodoListRequest todoListRequest) {

        TodoList todoList = todoListRepository.findOneById(todoListId);

        if(todoList == null) {
            throw new RecordNotFoundException("Record not found");
        }

        if (userService.isLoggedInUser(todoList.getOwner())) {

            todoList.setHeader(todoListRequest.getHeader());
            TodoList updatedTodoList = todoListRepository.save(todoList);

            return mapperService.mapTodoListResponse(updatedTodoList);

        } else {
            throw new IllegalOperationException("Only owner can update");
        }
    }

    @Transactional
    public void deleteTodoListById(String todoListId) {

        TodoList todoList = todoListRepository.findOneById(todoListId);

        if(todoList == null) {
            throw new RecordNotFoundException("Record not found");
        }

        if (userService.isLoggedInUser(todoList.getOwner())) {
            todoListRepository.delete(todoList);
        } else {
            throw new IllegalOperationException("Only owner can delete");
        }

    }

}
