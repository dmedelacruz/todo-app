package com.todoapp.database.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todo")
public class Todo extends BaseEntity{

    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

}
