package com.example.travelagency.repositories;

import com.example.travelagency.entities.ExcursionPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionPhotosRepository extends JpaRepository<ExcursionPhotos, Integer> {
}
