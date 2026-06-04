package io.github.crowdfund.domain.project.mapper;

import io.github.crowdfund.domain.project.Project;
import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.feature.project.creator.dto.extract.ShippingInfo;
import io.github.crowdfund.feature.project.creator.dto.fetch.ProjectInfo;
import io.github.crowdfund.feature.project.user.dto.detail.ProjectDetail;
import io.github.crowdfund.feature.project.user.dto.fetch.ProjectElement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 프로젝트 관련 복합 쿼리를 담은 매퍼 (MyBatis)
 */
@Mapper
public interface ProjectMapper {
    Long insert(@Param("creatorId") Long creatorId, @Param("command") Project project);

    List<ProjectElement> findAll(@Param("statuses") List<ProjectStatus> statuses, @Param("categoryId") Integer categoryId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt, @Param("cursorId") Long cursorId, @Param("limit") Integer limit);

    void update(@Param("projectId") Long projectId, @Param("title") String title, @Param("contentBlocks") String contentBlocks);

    void deleteById(Long projectId);

    List<ProjectInfo> findByCreatorId(Long creatorId);

    List<ShippingInfo> findShippingInfosByProjectId(Long projectId);

    void patchStatus(@Param("projectId") Long projectId, @Param("status") ProjectStatus status);
    
    void updateStatusForExpiredProjects(@Param("now") LocalDateTime now);

    ProjectDetail findByIdWithDetail(Long projectId);
}
