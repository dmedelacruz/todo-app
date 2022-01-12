package com.todoapp.database.repository;

import com.todoapp.database.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, String> {

    @Query(value = "SELECT * FROM todo WHERE id = ?1", nativeQuery = true)
    Todo findOneById(String id);
}
