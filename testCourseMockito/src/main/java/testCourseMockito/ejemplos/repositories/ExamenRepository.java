package testCourseMockito.ejemplos.repositories;

import java.util.List;

import testCourseMockito.ejemplos.modelos.Examen;

public interface ExamenRepository {
	
    Examen guardar(Examen examen);
    List<Examen> findAll();

}
