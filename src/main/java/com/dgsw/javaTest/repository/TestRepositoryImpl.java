package com.dgsw.javaTest.repository;

import com.dgsw.javaTest.entity.QTestItem;
import com.dgsw.javaTest.entity.TestItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TestRepositoryImpl implements TestRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    public TestRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<TestItem> findByNameContaining(String name) {
        QTestItem testItem = QTestItem.testItem;
        
        return queryFactory
                .selectFrom(testItem)
                .where(testItem.name.contains(name))
                .fetch();
    }

    @Override
    public List<TestItem> findByCategoryAndName(String category, String name) {
        QTestItem testItem = QTestItem.testItem;
        
        return queryFactory
                .selectFrom(testItem)
                .where(
                        testItem.category.eq(category),
                        testItem.name.contains(name)
                )
                .fetch();
    }

    @Override
    public Page<TestItem> findItemsWithPaging(Pageable pageable) {
        QTestItem testItem = QTestItem.testItem;
        
        List<TestItem> content = queryFactory
                .selectFrom(testItem)
                .orderBy(testItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        
        JPAQuery<Long> countQuery = queryFactory
                .select(testItem.count())
                .from(testItem);
        
        return new PageImpl<>(content, pageable, countQuery.fetchOne());
    }
}
