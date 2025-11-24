package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public Map<String, Object> register(@RequestBody TestItemDTO testItemDTO) {
        boolean saved = testService.save(testItemDTO);
        return saved
                ? Map.of("res", "fail", "msg", "item save fail")
                : Map.of("res", "success", "data", saved);
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") Long id) {
        TestItemDTO result = testService.findById(id);
        return result == null
                ? Map.of("res", "fail", "msg", "item search fail")
                : Map.of("res", "success", "data", result);
    }

    @GetMapping
    public Map<String, Object> findAll() {
        List<TestItemDTO> results = testService.findAll();
        return Map.of("res", "success", "data", results);
    }

    @PutMapping
    public Map<String, Object> update(@RequestBody TestItemDTO testItemDTO) {
        boolean updated = testService.update(testItemDTO);
        return updated
                ? Map.of("res", "success", "data", testItemDTO)
                : Map.of("res", "fail", "msg", "item update fail");
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> removeById(@PathVariable("id") Long id) {
        boolean result = testService.deleteById(id);
        return result
                ? Map.of("res", "success", "msg", "item delete success")
                :  Map.of("res", "fail", "msg", "item delete fail");
    }
}
