package com.dgsw.javaTest.repository;

import com.dgsw.javaTest.entity.TestItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestRepositoryCustom {
    List<TestItem> findByNameContaining(String name);
    List<TestItem> findByCategoryAndName(String category, String name);
    Page<TestItem> findItemsWithPaging(Pageable pageable);
}
