package com.company.controller;

import com.company.model.ExhibitionResponse;
import com.company.model.ServiceResult;
import com.company.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<ServiceResult> handleFileUpload(@RequestParam("fileInput") MultipartFile fileInput,
                                                          @RequestParam("exhibitionId") Integer exhibitionId,
                                                          @RequestParam("fileName") String fileName,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("uploadDate") Date uploadDate,
                                                          @RequestParam("location") String location,
                                                          @RequestParam("artist") String artist) {

        return new ResponseEntity<>(photoService.uploadPhoto(fileInput, exhibitionId, fileName, description, uploadDate, location, artist), HttpStatus.OK);
    }
    @GetMapping("/get-exhibition-data/{exhibitionId}")
    public ResponseEntity<ExhibitionResponse> getExhibitionData(@PathVariable Integer exhibitionId) {
        return photoService.getExhibitionData(exhibitionId);
    }
}
