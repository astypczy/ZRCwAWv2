package com.pwr.project.repositories;

import com.pwr.project.entities.Notice;
import com.pwr.project.entities.datatypes.NoticeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
    // For public notices
    List<Notice> findByNoticeStatus(NoticeStatus status);

    // For user's own notices
    List<Notice> findByCreatedByAndNoticeStatusNot(String createdBy, NoticeStatus status);

    // For search functionality
    List<Notice> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String title, String description);

    // For tags
    @Query("SELECT DISTINCT t FROM Notice n JOIN n.tags t")
    List<String> populateTags();
}
