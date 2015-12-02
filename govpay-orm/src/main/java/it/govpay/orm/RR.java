/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.govpay.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for RR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR">
 * 		&lt;sequence>
 * 			&lt;element name="idRT" type="{http://www.govpay.it/orm}id-rt" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idTracciatoXML" type="{http://www.govpay.it/orm}id-tracciato" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="codMsgRevoca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dataOraMsgRevoca" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="importoTotaleRevocato" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="descrizioneStato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="dataOraCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR", 
  propOrder = {
  	"idRT",
  	"idTracciatoXML",
  	"codMsgRevoca",
  	"dataOraMsgRevoca",
  	"importoTotaleRevocato",
  	"stato",
  	"descrizioneStato",
  	"dataOraCreazione"
  }
)

@XmlRootElement(name = "RR")

public class RR extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public RR() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public IdRt getIdRT() {
    return this.idRT;
  }

  public void setIdRT(IdRt idRT) {
    this.idRT = idRT;
  }

  public IdTracciato getIdTracciatoXML() {
    return this.idTracciatoXML;
  }

  public void setIdTracciatoXML(IdTracciato idTracciatoXML) {
    this.idTracciatoXML = idTracciatoXML;
  }

  public java.lang.String getCodMsgRevoca() {
    return this.codMsgRevoca;
  }

  public void setCodMsgRevoca(java.lang.String codMsgRevoca) {
    this.codMsgRevoca = codMsgRevoca;
  }

  public java.util.Date getDataOraMsgRevoca() {
    return this.dataOraMsgRevoca;
  }

  public void setDataOraMsgRevoca(java.util.Date dataOraMsgRevoca) {
    this.dataOraMsgRevoca = dataOraMsgRevoca;
  }

  public double getImportoTotaleRevocato() {
    return this.importoTotaleRevocato;
  }

  public void setImportoTotaleRevocato(double importoTotaleRevocato) {
    this.importoTotaleRevocato = importoTotaleRevocato;
  }

  public java.lang.String getStato() {
    return this.stato;
  }

  public void setStato(java.lang.String stato) {
    this.stato = stato;
  }

  public java.lang.String getDescrizioneStato() {
    return this.descrizioneStato;
  }

  public void setDescrizioneStato(java.lang.String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
  }

  public java.util.Date getDataOraCreazione() {
    return this.dataOraCreazione;
  }

  public void setDataOraCreazione(java.util.Date dataOraCreazione) {
    this.dataOraCreazione = dataOraCreazione;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static it.govpay.orm.model.RRModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(it.govpay.orm.RR.modelStaticInstance==null){
  			it.govpay.orm.RR.modelStaticInstance = new it.govpay.orm.model.RRModel();
	  }
  }
  public static it.govpay.orm.model.RRModel model(){
	  if(it.govpay.orm.RR.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return it.govpay.orm.RR.modelStaticInstance;
  }


  @XmlElement(name="idRT",required=true,nillable=false)
  protected IdRt idRT;

  @XmlElement(name="idTracciatoXML",required=true,nillable=false)
  protected IdTracciato idTracciatoXML;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codMsgRevoca",required=true,nillable=false)
  protected java.lang.String codMsgRevoca;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataOraMsgRevoca",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataOraMsgRevoca;

  @javax.xml.bind.annotation.XmlSchemaType(name="double")
  @XmlElement(name="importoTotaleRevocato",required=true,nillable=false)
  protected double importoTotaleRevocato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="stato",required=true,nillable=false)
  protected java.lang.String stato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="descrizioneStato",required=false,nillable=false)
  protected java.lang.String descrizioneStato;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="dataOraCreazione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataOraCreazione;

}
