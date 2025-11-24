package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.ResponseDTO;
import com.dgsw.javaTest.dto.TestItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestControllerTest {
    @Autowired
    private TestController testController;

    @Test
    void save() {
        ResponseDTO result = testController.save(new TestItemDTO(null, "asdf", "sadf"));
        Assertions.assertNotNull(result);
    }

    @Test
    void findByName() {

    }
}