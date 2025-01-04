package com.pwr.project.dto;

import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.datatypes.NoticeStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long id;
    private String title;
    private String description;
    private Set<File> files;
    private Set<String> tags;
    private NoticeStatus noticeStatus;
    private String sellerNumber;
    private String createdBy;
}
