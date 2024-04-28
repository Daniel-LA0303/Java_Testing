package testCourseMockito.ejemplos.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import testCourseMockito.ejemplos.modelos.Examen;

public class ExamenRepositoryImpl implements ExamenRepository{
	
    @Override
    public Examen guardar(Examen examen) {
        return null;
    }

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepositoryOtro");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(new Examen(5L, "Matematicas"), new Examen(5L, "Lenguaje"),
        		new Examen(5L, "Historia"));
    }

}
