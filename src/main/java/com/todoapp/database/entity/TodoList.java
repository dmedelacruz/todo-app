package com.todoapp.database.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "todo_list")
public class TodoList extends BaseEntity {

    @Column(name = "header", nullable = false)
    private String header;

    @OneToMany(mappedBy = "todoList")
    private List<Todo> todos;

    @Column(name = "owner", nullable = false)
    private String owner;

}
