package daos;

import entities.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaEstudianteDAO {
    private EntityManager em;

    public JpaEstudianteDAO(EntityManager em) {
        this.em = em;
    }

    // a) Dar de alta un estudiante
    public void insert(Estudiante estudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(estudiante);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar estudiante! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    // b) Matricular un estudiante en una carrera (Consulta implementada en InscripcionDAO)

    // c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por nombre
    public List<Estudiante> obtenerEstudiantesOrdenadosPorNombre() {
        try {
            return em.createQuery("SELECT e FROM Estudiante e ORDER BY e.nombres", Estudiante.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes ordenados por nombre! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    // d) Recuperar un estudiante en base a su número de libreta universitaria
    public Estudiante obtenerEstudiantePorLu(long lu) {
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.lu = :lu", Estudiante.class)
                    .setParameter("lu", lu)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    // e) Recuperar todos los estudiantes en base a su género
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero) {
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    // f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos (Consulta implementada en CarreraDAO)

    // g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia (Consulta implementada en InscripcionDAO)
}