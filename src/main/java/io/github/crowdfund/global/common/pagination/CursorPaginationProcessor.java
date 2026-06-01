package io.github.crowdfund.global.common.pagination;

import io.github.crowdfund.global.common.dto.pagination.CursorResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 커서 기반 페이징 처리를 위한 유틸리티 클래스
 */
public class CursorPaginationProcessor {

    /**
     * 프론트엔드에 반환할 커서 기반 객체를 생성하는 메서드
     *
     * @param contents        조회된 데이터 목록 (더 불러올 데이터가 있는지 확인하기 위해 limit + 1개가 조회되어야 함)
     * @param limit           한 요청마다 불러올 데이터 개수
     * @param cursorExtractor 조회된 데이터 목록의 마지막 아이템에서 커서를 추출하는 함수
     * @param <T>             조회하는 데이터의 타입
     * @param <C>             커서의 타입
     * @return CursorResponse<T, C>
     */
    public static <T, C> CursorResponse<T, C> convertToCursorResponse(
            List<T> contents,
            int limit,
            Function<T, C> cursorExtractor
    ) {
        // 1. 방어 조치: 조회할 데이터가 없으면 빈 응답을 반환한다.
        if (contents == null || contents.isEmpty()) {
            return new CursorResponse<>(Collections.emptyList(), false, null);
        }

        // 2. 다음 페이지 존재 여부 확인
        boolean hasNext = contents.size() > limit;
        C nextCursor = null;

        // 3. 다음 페이지가 존재한다면, 조회된 데이터에서 limit 개수만큼 리스트를 잘라낸다.
        List<T> resultList = hasNext
                ? new ArrayList<>(contents.subList(0, limit))
                : new ArrayList<>(contents);

        // 4. 다음 커서 추출 (추상화된 함수 호출)
        if (hasNext && !resultList.isEmpty()) {
            T lastItem = resultList.get(resultList.size() - 1);
            nextCursor = cursorExtractor.apply(lastItem); // 💡 헬퍼는 커서가 뭔지 몰라도 됩니다.
        }

        return new CursorResponse<>(resultList, hasNext, nextCursor);
    }
}
