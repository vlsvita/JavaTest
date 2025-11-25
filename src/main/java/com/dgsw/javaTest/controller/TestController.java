package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.ResponseDTO;
import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public ResponseDTO save(@Validated @RequestBody TestItemDTO testItemDTO) {
        TestItemDTO saved = testService.save(testItemDTO);
        return saved != null
                ? new ResponseDTO(HttpStatus.CREATED, List.of(testItemDTO))
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
    }

    @GetMapping("/{id}")
    public ResponseDTO findById(@PathVariable("id") Long id) {
        TestItemDTO result = testService.findById(id);
        return result == null
                ? new ResponseDTO(HttpStatus.BAD_REQUEST, null)
                : new ResponseDTO(HttpStatus.OK, List.of(result));
    }

    @GetMapping
    public ResponseDTO findAll() {
        List<TestItemDTO> results = testService.findAll();
        return new ResponseDTO(HttpStatus.OK, results);
    }

    @GetMapping("/{name}")
    public ResponseDTO findByName(@PathVariable("name") String name) {
        List<TestItemDTO> results = testService.findByName(name);
        return new ResponseDTO(HttpStatus.OK, results);
    }

    @PutMapping
    public ResponseDTO update(@Validated @RequestBody TestItemDTO testItemDTO) {
        TestItemDTO updated = testService.update(testItemDTO);
        return updated != null
                ? new ResponseDTO(HttpStatus.OK, List.of(updated))
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO removeById(@PathVariable("id") Long id) {
        boolean result = testService.deleteById(id);
        return result
                ? new ResponseDTO(HttpStatus.OK, null)
                : new ResponseDTO(HttpStatus.BAD_REQUEST, null);
    }
}
