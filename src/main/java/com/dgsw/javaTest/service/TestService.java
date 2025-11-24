package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;

import java.util.List;

public interface TestService {
    TestItemDTO save(TestItemDTO testItemDTO);
    TestItemDTO findById(Long id);
    List<TestItemDTO> findAll();
    List<TestItemDTO> findByName(String name);
    TestItemDTO update(TestItemDTO testItemDTO);
    boolean deleteById(Long id);
}
