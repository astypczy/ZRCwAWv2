package com.pwr.project.services;

import com.pwr.project.dto.NoticeDTO;
import com.pwr.project.entities.Notice;
import com.pwr.project.entities.User;
import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.datatypes.NoticeStatus;
import com.pwr.project.mappers.NoticeMapper;
import com.pwr.project.repositories.FileRepository;
import com.pwr.project.repositories.NoticeRepository;
import com.pwr.project.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AuthService authService;  // Dodajemy zależność do AuthService

    @Transactional
    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeRepository.findByNoticeStatus(NoticeStatus.Live);
        User currentUser = getCurrentUser();  // Pobieramy pełny obiekt użytkownika
        String currentUsername = currentUser.getLogin();  // Możemy uzyskać login użytkownika, jeśli to konieczne

        if (!currentUsername.equals("anonymousUser")) {
            notices.addAll(noticeRepository.findByCreatedByAndNoticeStatusNot(currentUsername, NoticeStatus.Live));
        }

        return notices.stream()
                .map(NoticeMapper::mapToNoticeDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> optionalNotice = noticeRepository.findById(id);
        Notice notice = optionalNotice.orElseThrow(() -> new IllegalArgumentException("Notice with ID " + id + " not found"));
        return NoticeMapper.mapToNoticeDTO(notice);
    }

    @Transactional
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        User user = getCurrentUser();  // Pobieramy pełny obiekt użytkownika
        if (!user.getIsSeller()) {
            throw new SecurityException("User is not a seller!");
        }

        Notice notice = NoticeMapper.mapToNotice(noticeDTO);
        notice.setCreatedBy(user.getLogin());  // Przypisujemy login użytkownika
        Notice savedNotice = noticeRepository.save(notice);
        return NoticeMapper.mapToNoticeDTO(savedNotice);
    }

    public NoticeDTO updateNotice(NoticeDTO notice) throws IllegalAccessException {
        Optional<Notice> existingNoticeOptional = noticeRepository.findById(notice.getId());
        if (existingNoticeOptional.isPresent()) {
            Notice existingNotice = existingNoticeOptional.get();
            User currentUser = getCurrentUser();  // Pobieramy pełny obiekt użytkownika
            if (existingNotice.getCreatedBy().equals(currentUser.getLogin())) {
                existingNotice.setTitle(notice.getTitle());
                existingNotice.setDescription(notice.getDescription());
                existingNotice.setTags(notice.getTags());
                existingNotice.setSellerNumber(notice.getSellerNumber());
                existingNotice.setNoticeStatus(notice.getNoticeStatus());
                Notice updatedNotice = noticeRepository.save(existingNotice);
                return NoticeMapper.mapToNoticeDTO(updatedNotice);
            } else {
                throw new IllegalAccessException("You don't have permission to edit notice with ID: " + notice.getId());
            }
        } else {
            throw new IllegalArgumentException("Notice with ID " + notice.getId() + " does not exist");
        }
    }

    public void deleteNotice(Long id) throws IllegalAccessException {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            User currentUser = getCurrentUser();  // Pobieramy pełny obiekt użytkownika
            if (existingNotice.get().getCreatedBy().equals(currentUser.getLogin())) {
                noticeRepository.deleteById(id);
            } else {
                throw new IllegalAccessException("You don't have permission to delete this notice");
            }
        } else {
            throw new IllegalArgumentException("Notice with ID " + id + " does not exist");
        }
    }

    // Zaktualizowana metoda, która używa AuthService do pobierania pełnych informacji o użytkowniku
    private User getCurrentUser() {
        return authService.getCurrentUser();  // Korzystamy z AuthService, który zwraca pełny obiekt User
    }

    @Transactional
    public NoticeDTO addFileToNotice(Long noticeId, File file) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        file.setNotice(notice);
        fileRepository.save(file);
        return NoticeMapper.mapToNoticeDTO(notice);
    }

    @Transactional
    public List<File> getAllFiles(Long noticeId) {
        return fileRepository.findAllByNoticeId(noticeId);
    }

    public List<String> populateTags() {
        return noticeRepository.populateTags();
    }

    public List<String> getAllTags() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .filter(notice -> notice.getTags() != null)
                .flatMap(notice -> notice.getTags().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
