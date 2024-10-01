package co.edu.escuelaing.jpaDemo.controller;

import co.edu.escuelaing.jpaDemo.exception.BadRequestException;
import co.edu.escuelaing.jpaDemo.exception.EntityNotFoundException;
import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.services.RealEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/realentitys")
public class RealEntityController {

    private final  RealEntityService realEntityService;

    public RealEntityController(RealEntityService realEntityService) {
        this.realEntityService = realEntityService;
    }

    @GetMapping("/test")
    public ResponseEntity<List<RealEntity>> getAllEntities(){
        try {
            List<RealEntity> realEntities = realEntityService.getRealEntities();
            return ResponseEntity.ok(realEntities);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Page<RealEntity>> getRealEntities(
            @RequestParam(required = false) String address,
            @RequestParam(required = false, defaultValue = "0") int minPrice,
            @RequestParam(required = false, defaultValue = "1000000") int maxPrice,
            @RequestParam(required = false, defaultValue = "0") int minSize,
            @RequestParam(required = false, defaultValue = "1000") int maxSize,
            Pageable pageable) {
        try {
            if (pageable.getPageSize() > 10) {
                pageable = PageRequest.of(pageable.getPageNumber(), 10);
            }
            Page<RealEntity> realEntities = realEntityService.getFilteredRealEntities(address, minPrice, maxPrice, minSize, maxSize, pageable);
            return ResponseEntity.ok(realEntities);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<RealEntity> createRealEntity(@RequestBody RealEntity realEntity) throws URISyntaxException {
        try {
            RealEntity real = realEntityService.createRealEntity(realEntity);
            URI uri = new URI("api/v1/realentitys/" + real.getId());
            return ResponseEntity.created(uri).body(real);
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<RealEntity> updateRealEntity(@PathVariable Long id, @RequestBody RealEntity realEntityData){
        try {
            RealEntity real = realEntityService.updateRealEntity(id, realEntityData);
            URI uri = new URI("api/v1/realentitys/" + realEntityData.getId());
            return ResponseEntity.created(uri).body(real);
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (EntityNotFoundException | URISyntaxException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RealEntity> deleteRealEntity(@PathVariable Long id){
        try {
            realEntityService.deleteRealEntity(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
