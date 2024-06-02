package com.company.repo;
import com.company.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepo extends JpaRepository<Photo,Integer > {

    List<Photo> findByExhibitionId(Integer exhibitionId);
}
