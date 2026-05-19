package io.github.authservice.crowdfund.feature.category.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryRequest {
    @Positive(message = "부모 카테고리 ID는 1 이상의 양수여야 합니다.")
    private Integer parentId;

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    @Size(min = 2, max = 20, message = "카테고리 이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    private Integer id;

    public CreateCategoryRequest(Integer parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }
}

