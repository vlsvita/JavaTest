package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.exception.ResourceNotFoundException;
import com.dgsw.javaTest.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    @Transactional
    public TestItemDTO save(TestItemDTO testItemDTO) {
        TestItem testItem = new TestItem(testItemDTO.getName(), testItemDTO.getCategory());
        TestItem saved = testRepository.save(testItem);
        return convertToDTO(saved);
    }

    @Override
    public TestItemDTO findById(Long id) {
        TestItem testItem = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestItem", id));
        return convertToDTO(testItem);
    }

    @Override
    public List<TestItemDTO> findAll() {
        return testRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestItemDTO> findByName(String name) {
        return testRepository.findByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestItemDTO> findByCategory(String category) {
        return testRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestItemDTO update(TestItemDTO testItemDTO) {
        TestItem testItem = testRepository.findById(testItemDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("TestItem", testItemDTO.getId()));
        
        testItem.setName(testItemDTO.getName());
        testItem.setCategory(testItemDTO.getCategory());
        
        return convertToDTO(testItem);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!testRepository.existsById(id)) {
            throw new ResourceNotFoundException("TestItem", id);
        }
        testRepository.deleteById(id);
    }

    private TestItemDTO convertToDTO(TestItem testItem) {
        TestItemDTO dto = new TestItemDTO();
        dto.setId(testItem.getId());
        dto.setName(testItem.getName());
        dto.setCategory(testItem.getCategory());
        return dto;
    }
}
