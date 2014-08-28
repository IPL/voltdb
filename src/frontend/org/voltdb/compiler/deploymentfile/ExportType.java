//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.28 at 01:42:43 PM CST 
//


package org.voltdb.compiler.deploymentfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configuration" type="{}ExportConfigurationType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="target" type="{}ServerExportEnum" default="file" />
 *       &lt;attribute name="exportconnectorclass" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportType", propOrder = {
    "configuration"
})
public class ExportType {

    protected ExportConfigurationType configuration;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "target")
    protected ServerExportEnum target;
    @XmlAttribute(name = "exportconnectorclass")
    protected String exportconnectorclass;

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link ExportConfigurationType }
     *     
     */
    public ExportConfigurationType getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportConfigurationType }
     *     
     */
    public void setConfiguration(ExportConfigurationType value) {
        this.configuration = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link ServerExportEnum }
     *     
     */
    public ServerExportEnum getTarget() {
        if (target == null) {
            return ServerExportEnum.FILE;
        } else {
            return target;
        }
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerExportEnum }
     *     
     */
    public void setTarget(ServerExportEnum value) {
        this.target = value;
    }

    /**
     * Gets the value of the exportconnectorclass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportconnectorclass() {
        if (exportconnectorclass == null) {
            return "";
        } else {
            return exportconnectorclass;
        }
    }

    /**
     * Sets the value of the exportconnectorclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportconnectorclass(String value) {
        this.exportconnectorclass = value;
    }

}
