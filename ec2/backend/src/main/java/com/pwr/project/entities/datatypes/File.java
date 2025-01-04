package com.pwr.project.entities.datatypes;

import com.pwr.project.entities.Notice;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "File")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fileName;
    private String fileType;

    @Lob
    private String fileContent;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;
}
