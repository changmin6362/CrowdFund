package io.github.authservice.crowdfund.feature.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA를 위한 기본 생성자
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer level;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_active")
    private boolean isActive = true; // 기본값 활성화

    // 자기 참조: 부모 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 자기 참조: 자식 카테고리 리스트
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderBy("sortOrder ASC") // 조회 시 순서 정렬
    private List<Category> children = new ArrayList<>();

    @Builder // 서비스에서 Category.builder()를 쓸 수 있게 해줌
    public Category(String name, Integer level, Integer sortOrder, Category parent) {
        this.name = name;
        this.level = level;
        this.sortOrder = sortOrder;
        this.parent = parent;
        this.isActive = true;
    }

    // --- 서비스 로직에서 사용하는 수정 메서드들 (도메인 주도 설계) ---

    public void updateName(String name) {
        this.name = name;
    }

    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void delete() {
        this.isActive = false;
    }
}
