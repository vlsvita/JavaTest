package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemRequestDTO;
import com.dgsw.javaTest.dto.TestItemResponseDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.repository.TestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    public TestServiceImpl(TestRepository testRepository, ModelMapper modelMapper) {
        this.testRepository = testRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public TestItemResponseDTO save(TestItemRequestDTO dto) {
        TestItem testItem = modelMapper.map(dto, TestItem.class);
        TestItem saved = testRepository.save(testItem);
        return modelMapper.map(saved, TestItemResponseDTO.class);
    }

    private TestItemResponseDTO toResponseDTO(TestItem testItem) {
        return modelMapper.map(testItem, TestItemResponseDTO.class);
    }

    public TestItemResponseDTO findById(Long id) {
        return testRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    public List<TestItemResponseDTO> findAll() {
        return testRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<TestItemResponseDTO> findByName(String name) {
        return testRepository.findByName(name).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public TestItemResponseDTO update(Long id, TestItemRequestDTO dto) {
        return testRepository.findById(id)
                .map(testItem -> {
                    modelMapper.map(dto, testItem); // DTO → Entity 매핑
                    return modelMapper.map(testItem, TestItemResponseDTO.class);
                })
                .orElse(null);
    }

    public boolean deleteById(Long id) {
        if (testRepository.existsById(id)) {
            testRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

