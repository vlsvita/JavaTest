package com.dgsw.javaTest.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private HttpStatus status;
    private List<TestItemDTO> testItemDTO;
}
