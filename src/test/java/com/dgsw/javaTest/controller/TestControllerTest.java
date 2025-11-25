package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private TestItemDTO testItemDTO;
    
    @BeforeEach
    void setUp() {
        testItemDTO = new TestItemDTO();
        testItemDTO.setName("테스트 아이템");
        testItemDTO.setCategory("테스트 카테고리");
    }
    
    @Test
    @DisplayName("아이템 생성 테스트")
    void save() throws Exception {
        // when & then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testItemDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("테스트 아이템"))
                .andExpect(jsonPath("$.data.category").value("테스트 카테고리"));
    }
    
    @Test
    @DisplayName("아이템 ID로 조회 테스트")
    void findById() throws Exception {
        // given - 더미 데이터가 data.sql에서 자동 삽입됨
        Long itemId = 1L;
        
        // when & then
        mockMvc.perform(get("/api/items/{id}", itemId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").value(itemId));
    }
    
    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAll() throws Exception {
        // when & then
        mockMvc.perform(get("/api/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(greaterThan(0))));
    }
    
    @Test
    @DisplayName("이름으로 아이템 검색 테스트")
    void findByName() throws Exception {
        // given
        String searchName = "노트북";
        
        // when & then
        mockMvc.perform(get("/api/items/search")
                        .param("name", searchName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray());
    }
    
    @Test
    @DisplayName("카테고리로 아이템 조회 테스트")
    void findByCategory() throws Exception {
        // given
        String category = "전자제품";
        
        // when & then
        mockMvc.perform(get("/api/items/category/{category}", category))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].category").value(category));
    }
    
    @Test
    @DisplayName("아이템 수정 테스트")
    void update() throws Exception {
        // given
        Long itemId = 1L;
        TestItemDTO updateDTO = new TestItemDTO();
        updateDTO.setName("수정된 이름");
        updateDTO.setCategory("수정된 카테고리");
        
        // when & then
        mockMvc.perform(put("/api/items/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("수정된 이름"))
                .andExpect(jsonPath("$.data.category").value("수정된 카테고리"));
    }
    
    @Test
    @DisplayName("아이템 삭제 테스트")
    void deleteById() throws Exception {
        // given
        Long itemId = 1L;
        
        // when & then
        mockMvc.perform(delete("/api/items/{id}", itemId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }
    
    @Test
    @DisplayName("존재하지 않는 아이템 조회 시 404 에러 테스트")
    void findById_NotFound() throws Exception {
        // given
        Long nonExistentId = 99999L;
        
        // when & then
        mockMvc.perform(get("/api/items/{id}", nonExistentId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"));
    }
    
    @Test
    @DisplayName("유효성 검증 실패 테스트 - 빈 이름")
    void save_ValidationFail_EmptyName() throws Exception {
        // given
        TestItemDTO invalidDTO = new TestItemDTO();
        invalidDTO.setName("");
        invalidDTO.setCategory("카테고리");
        
        // when & then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"));
    }
}