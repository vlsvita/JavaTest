package com.dgsw.javaTest.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TestItemDTO {
    private Long id;
    private String name;
    private String category;
}
