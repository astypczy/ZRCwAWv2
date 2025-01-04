package com.pwr.project.entities.search;

import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.datatypes.NoticeStatus;
import lombok.Data;

import java.util.Set;

@Data
public class SearchResponse {
    private long id;

    private String title;

    private String description;

    private Set<File> files;

    private Set<String> tags;

    private NoticeStatus noticeStatus;

    private String createdBy;

    private String sellerNumber;

    private String sellerMail;
}
