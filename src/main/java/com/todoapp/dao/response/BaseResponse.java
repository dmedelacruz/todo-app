package com.todoapp.dao.response;

import lombok.Data;

@Data
public final class BaseResponse<T> {
    private final T data;
}
