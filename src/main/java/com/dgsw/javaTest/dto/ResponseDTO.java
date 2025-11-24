package com.dgsw.javaTest.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private String status;
    private List<TestItemDTO> testItemDTO;
}
