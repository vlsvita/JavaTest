package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.ApiResponse;
import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.service.TestService;
<<<<<<< Updated upstream
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
=======
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Test Items", description = "테스트 아이템 관리 API")
@RestController
@RequestMapping("/api/items")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Operation(summary = "아이템 생성", description = "새로운 테스트 아이템을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "아이템 생성 성공",
                    content = @Content(schema = @Schema(implementation = TestItemDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<TestItemDTO>> save(
            @Valid @RequestBody TestItemDTO testItemDTO) {
        TestItemDTO saved = testService.save(testItemDTO);
<<<<<<< Updated upstream
        return saved != null
                ? new ResponseDTO(HttpStatus.CREATED, List.of(testItemDTO))
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
=======
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("아이템이 생성되었습니다.", saved));
>>>>>>> Stashed changes
    }

    @Operation(summary = "아이템 조회", description = "ID로 아이템을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "아이템을 찾을 수 없음"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestItemDTO>> findById(
            @Parameter(description = "아이템 ID", required = true)
            @PathVariable("id") Long id) {
        TestItemDTO result = testService.findById(id);
<<<<<<< Updated upstream
        return result == null
                ? new ResponseDTO(HttpStatus.BAD_REQUEST, null)
                : new ResponseDTO(HttpStatus.OK, List.of(result));
=======
        return ResponseEntity.ok(ApiResponse.success(result));
>>>>>>> Stashed changes
    }

    @Operation(summary = "전체 아이템 조회", description = "모든 아이템을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestItemDTO>>> findAll() {
        List<TestItemDTO> results = testService.findAll();
<<<<<<< Updated upstream
        return new ResponseDTO(HttpStatus.OK, results);
=======
        return ResponseEntity.ok(ApiResponse.success(results));
>>>>>>> Stashed changes
    }

    @Operation(summary = "이름으로 검색", description = "아이템 이름으로 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TestItemDTO>>> findByName(
            @Parameter(description = "검색할 아이템 이름", required = true)
            @RequestParam("name") String name) {
        List<TestItemDTO> results = testService.findByName(name);
<<<<<<< Updated upstream
        return new ResponseDTO(HttpStatus.OK, results);
=======
        return ResponseEntity.ok(ApiResponse.success(results));
>>>>>>> Stashed changes
    }

    @Operation(summary = "카테고리로 조회", description = "카테고리별로 아이템을 조회합니다.")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<TestItemDTO>>> findByCategory(
            @Parameter(description = "카테고리명", required = true)
            @PathVariable("category") String category) {
        List<TestItemDTO> results = testService.findByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @Operation(summary = "아이템 수정", description = "아이템 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "수정 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "아이템을 찾을 수 없음"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TestItemDTO>> update(
            @Parameter(description = "아이템 ID", required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody TestItemDTO testItemDTO) {
        testItemDTO.setId(id);
        TestItemDTO updated = testService.update(testItemDTO);
<<<<<<< Updated upstream
        return updated != null
                ? new ResponseDTO(HttpStatus.OK, List.of(updated))
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
=======
        return ResponseEntity.ok(ApiResponse.success("아이템이 수정되었습니다.", updated));
>>>>>>> Stashed changes
    }

    @Operation(summary = "아이템 삭제", description = "아이템을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "삭제 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "아이템을 찾을 수 없음"
            )
    })
    @DeleteMapping("/{id}")
<<<<<<< Updated upstream
    public ResponseDTO removeById(@PathVariable("id") Long id) {
        boolean result = testService.deleteById(id);
        return result
                ? new ResponseDTO(HttpStatus.OK, null)
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
=======
    public ResponseEntity<ApiResponse<Void>> deleteById(
            @Parameter(description = "아이템 ID", required = true)
            @PathVariable("id") Long id) {
        testService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("아이템이 삭제되었습니다.", null));
>>>>>>> Stashed changes
    }
}
