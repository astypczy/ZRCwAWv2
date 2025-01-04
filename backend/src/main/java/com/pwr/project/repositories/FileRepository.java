package com.pwr.project.repositories;

import com.pwr.project.entities.datatypes.File;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByNoticeId(long noticeId);
}
