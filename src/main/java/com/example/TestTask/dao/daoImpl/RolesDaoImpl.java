package com.example.TestTask.dao.daoImpl;

import com.example.TestTask.dao.RolesDao;
import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.TestTask.utils.HibernateUtil.getSessionFactory;

@Repository
public class RolesDaoImpl implements RolesDao {
    @Override
    public boolean EditListRoles(List<Roles> rolesList) {
        return false;
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

        try{
            session.createQuery("DELETE Roles WHERE user.login = :login")
                    .setParameter("login", users.getLogin())
                    .executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.close();
            return false;
        }
        session.close();
        return true;
    }
}
