package testCourseMockito.ejemplos.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import testCourseMockito.ejemplos.modelos.Examen;

public class ExamenRepositoryOtro implements ExamenRepository{
	
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
        return null;
    }

}
