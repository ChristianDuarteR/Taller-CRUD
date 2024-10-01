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

	@InjectMocks
	private RealEntityController realEntityController;

	@Mock
	private RealEntityService realEntityService;

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

		when(realEntityService.getRealEntities()).thenReturn(realEntities);

		// Act
		ResponseEntity<List<RealEntity>> response = realEntityController.getAllEntities();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(realEntities, response.getBody());
	}

	@Test
	public void testCreateRealEntity() throws URISyntaxException, BadRequestException {
		// Arrange
		RealEntity realEntity = new RealEntity();

		when(realEntityService.createRealEntity(realEntity)).thenReturn(realEntity);

		// Act
		ResponseEntity<RealEntity> response = realEntityController.createRealEntity(realEntity);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(realEntity, response.getBody());
	}

	@Test
	public void testUpdateRealEntity() throws  BadRequestException, EntityNotFoundException {
		// Arrange
		RealEntity realEntity = new RealEntity();
		when(realEntityService.createRealEntity(realEntity)).thenReturn(realEntity);
		RealEntity realEntityData = new RealEntity("MyAddress",1,1,"Muy grande");
		when(realEntityService.updateRealEntity(1L, realEntityData)).thenReturn(realEntity);
		// Act
		ResponseEntity<RealEntity> response = realEntityController.updateRealEntity(1L, realEntityData);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(realEntity, response.getBody());
	}

	@Test
	public void testDeleteRealEntity() {
		// Act
		ResponseEntity<RealEntity> response = realEntityController.deleteRealEntity(1L);
		// Assert
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}

