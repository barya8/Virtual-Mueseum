package com.company.service;

import com.company.dto.ExhibitionResponse;
import com.company.dto.PhotoDto;
import com.company.dto.ReturnCodeAndMessage;
import com.company.dto.ServiceResult;
import com.company.model.Photo;
import com.company.repo.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepo photoRepo;

    public ServiceResult uploadPhoto(PhotoDto photoDto) {
        ServiceResult serviceResult = new ServiceResult();
        ReturnCodeAndMessage returnCodeAndMessage = new ReturnCodeAndMessage();
        try {
            Photo photo = new Photo();
            photo
                    .setExhibitionId(photoDto.getExhibitionId())
                    .setFileName(photoDto.getFileName())
                    .setDescription(photoDto.getDescription())
                    .setUploadDate(photoDto.getUploadDate())
                    .setLocation(photoDto.getLocation())
                    .setArtist(photoDto.getArtist());

            // Read image data bytes from MultipartFile and encode to Base64
            byte[] imageDataBytes = photoDto.getFileInput().getBytes();
            String base64ImageData = Base64.getEncoder().encodeToString(imageDataBytes);
            photo.setImageData(base64ImageData);

            photoRepo.save(photo);

            returnCodeAndMessage.setReturnCode("0");
            returnCodeAndMessage.setReturnMessage("Photo uploaded and saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            returnCodeAndMessage.setReturnCode("99");
            returnCodeAndMessage.setReturnMessage("Error uploading photo.");
        }
        serviceResult.setServiceResult(returnCodeAndMessage);
        return serviceResult;
    }


    public ResponseEntity<ExhibitionResponse> getExhibitionData(Integer exhibitionId) {
        try {
            List<Photo> photos = photoRepo.findByExhibitionId(exhibitionId);
            ExhibitionResponse exhibitionResponse = new ExhibitionResponse(photos);
            return new ResponseEntity<>(exhibitionResponse, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
