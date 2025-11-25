package com.dgsw.javaTest.entity;

import com.dgsw.javaTest.dto.TestItemDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test_items")
@EntityListeners(AuditingEntityListener.class)
public class TestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Setter
    @Column(name = "category", nullable = false)
    private String category;
    @Setter
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Setter
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TestItem(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
