package com.todoapp.database.repository;

import com.todoapp.database.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, String> {

    List<TodoList> findAllByOwnerIn(List<String> owners);

    @Query(value = "SELECT * FROM todo_list WHERE id = ?1", nativeQuery = true)
    TodoList findOneById(String id);

    TodoList findByIdAndOwner(String id, String owner);
}
