package com.dgsw.javaTest.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private HttpStatus status;
    private T data;
}
