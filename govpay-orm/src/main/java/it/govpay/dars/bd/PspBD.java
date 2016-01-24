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
package it.govpay.dars.bd;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.filters.PspFilter;
import it.govpay.dars.model.ListaPspEntry;

public class PspBD extends it.govpay.bd.anagrafica.PspBD{

	public PspBD(BasicBD basicBD) {
		super(basicBD);
	}

	public List<ListaPspEntry> findAllPspEntries(PspFilter filter) throws ServiceException {
		try {
			List<ListaPspEntry> pspLst = new ArrayList<ListaPspEntry>();
			List<it.govpay.bd.model.Psp> lstpspDTO = this.findAll(filter);
			for(it.govpay.bd.model.Psp pspVo: lstpspDTO) {
				ListaPspEntry entry = new ListaPspEntry();
				entry.setAttivo(pspVo.isAttivo());
				entry.setId(pspVo.getId());
				entry.setCodFlusso(pspVo.getCodFlusso());
				entry.setCodPsp(pspVo.getCodPsp());
				entry.setRagioneSociale(pspVo.getRagioneSociale());
				entry.setBolloGestito(pspVo.isBolloGestito());
				entry.setStornoGestito(pspVo.isStornoGestito()); 
				pspLst.add(entry);
			}
			return pspLst;
		} catch (ServiceException e) {
			throw e;
		}
	}
}
