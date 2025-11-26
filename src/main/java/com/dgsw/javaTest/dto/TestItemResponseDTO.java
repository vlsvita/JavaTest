package com.dgsw.javaTest.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TestItemResponseDTO {
    private Long id;
    private String name;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
