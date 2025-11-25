package com.dgsw.javaTest.service;

import com.dgsw.javaTest.dto.TestItemDTO;
import com.dgsw.javaTest.entity.TestItem;
import com.dgsw.javaTest.exception.ResourceNotFoundException;
import com.dgsw.javaTest.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestServiceImpl testService;

    private TestItem testItem;
    private TestItemDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testItem = new TestItem(1L, "테스트 아이템", "테스트 카테고리");
        testItemDTO = new TestItemDTO();
        testItemDTO.setName("테스트 아이템");
        testItemDTO.setCategory("테스트 카테고리");
    }

    @Test
    @DisplayName("아이템 저장 성공 테스트")
    void save_Success() {
        // given
        when(testRepository.save(any(TestItem.class))).thenReturn(testItem);

        // when
        TestItemDTO result = testService.save(testItemDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트 아이템");
        assertThat(result.getCategory()).isEqualTo("테스트 카테고리");
        verify(testRepository, times(1)).save(any(TestItem.class));
    }

    @Test
    @DisplayName("ID로 아이템 조회 성공 테스트")
    void findById_Success() {
        // given
        when(testRepository.findById(1L)).thenReturn(Optional.of(testItem));

        // when
        TestItemDTO result = testService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("테스트 아이템");
        verify(testRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외 발생 테스트")
    void findById_NotFound() {
        // given
        when(testRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> testService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("TestItem");
    }

    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAll_Success() {
        // given
        TestItem item1 = new TestItem(1L, "아이템1", "카테고리1");
        TestItem item2 = new TestItem(2L, "아이템2", "카테고리2");
        when(testRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        // when
        List<TestItemDTO> results = testService.findAll();

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getName()).isEqualTo("아이템1");
        assertThat(results.get(1).getName()).isEqualTo("아이템2");
    }

    @Test
    @DisplayName("이름으로 아이템 검색 테스트")
    void findByName_Success() {
        // given
        when(testRepository.findByName("테스트 아이템"))
                .thenReturn(Arrays.asList(testItem));

        // when
        List<TestItemDTO> results = testService.findByName("테스트 아이템");

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("테스트 아이템");
    }

    @Test
    @DisplayName("카테고리로 아이템 조회 테스트")
    void findByCategory_Success() {
        // given
        when(testRepository.findByCategory("테스트 카테고리"))
                .thenReturn(Arrays.asList(testItem));

        // when
        List<TestItemDTO> results = testService.findByCategory("테스트 카테고리");

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCategory()).isEqualTo("테스트 카테고리");
    }

    @Test
    @DisplayName("아이템 수정 성공 테스트")
    void update_Success() {
        // given
        testItemDTO.setId(1L);
        testItemDTO.setName("수정된 이름");
        testItemDTO.setCategory("수정된 카테고리");
        when(testRepository.findById(1L)).thenReturn(Optional.of(testItem));

        // when
        TestItemDTO result = testService.update(testItemDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("수정된 이름");
        assertThat(result.getCategory()).isEqualTo("수정된 카테고리");
    }

    @Test
    @DisplayName("존재하지 않는 아이템 수정 시 예외 발생 테스트")
    void update_NotFound() {
        // given
        testItemDTO.setId(999L);
        when(testRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> testService.update(testItemDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("아이템 삭제 성공 테스트")
    void deleteById_Success() {
        // given
        when(testRepository.existsById(1L)).thenReturn(true);
        doNothing().when(testRepository).deleteById(1L);

        // when
        testService.deleteById(1L);

        // then
        verify(testRepository, times(1)).existsById(1L);
        verify(testRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 아이템 삭제 시 예외 발생 테스트")
    void deleteById_NotFound() {
        // given
        when(testRepository.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> testService.deleteById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
