package co.edu.escuelaing.jpaDemo;

import co.edu.escuelaing.jpaDemo.controller.RealEntityController;
import co.edu.escuelaing.jpaDemo.exception.BadRequestException;
import co.edu.escuelaing.jpaDemo.exception.EntityNotFoundException;
import co.edu.escuelaing.jpaDemo.model.RealEntity;
import co.edu.escuelaing.jpaDemo.services.RealEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RealEntityControllerTest {

	// Inyecta el controlador que será probado. Se inyectan los mocks creados con Mockito.
	@InjectMocks
	private RealEntityController realEntityController;

	// Mock del servicio para simular la lógica de negocio sin interactuar con la base de datos real.
	@Mock
	private RealEntityService realEntityService;

	// Inicializa los mocks antes de cada prueba.
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);  // Abre los mocks y los inyecta en las clases correspondientes.
	}

	// Prueba el método GET para obtener todas las entidades.
	@Test
	public void testGetRealEntities() {
		// Arrange: Configura los datos simulados para la prueba.
		RealEntity realEntity1 = new RealEntity();  // Simula la primera entidad.
		RealEntity realEntity2 = new RealEntity();  // Simula la segunda entidad.
		List<RealEntity> realEntities = Arrays.asList(realEntity1, realEntity2);  // Lista de entidades simuladas.

		// Simula el comportamiento del servicio para retornar la lista de entidades.
		when(realEntityService.getRealEntities()).thenReturn(realEntities);

		// Act: Llama al método del controlador.
		ResponseEntity<List<RealEntity>> response = realEntityController.getAllEntities();

		// Assert: Verifica que el código de estado sea 200 (OK) y que el cuerpo de la respuesta sea correcto.
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(realEntities, response.getBody());
	}

	// Prueba el método POST para crear una nueva entidad.
	@Test
	public void testCreateRealEntity() throws URISyntaxException, BadRequestException {
		// Arrange: Crea una entidad simulada.
		RealEntity realEntity = new RealEntity();

		// Simula el comportamiento del servicio para crear la entidad.
		when(realEntityService.createRealEntity(realEntity)).thenReturn(realEntity);

		// Act: Llama al método del controlador.
		ResponseEntity<RealEntity> response = realEntityController.createRealEntity(realEntity);

		// Assert: Verifica que el código de estado sea 201 (CREATED) y que el cuerpo de la respuesta sea correcto.
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(realEntity, response.getBody());
	}

	// Prueba el método PUT para actualizar una entidad existente.
	@Test
	public void testUpdateRealEntity() throws BadRequestException, EntityNotFoundException {
		// Arrange: Crea una entidad simulada y los datos de actualización.
		RealEntity realEntity = new RealEntity();
		when(realEntityService.createRealEntity(realEntity)).thenReturn(realEntity);
		RealEntity realEntityData = new RealEntity("MyAddress", 1, 1, "Muy grande");  // Nuevos datos para la actualización.

		// Simula el comportamiento del servicio para actualizar la entidad.
		when(realEntityService.updateRealEntity(1L, realEntityData)).thenReturn(realEntity);

		// Act: Llama al método del controlador para actualizar la entidad.
		ResponseEntity<RealEntity> response = realEntityController.updateRealEntity(1L, realEntityData);

		// Assert: Verifica que el código de estado sea 201 (CREATED) y que el cuerpo de la respuesta sea correcto.
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(realEntity, response.getBody());
	}

	// Prueba el método DELETE para eliminar una entidad.
	@Test
	public void testDeleteRealEntity() {
		// Act: Llama al método del controlador para eliminar la entidad con ID 1.
		ResponseEntity<RealEntity> response = realEntityController.deleteRealEntity(1L);

		// Assert: Verifica que el código de estado sea 204 (NO CONTENT) indicando que la entidad fue eliminada.
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}
