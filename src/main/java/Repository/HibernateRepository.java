package Repository;

import Entity.Result;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class HibernateRepository implements ResultRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Result saveResult(Result result) {
        System.out.println(result);
        if (result.getId() == null) {
            entityManager.persist(result);
        } else {
            entityManager.merge(result);
        }
        return result;
    }

    @Override
    public List<Result> getAllResults() {
        Query query = entityManager.createQuery("SELECT result FROM Result result", Result.class);
        return (List<Result>) query.getResultList();
    }

    @Override
    @Transactional
    public List<Result> removeAllResults() {
        List<Result> results = getAllResults();
        entityManager.createQuery("DELETE FROM Result").executeUpdate();
        return results;
    }

}