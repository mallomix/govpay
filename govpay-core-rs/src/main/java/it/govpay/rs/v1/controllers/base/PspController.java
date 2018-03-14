package it.govpay.rs.v1.controllers.base;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;

import it.govpay.core.dao.anagrafica.PspDAO;
import it.govpay.core.dao.anagrafica.dto.LeggiCanaleDTO;
import it.govpay.core.dao.anagrafica.dto.LeggiCanaleDTOResponse;
import it.govpay.core.dao.anagrafica.dto.LeggiPspDTO;
import it.govpay.core.dao.anagrafica.dto.LeggiPspDTOResponse;
import it.govpay.core.dao.anagrafica.dto.ListaCanaliDTO;
import it.govpay.core.dao.anagrafica.dto.ListaCanaliDTOResponse;
import it.govpay.core.dao.anagrafica.dto.ListaPspDTO;
import it.govpay.core.dao.anagrafica.dto.ListaPspDTOResponse;
import it.govpay.core.dao.anagrafica.exception.PspNonTrovatoException;
import it.govpay.core.rs.v1.beans.ListaCanali;
import it.govpay.core.rs.v1.beans.ListaPsp;
import it.govpay.core.rs.v1.beans.base.FaultBean;
import it.govpay.core.rs.v1.beans.base.FaultBean.CategoriaEnum;
import it.govpay.core.utils.GpContext;
import it.govpay.core.utils.GpThreadLocal;
import it.govpay.model.Canale.TipoVersamento;
import it.govpay.model.IAutorizzato;
import it.govpay.rs.v1.beans.converter.PspConverter;



public class PspController extends it.govpay.rs.BaseController {

     public PspController(String nomeServizio,Logger log) {
		super(nomeServizio,log);
     }

    public Response pspIdPspCanaliGET(IAutorizzato user, UriInfo uriInfo, HttpHeaders httpHeaders , String idPsp, Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, String modello, String tipoVersamento) {
    	String methodName = "pspIdPspCanaliGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			// Parametri - > DTO Input
			
			ListaCanaliDTO listaCanaliDTO = new ListaCanaliDTO(user);
			
			listaCanaliDTO.setPagina(pagina);
			listaCanaliDTO.setLimit(risultatiPerPagina);
			listaCanaliDTO.setOrderBy(ordinamento);
			listaCanaliDTO.setAbilitato(abilitato);
			listaCanaliDTO.setModello(modello);
			listaCanaliDTO.setTipoVersamento(tipoVersamento);
			listaCanaliDTO.setIdPsp(idPsp);
			
			// INIT DAO
			
			PspDAO pspDAO = new PspDAO();
			
			// CHIAMATA AL DAO
			
			ListaCanaliDTOResponse listaDTOResponse = pspDAO.listaCanali(listaCanaliDTO);
			
			// CONVERT TO JSON DELLA RISPOSTA
			
			List<it.govpay.core.rs.v1.beans.Canale> results = new ArrayList<it.govpay.core.rs.v1.beans.Canale>();
			for(LeggiCanaleDTOResponse elem: listaDTOResponse.getResults()) {
				results.add(PspConverter.toCanaleRsModel(elem.getCanale(), elem.getPsp()));
			}
			
			ListaCanali response = new ListaCanali(results, uriInfo.getRequestUri(),
					listaDTOResponse.getTotalResults(), pagina, risultatiPerPagina);
			
			this.logResponse(uriInfo, httpHeaders, methodName, response.toJSON(campi), 200);
			this.log.info("Esecuzione " + methodName + " completata."); 
			return Response.status(Status.OK).entity(response.toJSON(campi)).build();
			
		}catch (Exception e) {
			log.error("Errore interno durante la ricerca dei canali: " + e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo.toJSON(null)).build();
		} finally {
			if(ctx != null) ctx.log();
		}

    }

    public Response pspIdPspCanaliIdCanaleTipoVersamentoGET(IAutorizzato user, UriInfo uriInfo, HttpHeaders httpHeaders , String idPsp, String idCanale, String tipoVersamento) {
    	String methodName = "pspIdPspCanaliIdCanaleGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
			
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			LeggiCanaleDTO leggiPspDTO = new LeggiCanaleDTO(user);
			leggiPspDTO.setIdPsp(idPsp);
			leggiPspDTO.setIdCanale(idCanale);
			leggiPspDTO.setTipoVersamento(TipoVersamento.toEnum(tipoVersamento));
			
			PspDAO pspDAO = new PspDAO(); 
			
			LeggiCanaleDTOResponse leggiPspDTOResponse = pspDAO.leggiCanale(leggiPspDTO);
			
			it.govpay.core.rs.v1.beans.Canale response = PspConverter.toCanaleRsModel(leggiPspDTOResponse.getCanale(), leggiPspDTOResponse.getPsp());
			return Response.status(Status.OK).entity(response.toJSON(null)).build();
			
		}catch (PspNonTrovatoException e) {
			log.error(e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.OPERAZIONE);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), Status.NOT_FOUND.getStatusCode());
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.NOT_FOUND).entity(respKo.toJSON(null)).build();
		}catch (Exception e) {
			log.error("Errore interno durante la " + methodName, e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo.toJSON(null)).build();
		} finally {
			if(ctx != null) ctx.log();
		}
    }



    public Response pspIdPspGET(IAutorizzato user, UriInfo uriInfo, HttpHeaders httpHeaders , String idPsp) {
    	String methodName = "pspIdPspGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
			
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			LeggiPspDTO leggiPspDTO = new LeggiPspDTO(user);
			leggiPspDTO.setIdPsp(idPsp);
			
			PspDAO pspDAO = new PspDAO(); 
			
			LeggiPspDTOResponse leggiPspDTOResponse = pspDAO.leggiPsp(leggiPspDTO);
			
			it.govpay.core.rs.v1.beans.Psp response = PspConverter.toRsModel(leggiPspDTOResponse.getPsp());
			return Response.status(Status.OK).entity(response.toJSON(null)).build();
			
		}catch (PspNonTrovatoException e) {
			log.error(e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.OPERAZIONE);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), Status.NOT_FOUND.getStatusCode());
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.NOT_FOUND).entity(respKo.toJSON(null)).build();
		}catch (Exception e) {
			log.error("Errore interno durante la " + methodName, e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo.toJSON(null)).build();
		} finally {
			if(ctx != null) ctx.log();
		}
    }



    public Response pspGET(IAutorizzato user, UriInfo uriInfo, HttpHeaders httpHeaders , Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, Boolean bollo, Boolean storno) {
    	String methodName = "pspGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			// Parametri - > DTO Input
			
			ListaPspDTO listaPspDTO = new ListaPspDTO(user);
			
			listaPspDTO.setPagina(pagina);
			listaPspDTO.setLimit(risultatiPerPagina);
			listaPspDTO.setOrderBy(ordinamento);
			listaPspDTO.setAbilitato(abilitato);
			listaPspDTO.setBollo(bollo);
			listaPspDTO.setStorno(storno);
			
			// INIT DAO
			
			PspDAO pspDAO = new PspDAO();
			
			// CHIAMATA AL DAO
			
			ListaPspDTOResponse listaPspDTOResponse = pspDAO.listaPsp(listaPspDTO);
			
			// CONVERT TO JSON DELLA RISPOSTA
			
			List<it.govpay.core.rs.v1.beans.Psp> results = new ArrayList<it.govpay.core.rs.v1.beans.Psp>();
			for(it.govpay.bd.model.Psp psp: listaPspDTOResponse.getResults()) {
				results.add(PspConverter.toRsModel(psp));
			}
			
			ListaPsp response = new ListaPsp(results, uriInfo.getRequestUri(),
					listaPspDTOResponse.getTotalResults(), pagina, risultatiPerPagina);
			
			this.logResponse(uriInfo, httpHeaders, methodName, response.toJSON(campi), 200);
			this.log.info("Esecuzione " + methodName + " completata."); 
			return Response.status(Status.OK).entity(response.toJSON(campi)).build();
			
		}catch (Exception e) {
			log.error("Errore interno durante la ricerca dei PSP: " + e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo.toJSON(null), 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo.toJSON(null)).build();
		} finally {
			if(ctx != null) ctx.log();
		}
    }


}


