package com.todoapp.dao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TodoResponse {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("created_date")
    private final Date createdDate;

    @JsonProperty("modified_date")
    private final Date modifiedDate;

    @JsonProperty("is_done")
    private final Boolean isDone;

}
