package io.github.crowdfund.feature.project.scheduler;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.domain.project.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectScheduler {

    private final ProjectMapper projectMapper;

    /**
     * 매일 자정마다 종료일이 지난 ONGOING 프로젝트의 상태를 COMPLETED로 변경
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateExpiredProjectStatus() {
        log.info("[Scheduler] 기간이 지난 ONGOING 프로젝트의 상태를 COMPLETED로 변경 중...");
        
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = projectMapper.updateStatusForExpiredProjects(ProjectStatus.COMPLETED, now);
        
        log.info("[Scheduler] 업데이트 완료. {} 기간이 지난 Project의 status를 COMPLETED로 변경.", updatedCount);
    }
}
