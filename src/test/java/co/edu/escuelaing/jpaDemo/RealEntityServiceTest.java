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

    // Inyecta el servicio que se va a probar.
    @InjectMocks
    private RealEntityService realEntityService;

    // Mockea el repositorio que será usado dentro del servicio.
    @Mock
    private RealEntityRepository realEntityRepository;

    // Inicializa los mocks antes de cada prueba.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba para obtener todas las entidades.
    @Test
    public void testGetRealEntities() {
        // Arrange: Configura los datos simulados para la prueba.
        RealEntity realEntity1 = new RealEntity();
        RealEntity realEntity2 = new RealEntity();
        List<RealEntity> realEntities = Arrays.asList(realEntity1, realEntity2);  // Lista simulada de entidades.

        // Simula el comportamiento del repositorio.
        when(realEntityRepository.findAll()).thenReturn(realEntities);

        // Act: Llama al método del servicio.
        List<RealEntity> result = realEntityService.getRealEntities();

        // Assert: Verifica que el resultado sea el esperado.
        assertEquals(realEntities.size(), result.size());
        assertEquals(realEntities, result);
    }

    // Prueba para crear una entidad válida.
    @Test
    public void testCreateRealEntity_Valid() throws BadRequestException {
        // Arrange: Configura una entidad válida.
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("123 Main St");
        realEntity.setDescription("Nice house");
        realEntity.setSize(100);
        realEntity.setPrice(250000);

        // Simula el guardado de la entidad en el repositorio.
        when(realEntityRepository.save(realEntity)).thenReturn(realEntity);

        // Act: Llama al método del servicio para crear la entidad.
        RealEntity result = realEntityService.createRealEntity(realEntity);

        // Assert: Verifica que la entidad guardada sea igual a la original.
        assertEquals(realEntity, result);
    }

    // Prueba para crear una entidad inválida (dirección nula).
    @Test
    public void testCreateRealEntity_Invalid() {
        // Arrange: Configura una entidad con datos inválidos.
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress(null);  // Dirección nula.

        // Assert: Verifica que se lance una excepción `BadRequestException`.
        assertThrows(BadRequestException.class, () -> realEntityService.createRealEntity(realEntity));
    }

    // Prueba para actualizar una entidad existente con datos válidos.
    @Test
    public void testUpdateRealEntity_Valid() throws EntityNotFoundException, BadRequestException {
        // Arrange: Configura una entidad válida con datos actualizados.
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("Updated Address");
        realEntity.setDescription("Updated description");
        realEntity.setSize(150);
        realEntity.setPrice(300000);

        // Simula la búsqueda y guardado de la entidad en el repositorio.
        when(realEntityRepository.findById(1L)).thenReturn(Optional.of(realEntity));
        when(realEntityRepository.save(realEntity)).thenReturn(realEntity);

        // Act: Llama al método del servicio para actualizar la entidad.
        RealEntity result = realEntityService.updateRealEntity(1L, realEntity);

        // Assert: Verifica que los datos actualizados coincidan con los nuevos valores.
        assertEquals(realEntity.getAddress(), result.getAddress());
        assertEquals(realEntity.getDescription(), result.getDescription());
    }

    // Prueba para actualizar una entidad que no existe (debe lanzar `EntityNotFoundException`).
    @Test
    public void testUpdateRealEntity_NotFound() {
        // Arrange: Configura una entidad y simula que no se encuentra en la base de datos.
        RealEntity realEntity = new RealEntity();
        realEntity.setAddress("Updated Address");
        realEntity.setDescription("Updated description");

        // Simula que el repositorio no encuentra la entidad por ID.
        when(realEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verifica que se lance la excepción `EntityNotFoundException`.
        assertThrows(EntityNotFoundException.class, () -> realEntityService.updateRealEntity(1L, realEntity));
    }

    // Prueba para eliminar una entidad existente.
    @Test
    public void testDeleteRealEntity_Valid() throws EntityNotFoundException {
        // Arrange: Configura una entidad simulada y simula que existe en el repositorio.
        RealEntity realEntity = new RealEntity();

        // Simula la búsqueda y eliminación de la entidad en el repositorio.
        when(realEntityRepository.findById(1L)).thenReturn(Optional.of(realEntity));
        doNothing().when(realEntityRepository).delete(realEntity);

        // Act: Llama al método del servicio para eliminar la entidad.
        realEntityService.deleteRealEntity(1L);

        // Assert: Verifica que el método delete del repositorio se haya llamado una vez.
        verify(realEntityRepository, times(1)).delete(realEntity);
    }

    // Prueba para eliminar una entidad que no existe (debe lanzar `EntityNotFoundException`).
    @Test
    public void testDeleteRealEntity_NotFound() {
        // Arrange: Simula que la entidad no se encuentra en el repositorio.
        when(realEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verifica que se lance la excepción `EntityNotFoundException`.
        assertThrows(EntityNotFoundException.class, () -> realEntityService.deleteRealEntity(1L));
    }
}
