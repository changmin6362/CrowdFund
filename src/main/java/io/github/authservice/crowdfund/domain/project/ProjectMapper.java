package io.github.authservice.crowdfund.domain.project;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.feature.project.command.CreateProjectCommand;
import io.github.authservice.crowdfund.feature.project.response.ProjectInfo;
import io.github.authservice.crowdfund.feature.project.response.ShippingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {
    long insert(@Param("creatorId") Long creatorId, @Param("command") CreateProjectCommand command);

    List<ProjectInfo> findAll(@Param("statuses") List<ProjectStatus> statuses, @Param("categoryId") Integer categoryId);

    void update(@Param("projectId") Long projectId, @Param("title") String title, @Param("contentBlocks") String contentBlocks);

    void deleteById(Long projectId);

    List<ProjectInfo> findByCreatorId(Long creatorId);

    List<ShippingInfo> findShippingInfosByProjectId(Long projectId);

    void updateStatus(@Param("projectId") Long projectId, @Param("status") ProjectStatus status);
}
