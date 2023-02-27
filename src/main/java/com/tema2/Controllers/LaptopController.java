package com.tema2.Controllers;


import com.tema2.Models.Laptop;
import com.tema2.Services.LaptopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LaptopController {

    @Autowired
    private LaptopServices laptopServices;

    @GetMapping("/laptops")
    public ResponseEntity<List<Laptop>> getAll(){
        return ResponseEntity.ok(laptopServices.getAll());
    }
    @GetMapping("/laptop/{id}")
    public ResponseEntity<Laptop> getOne(@PathVariable Long id){
        return ResponseEntity.ok(laptopServices.getLaptopById(id).get());
    }

    @PostMapping("/add")
    public ResponseEntity<Laptop> add(@RequestBody Laptop laptop) {
        return ResponseEntity.ok(laptopServices.add(laptop));
    }
    @PutMapping("/update")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
        if (laptop.getId()!=null){
            laptopServices.add(laptop);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/laptop/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        laptopServices.delete(id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        laptopServices.deleteAll();
        return ResponseEntity.accepted().build();
    }

}
