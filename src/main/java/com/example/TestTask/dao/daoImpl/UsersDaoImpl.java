package com.example.TestTask.dao.daoImpl;

import com.example.TestTask.TestTaskXmlService.UserXml;
import com.example.TestTask.dao.RolesDao;
import com.example.TestTask.dao.UsersDao;
import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import static com.example.TestTask.utils.HibernateUtil.getSessionFactory;

@Repository
public class UsersDaoImpl implements UsersDao {
    @Override
    public List<UserXml> getListUsersWithoutRoles() {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery(" select login, firstName, password from Users ");
        List<Object[]> resultList = query.getResultList();

        List<UserXml> usersList = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            Object[] obj = resultList.get(i);
            UserXml userXml = new UserXml();
            userXml.setLogin((String) obj[0]);//login
            userXml.setFirstName((String) obj[1]);//firstName
            userXml.setPassword((String) obj[2]);//pass
            usersList.add(userXml);
        }

        session.close();
        return usersList;
    }

    @Override
    public Users getUserByLoginWithRoles(String login) {
        Session session = getSessionFactory().openSession();

        Query query = session.createQuery("from Users where login = :login");
        query.setParameter("login", login);
        Users users = (Users) query.getSingleResult();

        users.getRolesList();

        session.close();
        return users;
    }

    @Override
    public boolean DeleteUserByLogin(String login) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.createQuery("DELETE Users WHERE login = :login")
                    .setParameter("login", login)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    @Override
    public void addNewUserWithRoles(Users user) {

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean editUser(Users user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
