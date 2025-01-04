package com.pwr.project.controllers;

import com.pwr.project.dto.NoticeDTO;
import com.pwr.project.entities.Notice;
import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.search.SearchCriteriaRequest;
import com.pwr.project.entities.search.SearchResponse;
import com.pwr.project.services.NoticeService;
import com.pwr.project.services.search.AdvanceSearchService;
import com.pwr.project.services.search.NoticeSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notices")
@CrossOrigin(origins = "http://localhost:4200")  // This will apply to all methods
public class NoticeController {

    @Autowired
    private AdvanceSearchService advanceSearchService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeSearchService noticeSearchService;

    @PostMapping
    public ResponseEntity<NoticeDTO> createNotice(@RequestBody NoticeDTO noticeDTO) {
        NoticeDTO savedNotice = noticeService.createNotice(noticeDTO);
        return ResponseEntity.ok(savedNotice);
    }

    @GetMapping("{id}")
    public ResponseEntity<NoticeDTO> getNoticeById(@PathVariable("id") Long noticeId) {
        NoticeDTO notice = noticeService.getNoticeById(noticeId);
        return ResponseEntity.ok(notice);
    }

    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateNotice(@PathVariable("id") Long noticeId, @RequestBody NoticeDTO notice) {
        try {
            notice.setId(noticeId);
            NoticeDTO updatedNotice = noticeService.updateNotice(notice);
            return ResponseEntity.ok(String.format("Notice %s has been updated successfully!", noticeId));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(String.format("You don't have right to update notice with ID: %s", noticeId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Notice with ID: %s is not present in database", noticeId));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable("id") Long noticeId) {
        try {
            noticeService.deleteNotice(noticeId);
            return ResponseEntity.ok(String.format("Notice with id: %s has been deleted successfully!", noticeId));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(String.format("You don't have right to delete notice with id: %s", noticeId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Notice with id: %s does not exist in database", noticeId));
        }
    }

    @PostMapping("/{noticeId}/files")
    public ResponseEntity<NoticeDTO> addFilesToNotice(@PathVariable("noticeId") Long noticeId, @RequestBody List<File> files) {
        for (File file : files) {
            noticeService.addFileToNotice(noticeId, file);
        }
        NoticeDTO updatedNotice = noticeService.getNoticeById(noticeId);
        return ResponseEntity.ok(updatedNotice);
    }

    @GetMapping("/{noticeId}/files")
    public ResponseEntity<List<File>> getAllFilesForNotice(@PathVariable("noticeId") Long noticeId) {
        List<File> files = noticeService.getAllFiles(noticeId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> populateTags() {
        List<String> tags = noticeService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notice>> searchNotices(@RequestParam String query) {
        return ResponseEntity.ok(noticeSearchService.searchNotices(query));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SearchResponse>> searchByCriteria(@RequestBody SearchCriteriaRequest searchCriteriaRequest){
        return ResponseEntity.ok(advanceSearchService.searchNoticeByCriteria(searchCriteriaRequest));
    }
}
