package com.dgsw.javaTest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "테스트 아이템 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TestItemDTO {
    @Schema(description = "아이템 ID", example = "1")
    private Long id;
    
    @Schema(description = "아이템 이름", example = "노트북", required = true)
    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 20, message = "이름은 최대 20자까지 입력 가능합니다.")
    private String name;
    
    @Schema(description = "아이템 카테고리", example = "전자제품", required = true)
    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;
}
