
package com.example.TestTask.TestTaskXmlService;

import com.example.TestTask.models.Roles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roles complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roles"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="rolesName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loginUsers" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roles", propOrder = {
    "id",
    "rolesName",
    "loginUsers"
})
public class RolesXml {

    protected Long id;
    @XmlElement(required = true)
    protected String rolesName;
    @XmlElement(required = true)
    protected String loginUsers;

    /**
     * Gets the value of the id property.
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the rolesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolesName() {
        return rolesName;
    }

    /**
     * Sets the value of the rolesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolesName(String value) {
        this.rolesName = value;
    }

    /**
     * Gets the value of the loginUsers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginUsers() {
        return loginUsers;
    }

    /**
     * Sets the value of the loginUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginUsers(String value) {
        this.loginUsers = value;
    }

    public RolesXml(Long id, String rolesName, String loginUsers) {
        this.id = id;
        this.rolesName = rolesName;
        this.loginUsers = loginUsers;
    }

    public RolesXml() {
    }
}
