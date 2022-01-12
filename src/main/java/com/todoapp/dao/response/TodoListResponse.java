package com.todoapp.dao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TodoListResponse {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("header")
    private final String header;

    @JsonProperty("created_date")
    private final Date createdDate;

    @JsonProperty("modified_date")
    private final Date modifiedDate;

    @JsonProperty("owner")
    private final String owner;

    //TODO add todos

}
