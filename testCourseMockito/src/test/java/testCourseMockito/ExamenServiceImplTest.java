package testCourseMockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

import testCourseMockito.ejemplos.modelos.Examen;
import testCourseMockito.ejemplos.repositories.ExamenRepository;
import testCourseMockito.ejemplos.services.ExamenService;
import testCourseMockito.ejemplos.services.ExamenServiceImpl;

public class ExamenServiceImplTest {
	
	/**
	 * 1. 
	 */
    @Test
    void findExamenPorNombre() {
    	
    	ExamenRepository repository = mock(ExamenRepository.class);
    	ExamenService service = new ExamenServiceImpl(repository, null);
    	Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
    	List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(5L, "Lenguaje"),
        		new Examen(5L, "Historia"));
    	
    	when(repository.findAll()).thenReturn(datos);
    	
    	assertNotNull(examen);
    	assertEquals(5L, examen.getId());
    	assertEquals("Matematicas", examen.getNombre());
    }
}
