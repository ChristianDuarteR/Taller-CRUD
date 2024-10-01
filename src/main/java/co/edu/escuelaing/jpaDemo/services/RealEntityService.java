package co.edu.escuelaing.jpaDemo.services;

import co.edu.escuelaing.jpaDemo.exception.BadRequestException;
import co.edu.escuelaing.jpaDemo.exception.EntityNotFoundException;
import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.repository.RealEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RealEntityService {

    private final RealEntityRepository realEntityRepository;

    public RealEntityService(RealEntityRepository realEntityRepository) {
        this.realEntityRepository = realEntityRepository;
    }

    public List<RealEntity> getRealEntities() {
        List<RealEntity> realEntities = (List<RealEntity>) realEntityRepository.findAll();
        return realEntities;
    }

    public RealEntity createRealEntity(RealEntity realEntity) throws BadRequestException {
        if (realEntity.getAddress()== null
                || realEntity.getDescription() == null
                || realEntity.getSize() < 0
                || realEntity.getPrice() < 0 ){
            throw new BadRequestException();
        }else {
            return realEntityRepository.save(realEntity);
        }
    }

    public RealEntity updateRealEntity(Long id, RealEntity realEntityData) throws EntityNotFoundException, BadRequestException {
        if (realEntityData.getAddress()== null
                || realEntityData.getDescription() == null
                || realEntityData.getSize() < 0
                || realEntityData.getPrice() < 0 ){
            throw new BadRequestException();
        }else {
            Optional<RealEntity> optionalRealEntity = realEntityRepository.findById(id);
            if (optionalRealEntity.isPresent()) {
                RealEntity realEntity = optionalRealEntity.get();
                realEntity.setAddress(realEntityData.getAddress());
                realEntity.setDescription(realEntityData.getDescription());
                realEntity.setPrice(realEntityData.getPrice());
                realEntity.setSize(realEntityData.getSize());
                realEntityRepository.save(realEntity);
                return realEntity;
            } else {
                throw new EntityNotFoundException(id);
            }
        }
    }

    public void deleteRealEntity(Long id) throws EntityNotFoundException {
        Optional<RealEntity> optionalRealEntity = realEntityRepository.findById(id);
        if (optionalRealEntity.isPresent()){
            RealEntity realEntity = optionalRealEntity.get();
            realEntityRepository.delete(realEntity);
        }else {
            throw new EntityNotFoundException(id);
        }
    }
}
