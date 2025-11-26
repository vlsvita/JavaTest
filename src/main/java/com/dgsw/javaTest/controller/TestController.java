package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.TestItemRequestDTO;
import com.dgsw.javaTest.dto.TestItemResponseDTO;
import com.dgsw.javaTest.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // POST: 클라이언트가 보내는 DTO (id 없음)
    @PostMapping
    public ResponseEntity<TestItemResponseDTO> save(@Validated @RequestBody TestItemRequestDTO dto) {
        TestItemResponseDTO saved = testService.save(dto);
        return saved != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(saved)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // GET: id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<TestItemResponseDTO> findById(@PathVariable Long id) {
        TestItemResponseDTO result = testService.findById(id);
        return result != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET: 전체 조회
    @GetMapping
    public ResponseEntity<List<TestItemResponseDTO>> findAll() {
        return ResponseEntity.ok(testService.findAll());
    }

    // GET: 이름으로 검색
    @GetMapping("/search")
    public ResponseEntity<List<TestItemResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(testService.findByName(name));
    }

    // PUT: 수정은 id를 PathVariable로 전달, 클라이언트 DTO에는 id 없음
    @PutMapping("/{id}")
    public ResponseEntity<TestItemResponseDTO> update(@PathVariable Long id, @Validated @RequestBody TestItemRequestDTO dto) {
        TestItemResponseDTO updated = testService.update(id, dto);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = testService.deleteById(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
