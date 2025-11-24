package com.dgsw.javaTest.repository;

import com.dgsw.javaTest.entity.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestItem, Long> {
    List<TestItem> findByName(String name);
}
