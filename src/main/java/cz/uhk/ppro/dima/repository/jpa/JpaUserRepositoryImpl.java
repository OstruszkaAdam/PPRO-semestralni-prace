package cz.uhk.ppro.dima.repository.jpa;

import cz.uhk.ppro.dima.model.User;
import cz.uhk.ppro.dima.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return this.em.createQuery("SELECT usrs FROM User usrs").getResultList();
    }

    @Override
    public Optional<User> findById(int id) {
        Query query = this.em.createQuery("SELECT u FROM User u WHERE u.id =:id");
        query.setParameter("id", id);
        return (Optional<User>) query.setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = this.em.createQuery("SELECT u FROM User u WHERE u.username =:username");
        query.setParameter("username", username);
        return (Optional<User>) query.setMaxResults(1).getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public void save(User user) {
            this.em.persist(user);
    }
}
