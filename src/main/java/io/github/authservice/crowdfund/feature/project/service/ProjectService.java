package io.github.authservice.crowdfund.feature.project.service;

import io.github.authservice.crowdfund.feature.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 프로젝트 도메인 비즈니스 로직 처리 계층.
 * 컨트롤러의 요구 사양에 맞춰 전용 Response 객체를 반환하며 서버 측 데이터 가공 로직을 포함함.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    /**
     * 1. 신규 프로젝트 생성 기능을 수행함.
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성 결과 객체
     */
    @Transactional
    public CreateProjectResponse createProject(ProjectCreateRequest request) {
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(30);

        // TODO: 레포지토리 저장 로직 구현 예정
        Long temporaryId = 1L;

        return new CreateProjectResponse(temporaryId, "프로젝트가 성공적으로 생성되었습니다.");
    }

    /**
     * 2. 시스템의 대표 프로젝트 정보를 조회함.
     * @return ProjectResponse 프로젝트 상세 데이터
     */
    public ProjectResponse getProjects() {
        return createMockResponse(1L);
    }

    /**
     * 3. 특정 ID를 가진 프로젝트의 상세 데이터를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return 조회된 프로젝트 상세 응답 객체
     */
    public ProjectResponse getProjectDetail(Long projectId) {
        return createMockResponse(projectId);
    }

    /**
     * 4. 기존 프로젝트의 정보를 수정함.
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 정보
     * @return 수정 완료된 상세 응답 객체
     */
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request) {
        return createMockResponse(projectId);
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제함.
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void deleteProject(Long projectId) {
        // 삭제 로직 수행
    }

    /**
     * 6. 특정 사용자가 생성한 대표 프로젝트를 조회함.
     * @param userId 사용자(창작자) 식별 번호
     * @return 해당 사용자의 프로젝트 응답 객체
     */
    public ProjectResponse getProjectsByUser(Long userId) {
        return createMockResponse(1L);
    }

    /**
     * 7. 특정 카테고리 내의 대표 프로젝트를 조회함.
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리 소속 프로젝트 응답 객체
     */
    public ProjectResponse getProjectsByCategory(Integer categoryId) {
        return createMockResponse(1L);
    }

    /**
     * 8. 관리자 권한으로 특정 프로젝트를 강제 제거함.
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void forceDeleteProject(Long projectId) {
        // 강제 삭제 로직 수행
    }

    /**
     * 9. 해당 프로젝트의 후원자 배송지 정보를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return PledgeAddressResponse 배송지 정보 응답 객체
     */
    public PledgeAddressResponse getPledgeAddresses(Long projectId) {
        return new PledgeAddressResponse(1L, "홍길동", "12345", "대전광역시 유성구", "상세주소");
    }

    /**
     * 10. 프로젝트의 현재 진행 상태를 업데이트함.
     * @param projectId 프로젝트 식별 번호
     * @param status    변경할 상태값
     */
    @Transactional
    public void updateProjectStatus(Long projectId, String status) {
        // 상태 업데이트 로직 수행
    }

    /**
     * 테스트 및 초기 설계를 위한 임시 응답 객체 생성 메서드
     */
    private ProjectResponse createMockResponse(Long projectId) {
        return new ProjectResponse(
                projectId,
                "테스트 프로젝트",
                "프로젝트 상세 설명입니다.",
                new BigDecimal("1000000"),
                new BigDecimal("0"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                "진행중",
                1,
                100L
        );
    }
}