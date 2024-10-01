package co.edu.escuelaing.jpaDemo;

import co.edu.escuelaing.jpaDemo.exception.BadRequestException;
import co.edu.escuelaing.jpaDemo.exception.EntityNotFoundException;
import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.repository.RealEntityRepository;
import co.edu.escuelaing.jpaDemo.services.RealEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RealEntityServiceTest {

    @InjectMocks
    private RealEntityService realEntityService;

    @Mock
    private RealEntityRepository realEntityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRealEntities() {
        // Arrange
        RealEntity realEntity1 = new RealEntity();
        RealEntity realEntity2 = new RealEntity();
        List<RealEntity> realEntities = Arrays.asList(realEntity1, realEntity2);

        when(realEntityRepository.findAll()).thenReturn(realEntities);

        // Act
        List<RealEntity> result = realEntityService.getRealEntities();

        // Assert
        assertEquals(realEntities.size(), result.size());
        assertEquals(realEntities, result);
    }

    @Test
    public void testCreateRealEntity_Valid() throws BadRequestException {
        // Arrange
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("123 Main St");
        realEntity.setDescription("Nice house");
        realEntity.setSize(100);
        realEntity.setPrice(250000);

        when(realEntityRepository.save(realEntity)).thenReturn(realEntity);

        // Act
        RealEntity result = realEntityService.createRealEntity(realEntity);

        // Assert
        assertEquals(realEntity, result);
    }

    @Test
    public void testCreateRealEntity_Invalid() {
        // Arrange
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress(null);

        assertThrows(BadRequestException.class, () -> realEntityService.createRealEntity(realEntity));
    }

    @Test
    public void testUpdateRealEntity_Valid() throws EntityNotFoundException, BadRequestException {
        // Arrange
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("Updated Address");
        realEntity.setDescription("Updated description");
        realEntity.setSize(150);
        realEntity.setPrice(300000);

        when(realEntityRepository.findById(1L)).thenReturn(Optional.of(realEntity));
        when(realEntityRepository.save(realEntity)).thenReturn(realEntity);

        // Act
        RealEntity result = realEntityService.updateRealEntity(1L, realEntity);

        // Assert
        assertEquals(realEntity.getAddress(), result.getAddress());
        assertEquals(realEntity.getDescription(), result.getDescription());
    }

    @Test
    public void testUpdateRealEntity_NotFound() {
        // Arrange
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("Updated Address");
        realEntity.setDescription("Updated description");

        when(realEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> realEntityService.updateRealEntity(1L, realEntity));
    }

    @Test
    public void testDeleteRealEntity_Valid() throws EntityNotFoundException {
        // Arrange
        RealEntity realEntity = new RealEntity();

        when(realEntityRepository.findById(1L)).thenReturn(Optional.of(realEntity));
        doNothing().when(realEntityRepository).delete(realEntity);

        // Act
        realEntityService.deleteRealEntity(1L);

        // Assert
        verify(realEntityRepository, times(1)).delete(realEntity);
    }

    @Test
    public void testDeleteRealEntity_NotFound() {
        // Arrange
        when(realEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> realEntityService.deleteRealEntity(1L));
    }
}
