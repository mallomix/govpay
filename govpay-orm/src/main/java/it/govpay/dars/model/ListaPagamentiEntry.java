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
package it.govpay.dars.model;

import it.govpay.bd.model.Versamento.StatoRendicontazione;

import java.util.Date;

public class ListaPagamentiEntry {
	private long id;
	private String ente;
	private String iuv;
	private String stato;
	private String statoRendicontazione;
	private Date dataOraUltimoAggiornamento;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataOraUltimoAggiornamento() {
		return dataOraUltimoAggiornamento;
	}
	public void setDataOraUltimoAggiornamento(Date dataOraUltimoAggiornamento) {
		this.dataOraUltimoAggiornamento = dataOraUltimoAggiornamento;
	}
	public String getStatoRendicontazione() {
		if(this.statoRendicontazione==null) {
			return StatoRendicontazione.NON_RENDICONTATO.name();
		}
		return statoRendicontazione;
	}
	public void setStatoRendicontazione(String statoRendicontazione) {
		this.statoRendicontazione = statoRendicontazione;
	}

}
