package co.edu.escuelaing.jpaDemo.controller;

import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.services.RealEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/api/realentitys")
public class RealEntityController {

    private final  RealEntityService realEntityService;

    public RealEntityController(RealEntityService realEntityService) {
        this.realEntityService = realEntityService;
    }

    @GetMapping
    public ResponseEntity<List<RealEntity>> getRealEntitys(){
        List<RealEntity> realEntities = realEntityService.getUsers();
        return ResponseEntity.ok(realEntities);
    }

    @PostMapping
    public ResponseEntity<RealEntity> createRealEntity(@RequestBody RealEntity realEntity) throws URISyntaxException {
        RealEntity real = realEntityService.createRealEntity(realEntity);
        URI uri = new URI("api/realentitys" + real.getAddress());
        return ResponseEntity.created(uri).body(real);
    }
}
