package com.example.kursovayasocks.dao.repository;

import com.example.kursovayafromsocks.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    Color findColorByName(String name);
}

