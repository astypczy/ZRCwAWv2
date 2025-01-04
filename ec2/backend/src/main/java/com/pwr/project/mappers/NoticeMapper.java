package com.pwr.project.mappers;

import com.pwr.project.dto.NoticeDTO;
import com.pwr.project.entities.Notice;

public class NoticeMapper {
    public static NoticeDTO mapToNoticeDTO(Notice notice){
        return new NoticeDTO(
                notice.getId(),
                notice.getTitle(),
                notice.getDescription(),
                notice.getFiles(),
                notice.getTags(),
                notice.getNoticeStatus(),
                notice.getSellerNumber(),
                notice.getCreatedBy()
        );
    }
    public static Notice mapToNotice(NoticeDTO noticeDTO) {
        return new Notice(
                noticeDTO.getId(),
                noticeDTO.getTitle(),
                noticeDTO.getDescription(),
                noticeDTO.getFiles(),
                noticeDTO.getTags(),
                noticeDTO.getNoticeStatus(),
                noticeDTO.getSellerNumber(),
                noticeDTO.getCreatedBy()
        );
    }
}
