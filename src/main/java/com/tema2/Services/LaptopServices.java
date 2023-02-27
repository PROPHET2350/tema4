package com.tema2.Services;

import com.tema2.Models.Laptop;
import com.tema2.Repositories.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaptopServices {

    @Autowired
    private LaptopRepository laptopRepository;

    public List<Laptop> getAll(){
        return laptopRepository.findAll();
    }
    
    public Optional<Laptop> getLaptopById(Long id){
        return laptopRepository.findById(id);
    }
    
    public Laptop add(Laptop laptop){
        return laptopRepository.save(laptop);
    }
    
    public void delete(Long id){
        Optional<Laptop> laptop = laptopRepository.findById(id);
        laptop.ifPresent(value -> laptopRepository.delete(value));
    }

    public void deleteAll(){
        laptopRepository.deleteAll();
    }
}
