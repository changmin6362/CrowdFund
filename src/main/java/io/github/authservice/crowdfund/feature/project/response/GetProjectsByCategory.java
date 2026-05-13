package io.github.authservice.crowdfund.feature.project.response;

import java.util.List;

/**
 * 프로젝트 정보 응답용 데이터 객체.
 * 목록 및 상세 조회 시 클라이언트에게 전달할 데이터 구조 정의.
 * 설계 지침 준수를 위한 Record 타입 구성.
 */
public record GetProjectsByCategory(
        String message,
        List<ProjectInfo> projects

) {
}