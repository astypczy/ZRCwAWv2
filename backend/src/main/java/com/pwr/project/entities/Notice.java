package com.pwr.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.datatypes.NoticeStatus;
import jakarta.persistence.*;


import lombok.*;

import java.util.HashSet;
import java.util.Set;                            
@Getter
@Setter
@Entity
@Table(name = "notices")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<File> files = new HashSet<>();

    @ElementCollection
    private Set<String> tags;

    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;

    private String sellerNumber;

    private String createdBy;

    public void addFile(File file){
        files.add(file);
        file.setNotice(this);
    }

    public void removeFile(File file){
        files.remove(file);
        file.setNotice(null);
    }
}
