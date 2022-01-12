package com.todoapp.dao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TodoRequest {

    @JsonProperty("description")
    private String description;

}
