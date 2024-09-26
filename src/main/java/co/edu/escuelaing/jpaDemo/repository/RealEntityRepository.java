package co.edu.escuelaing.jpaDemo.repository;


import co.edu.escuelaing.jpaDemo.model.RealEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEntityRepository extends CrudRepository<RealEntity, Long> {

}
