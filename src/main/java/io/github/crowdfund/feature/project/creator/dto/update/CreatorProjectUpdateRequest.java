package io.github.crowdfund.feature.project.creator.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatorProjectUpdateRequest(
        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @Schema(description = "프로젝트 콘텐트 블럭 데이터",
                example = """
                {
                  "time": 1717200000000,
                  "blocks": [
                    {
                      "id": "b1",
                      "type": "header",
                      "data": { "text": "프로젝트 소개", "level": 2 }
                    },
                    {
                      "id": "b2",
                      "type": "paragraph",
                      "data": { "text": "친환경 소재로 만든 방수 미니멀 백팩입니다. 일상과 여행 모두에 완벽하게 어울립니다." }
                    },
                    {
                      "id": "b3",
                      "type": "image",
                      "data": { "url": "https://crowdfund.com", "caption": "백팩 착용 정면 사진" }
                    }
                  ],
                  "version": "2.28.2"
                }
                """)
        @NotBlank(message = "프로젝트 콘텐트 블럭 데이터는 필수입니다.")
        String contentBlocks
) {
}