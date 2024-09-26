package co.edu.escuelaing.jpaDemo.services;

import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.repository.RealEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class RealEntityService {

    private final RealEntityRepository realEntityRepository;

    public RealEntityService(RealEntityRepository realEntityRepository) {
        this.realEntityRepository = realEntityRepository;
    }

    public List<RealEntity> getUsers() {
        List<RealEntity> realEntities = (List<RealEntity>) realEntityRepository.findAll();
        return realEntities;
    }

    public RealEntity createRealEntity(RealEntity realEntity) {
        return realEntityRepository.save(realEntity);
    }
}
