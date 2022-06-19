package com.example.TestTask.endpoints;

import com.example.TestTask.TestTaskXmlService.*;
import com.example.TestTask.dao.RolesDao;
import com.example.TestTask.dao.UsersDao;
import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082";

    private UsersDao usersDao;
    private RolesDao rolesDao;

    public void setup(){
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
        List<RolesXml> roleXml = request.getUser().getRoleList();
        List<Roles> rolesList = new ArrayList<>();
        Users user = new Users(userXml.getLogin(), userXml.getFirstName(), userXml.getPassword());

        for (int i=0; i< roleXml.size(); i++){
            try {
                Roles role = new Roles();
                role.setNameRole(roleXml.get(i).getRolesName());
                role.setUser(usersDao.getUserByLoginWithRoles(roleXml.get(i).getLoginUsers()));
                rolesList.add(role);
            }catch (Exception e){
                System.out.println("Не удалось добавить роль");
            }
        }
        user.setRolesList(rolesList);

        try {
            usersDao.addNewUserWithRoles(user);

            for (int i=0; i< rolesList.size(); i++)
                rolesDao.AddNewRoles(rolesList.get(i));

            response.setSuccess(true);
        }catch (Exception e){
            System.out.println("Неудалось добавить информацию в БД");
            response.setSuccess(false);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListUsersWithoutRolesRequest")
    @ResponsePayload
    public GetListUsersWithoutRolesResponse getListUsersWithoutRoles(@RequestPayload GetListUsersWithoutRolesRequest request) throws JAXBException, IOException {
        GetListUsersWithoutRolesResponse response = new GetListUsersWithoutRolesResponse();
        List<UserXml> userListWithoutRoles = usersDao.getListUsersWithoutRoles();

        System.out.println();
        response.setUserList(userListWithoutRoles);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByLoginWithRolesRequest")
    @ResponsePayload
    public GetUserByLoginWithRolesResponse getUserByLoginWithRolesResponse(@RequestPayload GetUserByLoginWithRolesRequest request){
        GetUserByLoginWithRolesResponse response = new GetUserByLoginWithRolesResponse();
        Users user = usersDao.getUserByLoginWithRoles(request.getLogin());

        UserXml userXml = marshal(user);

        response.setUser(userXml);

        return response;
    }
    public UserXml marshal(Users user) {
        UserXml userXml = new UserXml();
        userXml.setLogin(user.getLogin());
        userXml.setFirstName(user.getFirstName());
        userXml.setPassword(user.getPassword());

        List<Roles> rolesList = user.getRolesList();
        if (rolesList.size() == 0){
            return userXml;
        }
        else{

            List<RolesXml> rolesXmlList = new ArrayList<>();
            for (int i=0; i<user.getRolesList().size(); i++){
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
        if (userXml.getRolesList().size() == 0){
            return user;
        } else
            user.setRolesList(userXml.getRolesList());
        return user;
    }
}
