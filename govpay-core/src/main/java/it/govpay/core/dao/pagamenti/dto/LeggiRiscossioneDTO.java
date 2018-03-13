/**
 * 
 */
package it.govpay.core.dao.pagamenti.dto;

import it.govpay.core.dao.anagrafica.dto.BasicCreateRequestDTO;
import it.govpay.model.IAutorizzato;

/**
 * @author Bussu Giovanni (bussu@link.it)
 * @author  $Author: bussu $
 * @version $ Rev: 12563 $, $Date: 01 feb 2018 $
 * 
 */
public class LeggiRiscossioneDTO extends BasicCreateRequestDTO {


	public LeggiRiscossioneDTO(IAutorizzato user, String idDominio, String iuv, String iur, Integer indice) {
		super(user);
		this.idDominio=idDominio;
		this.iuv=iuv;
		this.iur=iur;
		this.indice=indice;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	private String idDominio;
	private String iuv;
	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getIur() {
		return iur;
	}

	public void setIur(String iur) {
		this.iur = iur;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	private String iur;
	private Integer indice;
}
