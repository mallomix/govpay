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
package it.govpay.bd.anagrafica;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.filters.PspFilter;
import it.govpay.bd.model.Psp;
import it.govpay.bd.model.Psp.Canale;
import it.govpay.bd.model.converter.CanaleConverter;
import it.govpay.bd.model.converter.PspConverter;
import it.govpay.bd.pagamento.TracciatiBD.TipoTracciato;
import it.govpay.orm.IdCanale;
import it.govpay.orm.IdPsp;
import it.govpay.orm.IdTracciato;
import it.govpay.orm.TracciatoXML;
import it.govpay.orm.dao.IDBCanaleServiceSearch;
import it.govpay.orm.dao.jdbc.JDBCPspServiceSearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.utils.UtilsException;

public class PspBD extends BasicBD {

	public PspBD(BasicBD basicBD) {
		super(basicBD);
	}

	/**
	 * Recupera la lista dei Psp. Abilitati e non.
	 */
	public List<Psp> getPsp() throws ServiceException {
		return this.findAll(this.newFilter());
	}

	/**
	 * Recupera la lista dei Psp.
	 * 
	 * Se boolean == true > ritorna i soli psp abilitati
	 * Se boolean == false > ritorna i soli psp disabilitati
	 * @throws ServiceException
	 */
	public List<Psp> getPsp(boolean abilitato) throws ServiceException {
		try {
			IPaginatedExpression exp = this.getServiceManager().getPspServiceSearch().newPaginatedExpression();
			exp.equals(it.govpay.orm.Psp.model().ABILITATO, abilitato);
			return getPspList(this.getServiceManager().getPspServiceSearch().findAll(exp));
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}
	}

	private List<Psp> getPspList(List<it.govpay.orm.Psp> pspVO) throws ServiceException {
		List<Psp> lst = new ArrayList<Psp>();
		if(pspVO != null && !pspVO.isEmpty()) {
			for (it.govpay.orm.Psp psp : pspVO) {
				lst.add(getPsp(psp));
			}
		}
		return lst;
	}

	private Psp getPsp(it.govpay.orm.Psp pspVO) throws ServiceException {
		try {
			Psp psp = PspConverter.toDTO(pspVO);

			IPaginatedExpression exp = this.getServiceManager().getCanaleService().newPaginatedExpression();
			exp.equals(it.govpay.orm.Canale.model().ID_PSP.COD_PSP, psp.getCodPsp());
			
			List<it.govpay.orm.Canale> canali = this.getServiceManager().getCanaleService().findAll(exp);
			
			if(canali != null && !canali.isEmpty()) {
				List<Canale> canaliLst = new ArrayList<Psp.Canale>();
				for(it.govpay.orm.Canale canaleVO: canali) {
					Canale canale = CanaleConverter.toDTO(canaleVO, psp);
					canaliLst.add(canale);
				}
				psp.setCanali(canaliLst);
			}
			return psp;
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}

	}
	
	/**
	 * Ritorna il psp identificato dal codice fornito.
	 * 
	 * @param codPsp
	 * @return
	 */
	public Psp getPsp(String codPsp) throws NotFoundException, ServiceException, MultipleResultException{
		try {

			IdPsp id = new IdPsp();
			id.setCodPsp(codPsp);
			
			return getPsp(this.getServiceManager().getPspServiceSearch().get(id));
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Ritorna il psp identificato dal codice fornito.
	 * 
	 * @param codPsp
	 * @return
	 */
	public Psp getPsp(Long idPsp) throws NotFoundException, ServiceException, MultipleResultException{
		if(idPsp== null) {
			throw new ServiceException("Parameter 'id' cannot be NULL");
		}
		
		long id = idPsp.longValue();


		try {
			return getPsp(((JDBCPspServiceSearch)this.getServiceManager().getPspServiceSearch()).get(id));
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	public Canale getCanale(Long idCanale) throws NotFoundException, ServiceException, MultipleResultException{
		if(idCanale== null) {
			throw new ServiceException("Parameter 'id' cannot be NULL");
		}
		
		long id = idCanale.longValue();
		try {
			it.govpay.orm.Canale canale = ((IDBCanaleServiceSearch)this.getServiceManager().getCanaleServiceSearch()).get(id);
			Psp psp = getPsp(canale.getIdPsp().getId());
			return CanaleConverter.toDTO(canale, psp);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Aggiorna il PSP esistente con i dati inviati. Se non esiste, dare errore
	 * 
	 * Con l'XML viene registrato il Documento con le seguenti valorizzazioni:
	 * tipoTracciato = psp
	 * codMessaggio = psp.codFlusso
	 * refid = psp.id
	 * 
	 * @param psp
	 * @param xml
	 * @throws ServiceException 
	 * @throws NotFoundException 
	 */
	public void updatePsp(Psp psp, byte[] xml) throws NotFoundException, ServiceException {
		try {
			
			it.govpay.orm.Psp vo = PspConverter.toVO(psp);
			
			IdPsp idVO = this.getServiceManager().getPspServiceSearch().convertToId(vo);
			if(!this.getServiceManager().getPspServiceSearch().exists(idVO)) {
				throw new NotFoundException("Psp con id ["+idVO.toJson()+"] non trovato");
			}
			
			//Inserisco il nuovo tracciato se e solo se il codFlusso e' cambiato
			it.govpay.orm.Psp oldPsp = this.getServiceManager().getPspServiceSearch().get(idVO);
			String codFlussoOld = oldPsp.getCodFlusso();
			if(!psp.getCodFlusso().equals(codFlussoOld)) {
				insertTracciato(vo, xml);				
			} else {
				vo.setIdTracciato(oldPsp.getIdTracciato());
			}
			
			this.getServiceManager().getPspService().update(idVO, vo);
			psp.setId(vo.getId());

			IPaginatedExpression exp = this.getServiceManager().getCanaleService().newPaginatedExpression();
			exp.equals(it.govpay.orm.Canale.model().ID_PSP.COD_PSP, psp.getCodPsp());
			
			List<it.govpay.orm.Canale> findAll = this.getServiceManager().getCanaleService().findAll(exp);
			
			if(findAll != null) {
				for(it.govpay.orm.Canale canaleDaDisabilitare: findAll) {
					canaleDaDisabilitare.setAbilitato(false);
					IdCanale idCanale = this.getServiceManager().getCanaleService().convertToId(canaleDaDisabilitare);
					this.getServiceManager().getCanaleService().update(idCanale, canaleDaDisabilitare);
				}
				
			}
			
			for(Canale canale: psp.getCanali()) {
				canale.setPsp(psp);
				canale.setAbilitato(true);
				it.govpay.orm.Canale canaleVO = CanaleConverter.toVO(canale);
				IdCanale idCanale = new IdCanale();
				idCanale.setCodCanale(canale.getCodCanale());
				idCanale.setTipoVersamento(canale.getTipoVersamento().getCodifica());
				idCanale.setIdPsp(idVO);
				try {
					this.getServiceManager().getCanaleService().update(idCanale, canaleVO);
				} catch (NotFoundException e) {
					this.getServiceManager().getCanaleService().create(canaleVO);
				}
				canale.setId(canaleVO.getId());
			}

		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (UtilsException e) {
			throw new ServiceException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		}

	}
	
	private void insertTracciato(it.govpay.orm.Psp psp, byte[] xml) throws ServiceException, NotImplementedException {
		TracciatoXML tracciatoXML = new TracciatoXML();
		tracciatoXML.setTipoTracciato(TipoTracciato.PSP.name());
		tracciatoXML.setCodMessaggio(psp.getCodFlusso());
		tracciatoXML.setDataOraCreazione(new Date());
		tracciatoXML.setXml(xml);

		this.getServiceManager().getTracciatoXMLService().create(tracciatoXML);
		
		IdTracciato idTracciato = new IdTracciato();
		idTracciato.setId(tracciatoXML.getId());
		
		psp.setIdTracciato(idTracciato);
	}

	/**
	 * Inserisce il PSP con i dati inviati. Se esiste gia', dare errore.
	 * 
	 * Con l'XML viene registrato il Documento con le seguenti valorizzazioni:
	 * tipoTracciato = psp
	 * codMessaggio = psp.codFlusso
	 * refid = psp.id (appena creato)
     * bkey = psp.codPsp
	 * 
	 * @param psp
	 * @param xml
	 * @throws ServiceException 
	 */
	public void insertPsp(Psp psp, byte[] xml) throws ServiceException {
		try {
			it.govpay.orm.Psp vo = PspConverter.toVO(psp);

			IdPsp idVO = this.getServiceManager().getPspServiceSearch().convertToId(vo);
			if(this.getServiceManager().getPspServiceSearch().exists(idVO)) {
				throw new NotFoundException("Psp con id ["+idVO.toJson()+"] gia' esistente");
			}

			insertTracciato(vo, xml);
			this.getServiceManager().getPspService().create(vo);
			psp.setId(vo.getId());
			
			for(Canale canale: psp.getCanali()) {
				canale.setPsp(psp);
				it.govpay.orm.Canale canaleVO = CanaleConverter.toVO(canale);
				this.getServiceManager().getCanaleService().create(canaleVO);
				canale.setId(canaleVO.getId());
			}
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (NotFoundException e) {
			throw new ServiceException(e);
		} catch (UtilsException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Disabilita il PSP aggiornando a FALSE il campo abilitato.
	 * @throws ServiceException 
	 * @throws NotFoundException
	 */
	public void disablePsp(long idPsp) throws ServiceException, NotFoundException {
		try {
			if(!((JDBCPspServiceSearch)this.getServiceManager().getPspServiceSearch()).exists(idPsp)) {
				throw new NotFoundException("Psp con id ["+idPsp+"] non trovato");
			}
			IdPsp idVO = ((JDBCPspServiceSearch)this.getServiceManager().getPspServiceSearch()).findId(idPsp, true);
			
			UpdateField abilitatoField = new UpdateField(it.govpay.orm.Psp.model().ABILITATO, false);
			
			this.getServiceManager().getPspService().updateFields(idVO, abilitatoField);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		}
	}
	
	public PspFilter newFilter() throws ServiceException {
		try {
			return new PspFilter(this.getServiceManager().getPspServiceSearch());
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public long count(PspFilter filter) throws ServiceException {
		try {
			return this.getServiceManager().getPspServiceSearch().count(filter.toExpression()).longValue();
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

	public List<Psp> findAll(PspFilter filter) throws ServiceException {
		try {
			return getPspList(this.getServiceManager().getPspServiceSearch().findAll(filter.toPaginatedExpression()));
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}

}
