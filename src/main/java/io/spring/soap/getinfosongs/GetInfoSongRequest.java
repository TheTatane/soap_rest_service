//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.21 at 06:32:29 PM CET 
//


package io.spring.soap.getinfosongs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name_title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name_artiste" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nameTitle",
    "nameArtiste"
})
@XmlRootElement(name = "getInfoSongRequest")
public class GetInfoSongRequest {

    @XmlElement(name = "name_title", required = true)
    protected String nameTitle;
    @XmlElement(name = "name_artiste", required = true)
    protected String nameArtiste;

    /**
     * Gets the value of the nameTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameTitle() {
        return nameTitle;
    }

    /**
     * Sets the value of the nameTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameTitle(String value) {
        this.nameTitle = value;
    }

    /**
     * Gets the value of the nameArtiste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameArtiste() {
        return nameArtiste;
    }

    /**
     * Sets the value of the nameArtiste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameArtiste(String value) {
        this.nameArtiste = value;
    }

}
