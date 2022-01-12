package com.todoapp.database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "todo_list")
public class TodoList extends BaseEntity {

    @Column(name = "header", nullable = false)
    private String header;

    @OneToMany(mappedBy = "todoList", fetch = FetchType.LAZY)
    private List<Todo> todos;

    @Column(name = "owner", nullable = false)
    private String owner;

}
