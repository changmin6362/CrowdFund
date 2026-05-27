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

@Mapper
public interface ProjectMapper {
    long insert(@Param("creatorId") Long creatorId, @Param("command") Project project);

    List<ProjectElement> findAll(@Param("statuses") List<ProjectStatus> statuses, @Param("categoryId") Integer categoryId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt, @Param("cursorId") Long cursorId, @Param("limit") Integer limit);

    void update(@Param("projectId") Long projectId, @Param("title") String title, @Param("contentBlocks") String contentBlocks);

    void deleteById(Long projectId);

    List<ProjectInfo> findByCreatorId(Long creatorId);

    List<ShippingInfo> findShippingInfosByProjectId(Long projectId);

    void patchStatus(@Param("projectId") Long projectId, @Param("status") ProjectStatus status);

    ProjectDetail findByIdWithDetail(Long projectId);
}
