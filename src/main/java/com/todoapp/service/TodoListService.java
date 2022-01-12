package com.todoapp.service;

import com.todoapp.dao.request.TodoListRequest;
import com.todoapp.dao.response.TodoListResponse;
import com.todoapp.database.entity.TodoList;
import com.todoapp.database.repository.TodoListRepository;
import com.todoapp.exception.IllegalOperationException;
import com.todoapp.exception.RecordNotFoundException;
import com.todoapp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;

    public List<TodoListResponse> getAllTodoLists(List<String> owners) {
        if(owners.isEmpty()) {
            owners.add(UserUtil.getLoggedInUserId());
        }
        List<TodoList> todoLists = todoListRepository.findAllByOwnerIn(owners);
        return todoLists.stream().map(this::mapTodoListResponse).collect(Collectors.toList());
    }

    public TodoListResponse getTodoListById(String todoListId) {
        TodoList todoList = todoListRepository.findOneById(todoListId);
        return mapTodoListResponse(todoList);
    }

    @Transactional
    public TodoListResponse createTodoList(TodoListRequest todoListRequest) {
        TodoList todoList = new TodoList();
        todoList.setHeader(todoListRequest.getHeader());
        todoList.setOwner(UserUtil.getLoggedInUserId());

        TodoList persistedTodoList = todoListRepository.save(todoList);

        return mapTodoListResponse(persistedTodoList);
    }

    @Transactional
    public TodoListResponse updateTodoList(String todoListId, TodoListRequest todoListRequest) {

        TodoList todoList = todoListRepository.findOneById(todoListId);

        if(todoList == null) {
            throw new RecordNotFoundException("Record not found");
        }

        if (UserUtil.isLoggedInUser(todoList.getOwner())) {

            todoList.setHeader(todoListRequest.getHeader());
            TodoList updatedTodoList = todoListRepository.save(todoList);

            return mapTodoListResponse(updatedTodoList);

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

        if (UserUtil.isLoggedInUser(todoList.getOwner())) {
            todoListRepository.delete(todoList);
        } else {
            throw new IllegalOperationException("Only owner can delete");
        }

    }

//    @Transactional
//    public void deleteTodoLists(List<String> todoListIds) {
//        todoListRepository.deleteAllById(todoListIds);
//    }

    private TodoListResponse mapTodoListResponse(TodoList todoList) {
        return new TodoListResponse(
                todoList.getId(),
                todoList.getHeader(),
                todoList.getCreatedDate(),
                todoList.getModifiedDate(),
                todoList.getOwner()
        );
    }
}
