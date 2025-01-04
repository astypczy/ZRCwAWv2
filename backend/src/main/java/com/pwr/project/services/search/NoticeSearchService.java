package com.pwr.project.services.search;

import com.pwr.project.entities.Notice;
import com.pwr.project.repositories.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeSearchService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> searchNotices(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return noticeRepository.findAll();
        }

        return noticeRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            searchTerm, searchTerm);
    }
}