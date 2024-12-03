package ru.andrew.spring.project3RestSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrew.spring.project3RestSpring.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findBySensor_Name(String name);
    Integer countByRainingTrue();
}
