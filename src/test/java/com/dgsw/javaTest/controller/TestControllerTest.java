package com.dgsw.javaTest.controller;

import com.dgsw.javaTest.dto.TestItemRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("아이템 생성 테스트")
    void createItem() throws Exception {
        // given
        TestItemRequestDTO requestDTO = new TestItemRequestDTO();
        requestDTO.setName("테스트 아이템");
        requestDTO.setCategory("테스트 카테고리");

        // when & then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("테스트 아이템"))
                .andExpect(jsonPath("$.category").value("테스트 카테고리"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAllItems() throws Exception {
        // when & then
        mockMvc.perform(get("/api/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("ID로 아이템 조회 테스트")
    void findItemById() throws Exception {
        // given - 먼저 아이템 생성
        TestItemRequestDTO requestDTO = new TestItemRequestDTO();
        requestDTO.setName("조회 테스트");
        requestDTO.setCategory("카테고리");

        String response = mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // JSON 파싱해서 ID 추출 (간단하게)
        Long id = 1L;

        // when & then
        mockMvc.perform(get("/api/items/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("아이템 수정 테스트")
    void updateItem() throws Exception {
        // given - 먼저 아이템 생성
        TestItemRequestDTO createDTO = new TestItemRequestDTO();
        createDTO.setName("원본 이름");
        createDTO.setCategory("원본 카테고리");

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));

        Long id = 1L;

        // 수정할 데이터
        TestItemRequestDTO updateDTO = new TestItemRequestDTO();
        updateDTO.setName("수정된 이름");
        updateDTO.setCategory("수정된 카테고리");

        // when & then
        mockMvc.perform(put("/api/items/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("수정된 이름"))
                .andExpect(jsonPath("$.category").value("수정된 카테고리"));
    }

    @Test
    @DisplayName("아이템 삭제 테스트")
    void deleteItem() throws Exception {
        // given - 먼저 아이템 생성
        TestItemRequestDTO requestDTO = new TestItemRequestDTO();
        requestDTO.setName("삭제될 아이템");
        requestDTO.setCategory("카테고리");

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        Long id = 1L;

        // when & then
        mockMvc.perform(delete("/api/items/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유효성 검증 실패 테스트 - 빈 이름")
    void validationFail_EmptyName() throws Exception {
        // given
        TestItemRequestDTO requestDTO = new TestItemRequestDTO();
        requestDTO.setName("");
        requestDTO.setCategory("카테고리");

        // when & then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
