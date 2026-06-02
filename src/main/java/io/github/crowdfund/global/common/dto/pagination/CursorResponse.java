package io.github.crowdfund.global.common.dto.pagination;

import java.util.List;

/**
 * 커서 기반 페이지네이션 공통 응답 레코드
 *
 * @param content: 조회된 데이터 목록
 * @param hasNext: 다음 페이지 존재 여부
 * @param nextCursor: 다음 페이지 커서
 * @param <T>: 조회하는 데이터의 타입
 * @param <C>: 커서의 타입
 */
public record CursorResponse<T, C>(
        List<T> content,
        boolean hasNext,
        C nextCursor
) {
}
