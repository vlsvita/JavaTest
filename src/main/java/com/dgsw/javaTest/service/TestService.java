package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemRequestDTO;
import com.dgsw.javaTest.dto.TestItemResponseDTO;
import java.util.List;

public interface TestService {
    TestItemResponseDTO save(TestItemRequestDTO testItemDTO);
    TestItemResponseDTO findById(Long id);
    List<TestItemResponseDTO> findAll();
    List<TestItemResponseDTO> findByName(String name);
    TestItemResponseDTO update(Long id, TestItemRequestDTO testItemDTO);
    boolean deleteById(Long id);
}
