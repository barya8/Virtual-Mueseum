package com.company.service;

import com.company.model.ExhibitionResponse;
import com.company.exceptions.ServiceResultException;
import com.company.model.ServiceResult;
import com.company.model.Photo;
import com.company.repo.PhotoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    private PhotoRepo photoRepo;

    public ServiceResult uploadPhoto(MultipartFile fileInput,
                                     Integer exhibitionId,
                                     String fileName,
                                     String description,
                                     Date uploadDate,
                                     String location,
                                     String artist) {
        ServiceResult.ServiceResultBuilder serviceResult=ServiceResult.builder();
        try {
            log.info("Received request to upload photo: exhibitionId={}, fileName={}, description={}, uploadDate={}, location={}, artist={}",
                    exhibitionId, fileName, description, uploadDate, location, artist);

            Photo photo = Photo.builder()
                    .exhibitionId(exhibitionId)
                    .fileName(fileName)
                    .description(description)
                    .uploadDate(uploadDate)
                    .location(location)
                    .artist(artist)
                    .build();

            // Read image data bytes from MultipartFile and encode to Base64
            byte[] imageDataBytes = fileInput.getBytes();
            String base64ImageData = Base64.getEncoder().encodeToString(imageDataBytes);
            photo.setImageData(base64ImageData);

            photoRepo.save(photo);
            log.info("Photo uploaded and saved successfully: exhibitionId={}, photoId={}", exhibitionId, photo.getPhotoId());

            serviceResult
                    .returnCode("0")
                    .returnMessage("Photo uploaded and saved successfully!");
        } catch (IOException e) {
            log.error("Error uploading photo.", e);
            throw new ServiceResultException(ServiceResult.builder()
                    .returnCode("99")
                    .returnMessage("Error uploading photo.")
                    .build());
        }
        return serviceResult.build();
    }


    public ResponseEntity<ExhibitionResponse> getExhibitionData(Integer exhibitionId) {
        try {
            log.info("Received request to retrieve exhibition data: exhibitionId={}", exhibitionId);
            List<Photo> photos = photoRepo.findByExhibitionId(exhibitionId);
            ExhibitionResponse exhibitionResponse = new ExhibitionResponse(photos);
            log.info("Exhibition data retrieved successfully: exhibitionId={}, numPhotos={}", exhibitionId, photos.size());
            return new ResponseEntity<>(exhibitionResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving exhibition data.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
