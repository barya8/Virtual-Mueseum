package com.company.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photoId")
    private Integer photoId;
    @Column(name = "exhibitionId")
    private Integer exhibitionId;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "description")
    private String description;
    @Column(name = "uploadDate")
    private Date uploadDate;
    @Column(name = "location")
    private String location;
    @Column(name = "artist")
    private String artist;

    @Lob
    @Column(name = "base64Data")
    private String imageData;
}
