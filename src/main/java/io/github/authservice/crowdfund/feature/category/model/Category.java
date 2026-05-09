package io.github.authservice.crowdfund.feature.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//모델 객체

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sortOrder;
    private Integer level;
    private boolean isActive;
}
