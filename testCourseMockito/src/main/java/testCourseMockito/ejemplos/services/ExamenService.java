package testCourseMockito.ejemplos.services;

import java.util.Optional;

import testCourseMockito.ejemplos.modelos.Examen;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);

    Examen findExamenPorNombreConPreguntas(String nombre);

    Examen guardar(Examen examen);
}
