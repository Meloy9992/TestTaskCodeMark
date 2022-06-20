
package com.example.TestTask.TestTaskXmlService;

import com.example.TestTask.TestTaskXmlService.DeleteUserByLoginRequest;
import com.example.TestTask.TestTaskXmlService.DeleteUserByLoginResponse;
import com.example.TestTask.TestTaskXmlService.GetListUsersWithoutRolesResponse;
import com.example.TestTask.TestTaskXmlService.GetUserByLoginWithRolesRequest;
import com.example.TestTask.TestTaskXmlService.GetUserByLoginWithRolesResponse;
import com.example.TestTask.TestTaskXmlService.RolesXml;
import com.example.TestTask.TestTaskXmlService.UserXml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the localhost._8082 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetListUsersWithoutRolesRequest_QNAME = new QName("http://localhost:8082", "getListUsersWithoutRolesRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: localhost._8082
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetListUsersWithoutRolesResponse }
     * 
     */
    public GetListUsersWithoutRolesResponse createGetListUsersWithoutRolesResponse() {
        return new GetListUsersWithoutRolesResponse();
    }

    /**
     * Create an instance of {@link UserXml }
     * 
     */
    public UserXml createUser() {
        return new UserXml();
    }

    /**
     * Create an instance of {@link GetUserByLoginWithRolesRequest }
     * 
     */
    public GetUserByLoginWithRolesRequest createGetUserByLoginWithRolesRequest() {
        return new GetUserByLoginWithRolesRequest();
    }

    /**
     * Create an instance of {@link GetUserByLoginWithRolesResponse }
     * 
     */
    public GetUserByLoginWithRolesResponse createGetUserByLoginWithRolesResponse() {
        return new GetUserByLoginWithRolesResponse();
    }

    /**
     * Create an instance of {@link DeleteUserByLoginRequest }
     * 
     */
    public DeleteUserByLoginRequest createDeleteUserByLoginRequest() {
        return new DeleteUserByLoginRequest();
    }

    /**
     * Create an instance of {@link DeleteUserByLoginResponse }
     * 
     */
    public DeleteUserByLoginResponse createDeleteUserByLoginResponse() {
        return new DeleteUserByLoginResponse();
    }

    /**
     * Create an instance of {@link AddNewUserWithRolesRequest }
     * 
     */
    public AddNewUserWithRolesRequest createAddNewUserWithRolesRequest() {
        return new AddNewUserWithRolesRequest();
    }

    /**
     * Create an instance of {@link AddNewUserWithRolesResponse }
     * 
     */
    public AddNewUserWithRolesResponse createAddNewUserWithRolesResponse() {
        return new AddNewUserWithRolesResponse();
    }

    /**
     * Create an instance of {@link EditUserRequest }
     * 
     */
    public EditUserRequest createEditUserRequest() {
        return new EditUserRequest();
    }

    /**
     * Create an instance of {@link EditUserResponse }
     * 
     */
    public EditUserResponse createEditUserResponse() {
        return new EditUserResponse();
    }

    /**
     * Create an instance of {@link RolesXml }
     * 
     */
    public RolesXml createRoles() {
        return new RolesXml();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://localhost:8082", name = "getListUsersWithoutRolesRequest")
    public JAXBElement<Object> createGetListUsersWithoutRolesRequest(Object value) {
        return new JAXBElement<Object>(_GetListUsersWithoutRolesRequest_QNAME, Object.class, null, value);
    }

}
