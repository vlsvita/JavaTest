package com.dgsw.javaTest.repository;

import com.dgsw.javaTest.entity.TestItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TestRepositoryTest {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestEntityManager entityManager;

    private TestItem testItem1;
    private TestItem testItem2;

    @BeforeEach
    void setUp() {
        testItem1 = new TestItem("노트북", "전자제품");
        testItem2 = new TestItem("책상", "가구");
    }

    @Test
    @DisplayName("아이템 저장 및 조회 테스트")
    void save_And_FindById() {
        // given
        TestItem savedItem = testRepository.save(testItem1);
        entityManager.flush();
        entityManager.clear();

        // when
        Optional<TestItem> foundItem = testRepository.findById(savedItem.getId());

        // then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("노트북");
        assertThat(foundItem.get().getCategory()).isEqualTo("전자제품");
    }

    @Test
    @DisplayName("Query Method - 이름으로 검색 테스트")
    void findByName() {
        // given
        testRepository.save(testItem1);
        testRepository.save(testItem2);
        entityManager.flush();

        // when
        List<TestItem> results = testRepository.findByName("노트북");

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(item -> item.getName().equals("노트북"));
    }

    @Test
    @DisplayName("Query Method - 카테고리로 검색 테스트")
    void findByCategory() {
        // given
        testRepository.save(testItem1);
        testRepository.save(new TestItem("마우스", "전자제품"));
        testRepository.save(testItem2);
        entityManager.flush();

        // when
        List<TestItem> results = testRepository.findByCategory("전자제품");

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSizeGreaterThanOrEqualTo(2);
        assertThat(results).allMatch(item -> item.getCategory().equals("전자제품"));
    }

    @Test
    @DisplayName("JPQL - 키워드로 검색 테스트")
    void searchByKeyword() {
        // given
        testRepository.save(testItem1);
        testRepository.save(new TestItem("노트", "문구"));
        testRepository.save(testItem2);
        entityManager.flush();

        // when
        List<TestItem> results = testRepository.searchByKeyword("노트");

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAll() {
        // given
        testRepository.save(testItem1);
        testRepository.save(testItem2);
        entityManager.flush();

        // when
        List<TestItem> results = testRepository.findAll();

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("아이템 삭제 테스트")
    void deleteById() {
        // given
        TestItem savedItem = testRepository.save(testItem1);
        Long savedId = savedItem.getId();
        entityManager.flush();

        // when
        testRepository.deleteById(savedId);
        entityManager.flush();

        // then
        Optional<TestItem> deletedItem = testRepository.findById(savedId);
        assertThat(deletedItem).isEmpty();
    }

    @Test
    @DisplayName("아이템 존재 여부 확인 테스트")
    void existsById() {
        // given
        TestItem savedItem = testRepository.save(testItem1);
        entityManager.flush();

        // when
        boolean exists = testRepository.existsById(savedItem.getId());
        boolean notExists = testRepository.existsById(99999L);

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
