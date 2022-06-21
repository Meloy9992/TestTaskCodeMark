package com.example.TestTask.endpoints;

import com.example.TestTask.TestTaskXmlService.*;
import com.example.TestTask.dao.RolesDao;
import com.example.TestTask.dao.UsersDao;
import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082";

    private UsersDao usersDao;
    private RolesDao rolesDao;

    public void setup() {
        UsersPortService service = new UsersPortService();
        UsersPort countryService = service.getUsersPortSoap11();
    }

    @Autowired
    public UsersEndpoint(UsersDao usersDao, RolesDao rolesDao) {
        this.usersDao = usersDao;
        this.rolesDao = rolesDao;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddNewUserWithRolesRequest")
    @ResponsePayload
    public AddNewUserWithRolesResponse AddNewUserWithRoles(@RequestPayload AddNewUserWithRolesRequest request) {
        AddNewUserWithRolesResponse response = new AddNewUserWithRolesResponse();

        UserXml userXml = request.getUser();

        HashMap<Boolean, List<Exception>> formatter = formatterExceptionUserXml(userXml);

        if (formatter.containsKey(false)) {
            response.setSuccess(false);

            List<String> errorsString = new ArrayList<>();
            for (int i = 0; i < formatter.get(false).size(); i++) {
                errorsString.add(formatter.get(false).get(i).toString());
            }
            response.setErrors(errorsString);
            return response;
        }

        Users user = unmarshal(userXml);

        try {
            usersDao.addNewUserWithRoles(user);

            for (int i = 0; i < user.getRolesList().size(); i++)
                rolesDao.AddNewRoles(user.getRolesList().get(i));

        } catch (Exception e) {
            System.out.println("Неудалось добавить информацию в БД");
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListUsersWithoutRolesRequest")
    @ResponsePayload
    public GetListUsersWithoutRolesResponse getListUsersWithoutRoles(@RequestPayload GetListUsersWithoutRolesRequest request) throws JAXBException, IOException {
        GetListUsersWithoutRolesResponse response = new GetListUsersWithoutRolesResponse();
        List<UserXml> userListWithoutRoles = usersDao.getListUsersWithoutRoles();

        response.setUserList(userListWithoutRoles);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByLoginWithRolesRequest")
    @ResponsePayload
    public GetUserByLoginWithRolesResponse getUserByLoginWithRolesResponse(@RequestPayload GetUserByLoginWithRolesRequest request) {
        GetUserByLoginWithRolesResponse response = new GetUserByLoginWithRolesResponse();
        Users user = usersDao.getUserByLoginWithRoles(request.getLogin());

        UserXml userXml = marshal(user);

        response.setUser(userXml);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public DeleteUserByLoginResponse deleteUserByLoginResponse(@RequestPayload DeleteUserByLoginRequest request) {
        DeleteUserByLoginResponse response = new DeleteUserByLoginResponse();

        Users users = usersDao.getUserByLoginWithRoles(request.getLogin());

        rolesDao.DeleteRole(users);
        usersDao.DeleteUserByLogin(request.getLogin());

        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditUserRequest")
    @ResponsePayload
    public EditUserResponse editUserResponse(@RequestPayload EditUserRequest request) {
        EditUserResponse response = new EditUserResponse();
        List<Roles> newRolesList = new ArrayList<>();

        Users user = unmarshal(request.getUserXml());

        HashMap<Boolean, List<Exception>> formatter = formatterExceptionUser(user);

        if (formatter.containsKey(false)) {
            response.setSuccess(false);

            List<String> errorsString = new ArrayList<>();
            for (int i = 0; i < formatter.get(false).size(); i++) {
                errorsString.add(formatter.get(false).get(i).toString());
            }
            response.setErrors(errorsString);
            return response;
        }

        List<Roles> usersRoleList = user.getRolesList();

        Users userDb = usersDao.getUserByLoginWithRoles(user.getLogin());

        List<Roles> dbRoleList = userDb.getRolesList();

        for (int i = 0; i < usersRoleList.size(); i++) {
            if (rolesDao.existsByRoleName(usersRoleList.get(i).getNameRole(), user.getLogin())) {
                for (int j = 0; j < dbRoleList.size(); j++) {
                    if (dbRoleList.get(j).getNameRole().hashCode() == usersRoleList.get(i).getNameRole().hashCode()) {
                        dbRoleList.get(j).setUser(user);
                        newRolesList.add(dbRoleList.get(j));
                    }
                }
            } else {
                newRolesList.add(user.getRolesList().get(i));
            }
        }
        user.setRolesList(newRolesList);

        rolesDao.DeleteRole(dbRoleList.get(0).getUser());
        rolesDao.EditListRoles(user.getRolesList());
        usersDao.editUser(user);

        response.setSuccess(true);

        return response;
    }

    public UserXml marshal(Users user) {
        UserXml userXml = new UserXml();
        userXml.setLogin(user.getLogin());
        userXml.setFirstName(user.getFirstName());
        userXml.setPassword(user.getPassword());

        List<Roles> rolesList = user.getRolesList();
        if (rolesList.size() == 0) {
            return userXml;
        } else {

            List<RolesXml> rolesXmlList = new ArrayList<>();
            for (int i = 0; i < user.getRolesList().size(); i++) {
                RolesXml rolesXml = new RolesXml(rolesList.get(i).getId(),
                        rolesList.get(i).getNameRole(), rolesList.get(i).getUser().getLogin());
                rolesXmlList.add(rolesXml);
            }

            userXml.setRoleList(rolesXmlList);
        }
        return userXml;
    }

    public Users unmarshal(UserXml userXml) {
        Users user = new Users(userXml.getLogin(), userXml.getFirstName(), userXml.getPassword());
        List<RolesXml> roleXml = userXml.getRoleList();
        List<Roles> rolesList = new ArrayList<>();

        for (int i = 0; i < roleXml.size(); i++) {
            try {
                Roles role = new Roles();
                if (roleXml.get(i).getId() != null) {
                    role.setId(roleXml.get(i).getId());
                }
                role.setNameRole(roleXml.get(i).getRolesName());
                role.setUser(user);
                rolesList.add(role);
            } catch (Exception e) {
                System.out.println("Не удалось добавить роль");
            }
        }
        user.setRolesList(rolesList);
        return user;
    }

    public HashMap<Boolean, List<Exception>> formatterExceptionUser(Users user) {
        Logger logger = Logger.getLogger(Users.class.getName());
        HashMap<Boolean, List<Exception>> map = new HashMap<>();
        List<Exception> errors = new ArrayList<>();
        try {
            if (user.getFirstName() == null || user.getFirstName() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутствует имя");
            errors.add(e);
        }
        try {
            if (user.getLogin() == null || user.getLogin() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутсвует логин");
            errors.add(e);
        }
        try {
            if (user.getPassword() == null || user.getPassword() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутствует пароль");
            errors.add(e);
        }
        try {
            boolean checkPass = checkPassword(user.getPassword());
            if (checkPass == false) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Неправильный формат пароля");
            errors.add(e);
        }

        if (errors.size() != 0) {
            map.put(false, errors);
            return map;
        } else map.put(true, errors);

        return map;
    }

    public HashMap<Boolean, List<Exception>> formatterExceptionUserXml(UserXml user) {
        Logger logger = Logger.getLogger(Users.class.getName());
        HashMap<Boolean, List<Exception>> map = new HashMap<>();
        List<Exception> errors = new ArrayList<>();

        try {
            if (user.getFirstName() == null || user.getFirstName() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутствует имя");
            errors.add(e);
        }
        try {
            if (user.getLogin() == null || user.getLogin() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутсвует логин");
            errors.add(e);
        }
        try {
            if (user.getPassword() == null || user.getPassword() == "")
                throw new Exception();
        } catch (Exception e) {
            logger.log(Level.INFO, "Отсутствует пароль");
            errors.add(e);
        }
        try {
            boolean checkPass = checkPassword(user.getPassword());
            if (checkPass == false) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Неправильный формат пароля");
            errors.add(e);
        }

        if (errors.size() != 0) {
            map.put(false, errors);
            return map;
        } else map.put(true, errors);

        return map;
    }

    public boolean checkPassword(String password) {
        boolean isCapitalLetter = false;
        char currentCharacter;
        boolean isNumber = false;

        for (int i = 0; i < password.length(); i++) {
            currentCharacter = password.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                isNumber = true;
            }
            if (Character.isUpperCase(currentCharacter)) {
                isCapitalLetter = true;
            }
        }

        if (isCapitalLetter && isNumber == true) {
            return true;
        } else
            return false;

    }

}
