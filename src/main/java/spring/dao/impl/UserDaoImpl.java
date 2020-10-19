package spring.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import spring.dao.UserDao;
import spring.model.User;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(User user) {
        Transaction transaction = null;
        Session session = null;
        log.info("Method add() called");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("User " + user + " added into DB.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant add user "
                    + user + " into DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<User> findAllUsers() {
        log.info("findAllUsers() method called");
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> users = query.getResultList();
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Can't get users list.", e);
        }
    }
}
