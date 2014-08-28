//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.28 at 01:42:43 PM CST 
//


package org.voltdb.compiler.deploymentfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityProviderString.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SecurityProviderString">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="hash"/>
 *     &lt;enumeration value="kerberos"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SecurityProviderString")
@XmlEnum
public enum SecurityProviderString {

    @XmlEnumValue("hash")
    HASH("hash"),
    @XmlEnumValue("kerberos")
    KERBEROS("kerberos");
    private final String value;

    SecurityProviderString(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SecurityProviderString fromValue(String v) {
        for (SecurityProviderString c: SecurityProviderString.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
