package co.edu.escuelaing.jpaDemo.repository;


import co.edu.escuelaing.jpaDemo.model.RealEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEntityRepository extends JpaRepository<RealEntity, Long> {
    Page<RealEntity> findByAddressContainingAndPriceBetweenAndSizeBetween(
            String address,
            int minPrice,
            int maxPrice,
            int minSize,
            int maxSize,
            Pageable pageable);
}

