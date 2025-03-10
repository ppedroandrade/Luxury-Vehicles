package app.controller;

import app.entity.Motorcycle;
import app.service.MotorcycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Motorcycle motorcycle) {
        try {
            String newMotorcycle = motorcycleService.save(motorcycle);
            return new ResponseEntity<>(newMotorcycle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Motorcycle motorcycle = motorcycleService.findById(id);
            return new ResponseEntity<>(motorcycle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Motorcycle>> findAll() {
        try {
            List<Motorcycle> motorcycleList = motorcycleService.findAll();
            return new ResponseEntity<>(motorcycleList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Motorcycle motorcycle, @PathVariable Long id) {
        try {
            String updatedMotorcycle = motorcycleService.update(id, motorcycle);
            return new ResponseEntity<>(updatedMotorcycle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String deleteMotocycle = this.motorcycleService.delete(id);
            return new ResponseEntity<>(deleteMotocycle,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByModel")
    public ResponseEntity<?> findByModel(@RequestParam String model) {
        try {
            List<Motorcycle> motorcycleList = motorcycleService.findByModel(model);
            return new ResponseEntity<>(motorcycleList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByBikeType")
    public ResponseEntity<?> findByBikeType(@RequestParam String bikeType) {
        try {
            List<Motorcycle> motorcycleList = motorcycleService.findByBikeType(bikeType);
            return new ResponseEntity<>(motorcycleList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}