package com.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {
    private Integer photoId;
    private Integer exhibitionId;
    private String fileName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;
    private String location;
    private String artist;
    private MultipartFile fileInput;
}
