package io.github.authservice.crowdfund.feature.reward.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;

/**
 * 리워드 조회 요청 데이터
 *
 * projectId : 조회할 프로젝트 ID
 */
public record GetRequest(

        @NotBlank(message = "프로젝트 아이디는 필수입니다.")
        Long projectId

) {
}