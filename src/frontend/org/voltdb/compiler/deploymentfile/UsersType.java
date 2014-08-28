//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.28 at 01:42:43 PM CST 
//


package org.voltdb.compiler.deploymentfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="groups" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="roles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="password" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="plaintext" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usersType", propOrder = {
    "user"
})
public class UsersType {

    @XmlElement(required = true)
    protected List<UsersType.User> user;

    /**
     * Gets the value of the user property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the user property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsersType.User }
     * 
     * 
     */
    public List<UsersType.User> getUser() {
        if (user == null) {
            user = new ArrayList<UsersType.User>();
        }
        return this.user;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="groups" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="roles" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="password" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="plaintext" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class User {

        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "groups")
        protected String groups;
        @XmlAttribute(name = "roles")
        protected String roles;
        @XmlAttribute(name = "password", required = true)
        protected String password;
        @XmlAttribute(name = "plaintext")
        protected Boolean plaintext;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the groups property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGroups() {
            return groups;
        }

        /**
         * Sets the value of the groups property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGroups(String value) {
            this.groups = value;
        }

        /**
         * Gets the value of the roles property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoles() {
            return roles;
        }

        /**
         * Sets the value of the roles property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoles(String value) {
            this.roles = value;
        }

        /**
         * Gets the value of the password property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the value of the password property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPassword(String value) {
            this.password = value;
        }

        /**
         * Gets the value of the plaintext property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isPlaintext() {
            if (plaintext == null) {
                return true;
            } else {
                return plaintext;
            }
        }

        /**
         * Sets the value of the plaintext property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setPlaintext(Boolean value) {
            this.plaintext = value;
        }

    }

}
