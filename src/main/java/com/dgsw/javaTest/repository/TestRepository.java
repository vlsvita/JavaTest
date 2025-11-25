package com.dgsw.javaTest.repository;

import com.dgsw.javaTest.entity.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<TestItem, Long>, TestRepositoryCustom {
    // Query Method
    List<TestItem> findByName(String name);
    
    // Query Method
    List<TestItem> findByCategory(String category);
    
    // JPQL
    @Query("SELECT t FROM TestItem t WHERE t.name LIKE %:keyword% OR t.category LIKE %:keyword%")
    List<TestItem> searchByKeyword(@Param("keyword") String keyword);
}
