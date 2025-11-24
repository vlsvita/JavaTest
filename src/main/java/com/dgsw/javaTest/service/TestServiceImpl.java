package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public boolean save(TestItemDTO testItemDTO) {
        TestItem testItem = new TestItem(testItemDTO.getName(), testItemDTO.getCategory());
        TestItem saved = testRepository.save(testItem);
        return saved.getId() != null;
    }

    public TestItemDTO findById(Long id) {
        Optional<TestItem> testItem = testRepository.findById(id);
        if (testItem.isPresent()) {
            TestItemDTO testItemDTO = new TestItemDTO();
            testItemDTO.setId(testItem.get().getId());
            testItemDTO.setName(testItem.get().getName());
            testItemDTO.setCategory(testItem.get().getCategory());
            return testItemDTO;
        }
        return null;
    }

    public List<TestItemDTO> findAll() {
        return testRepository.findAll().stream().map(entity -> {
            TestItemDTO testItemDTO = new TestItemDTO();
            testItemDTO.setId(entity.getId());
            testItemDTO.setName(entity.getName());
            testItemDTO.setCategory(entity.getCategory());
            return testItemDTO;
        }).collect(Collectors.toList());
    }

    public List<TestItemDTO> findByName(String name) {
        return testRepository.findByName(name).stream().map(entity -> {
            TestItemDTO testItemDTO = new TestItemDTO();
            testItemDTO.setId(entity.getId());
            testItemDTO.setName(entity.getName());
            testItemDTO.setCategory(entity.getCategory());
            return testItemDTO;
        }).collect(Collectors.toList());
    }

    public boolean updateById(TestItemDTO testItemDTO) {
        Optional<TestItem> optional = testRepository.findById(testItemDTO.getId());
        if (optional.isPresent()) {
            TestItem testItem = optional.get();
            testItem.setName(testItemDTO.getName());
            testItem.setCategory(testItemDTO.getCategory());
            testRepository.save(testItem);
            return true;
        }
        return false;
    }

    public boolean deleteById(Long id) {
        if (testRepository.existsById(id)) {
            testRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
