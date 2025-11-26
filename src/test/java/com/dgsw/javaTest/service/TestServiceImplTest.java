package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemRequestDTO;
import com.dgsw.javaTest.dto.TestItemResponseDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TestServiceImpl testService;

    private TestItem testItem;
    private TestItemRequestDTO requestDTO;
    private TestItemResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        testItem = new TestItem("테스트 아이템", "테스트 카테고리");
        
        requestDTO = new TestItemRequestDTO();
        requestDTO.setName("테스트 아이템");
        requestDTO.setCategory("테스트 카테고리");

        responseDTO = new TestItemResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("테스트 아이템");
        responseDTO.setCategory("테스트 카테고리");
    }

    @Test
    @DisplayName("아이템 저장 성공 테스트")
    void save_Success() {
        // given
        when(modelMapper.map(any(TestItemRequestDTO.class), eq(TestItem.class))).thenReturn(testItem);
        when(testRepository.save(any(TestItem.class))).thenReturn(testItem);
        when(modelMapper.map(any(TestItem.class), eq(TestItemResponseDTO.class))).thenReturn(responseDTO);

        // when
        TestItemResponseDTO result = testService.save(requestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트 아이템");
        verify(testRepository, times(1)).save(any(TestItem.class));
    }

    @Test
    @DisplayName("ID로 아이템 조회 성공 테스트")
    void findById_Success() {
        // given
        when(testRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(modelMapper.map(any(TestItem.class), eq(TestItemResponseDTO.class))).thenReturn(responseDTO);

        // when
        TestItemResponseDTO result = testService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(testRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAll_Success() {
        // given
        TestItem item1 = new TestItem("아이템1", "카테고리1");
        TestItem item2 = new TestItem("아이템2", "카테고리2");
        when(testRepository.findAll()).thenReturn(Arrays.asList(item1, item2));
        when(modelMapper.map(any(TestItem.class), eq(TestItemResponseDTO.class))).thenReturn(responseDTO);

        // when
        List<TestItemResponseDTO> results = testService.findAll();

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(2);
        verify(testRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("아이템 수정 성공 테스트")
    void update_Success() {
        // given
        when(testRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(modelMapper.map(any(TestItem.class), eq(TestItemResponseDTO.class))).thenReturn(responseDTO);

        // when
        TestItemResponseDTO result = testService.update(1L, requestDTO);

        // then
        assertThat(result).isNotNull();
        verify(testRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(requestDTO, testItem);
    }

    @Test
    @DisplayName("아이템 삭제 성공 테스트")
    void delete_Success() {
        // given
        when(testRepository.existsById(1L)).thenReturn(true);
        doNothing().when(testRepository).deleteById(1L);

        // when
        boolean result = testService.deleteById(1L);

        // then
        assertThat(result).isTrue();
        verify(testRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 아이템 삭제 실패 테스트")
    void delete_NotFound() {
        // given
        when(testRepository.existsById(999L)).thenReturn(false);

        // when
        boolean result = testService.deleteById(999L);

        // then
        assertThat(result).isFalse();
        verify(testRepository, never()).deleteById(999L);
    }
}
