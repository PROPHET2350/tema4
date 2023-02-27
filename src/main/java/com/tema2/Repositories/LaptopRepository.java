package com.tema2.Repositories;

import com.tema2.Models.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}