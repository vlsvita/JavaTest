package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;

import java.util.List;

public interface TestService {
    boolean save(TestItemDTO testItemDTO);
    TestItemDTO findById(Long id);
    List<TestItemDTO> findAll();
    List<TestItemDTO> findByName(String name);
    boolean update(TestItemDTO testItemDTO);
    boolean deleteById(Long id);
}
