package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public TestItemDTO save(TestItemDTO testItemDTO) {
        TestItem saved = testRepository.save(new TestItem(testItemDTO.getName(), testItemDTO.getCategory()));
        return saved.getId() != null
                ? new TestItemDTO(saved.getId(), saved.getName(), saved.getCategory(), saved.getCreatedAt())
                : null;
    }

    private TestItemDTO toDTO(TestItem testItem) {
        TestItemDTO testItemDTO = new TestItemDTO();
        testItemDTO.setId(testItem.getId());
        testItemDTO.setName(testItem.getName());
        testItemDTO.setCategory(testItem.getCategory());
        return testItemDTO;
    }

    public TestItemDTO findById(Long id) {
        Optional<TestItem> testItem = testRepository.findById(id);
        return testItem.map(this::toDTO).orElse(null);
    }

    public List<TestItemDTO> findAll() {
        return testRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<TestItemDTO> findByName(String name) {
        return testRepository.findByName(name).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TestItemDTO update(TestItemDTO testItemDTO) {
        Optional<TestItem> optional = testRepository.findById(testItemDTO.getId());
        if (optional.isPresent()) {
            TestItem testItem = optional.get();
            testItem.setName(testItemDTO.getName());
            testItem.setCategory(testItemDTO.getCategory());
            testRepository.save(testItem);
            return new TestItemDTO(testItem.getId(), testItem.getName(), testItem.getCategory(), testItem.getCreatedAt());
        }
        return null;
    }

    public boolean deleteById(Long id) {
        if (testRepository.existsById(id)) {
            testRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
