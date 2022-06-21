package com.example.TestTask.dao.daoImpl;

import com.example.TestTask.dao.RolesDao;
import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

import static com.example.TestTask.utils.HibernateUtil.getSessionFactory;

@Repository
public class RolesDaoImpl implements RolesDao {
    @Override
    public boolean EditListRoles(List<Roles> rolesList) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        for (int i = 0; i < rolesList.size(); i++) {
            session.save(rolesList.get(i));
        }

        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean AddNewRoles(Roles roles) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(roles);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean DeleteRole(Users users) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        try {
            session.createQuery("DELETE Roles WHERE user.login = :login")
                    .setParameter("login", users.getLogin())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    @Override
    public Roles getRoles(long id) {
        Session session = getSessionFactory().openSession();

        Query query = session.createQuery("from Roles where id = :id");
        query.setParameter("id", id);
        Roles role = (Roles) query.getSingleResult();
        session.close();
        return role;
    }

    @Override
    public Boolean existsByRoleNameAndFirstName(String newRoleName, String login) {
        Session session = getSessionFactory().openSession();
        Query query = session.createSQLQuery("SELECT EXISTS(SELECT name_role FROM roles WHERE name_role = :nameRole AND login = :login)")
                .setParameter("login", login)
                .setParameter("nameRole", newRoleName);
        boolean isExist = (boolean) query.getSingleResult();
        session.close();
        return isExist;
    }


}
