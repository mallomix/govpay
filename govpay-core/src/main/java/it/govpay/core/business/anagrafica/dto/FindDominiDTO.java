/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
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
package it.govpay.core.business.anagrafica.dto;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.core.exceptions.InternalException;
import it.govpay.core.exceptions.RequestParamException;
import it.govpay.model.IAutorizzato;

public class FindDominiDTO extends BasicFindRequestDTO {
	
	public enum Field {
		codDominio(it.govpay.orm.Dominio.model().COD_DOMINIO),
		ragioneSociale(it.govpay.orm.Dominio.model().RAGIONE_SOCIALE);
		
		private IField ifield;

		private Field(IField ifield) {
			this.ifield = ifield;
		}
		
		public IField getField(){
			return ifield;
		}
	}
	
	private String codStazione = null;
	private String codDominio = null;
	private String ragioneSociale = null;
	private Boolean abilitato = null;
	
	public FindDominiDTO(IAutorizzato user) throws ServiceException {
		super(user);
	}

	public String getCodStazione() {
		return codStazione;
	}

	public void setCodStazione(String codStazione) {
		this.codStazione = codStazione;
	}

	public String getCodDominio() {
		return codDominio;
	}

	public void setCodDominio(String codDominio) {
		this.codDominio = codDominio;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	public void setOrderBy(String orderBy) throws RequestParamException, InternalException {
		setOrderBy(Field.class, orderBy);
	}

}
