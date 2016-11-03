package it.govpay.bd.pagamento.util;

import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.text.StrSubstitutor;
import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.model.Applicazione;
import it.govpay.model.Dominio;

public class CustomIuv {
	
	public final String getCodApplicazione(Dominio dominio, String iuv, Applicazione applicazioneDefault) throws ServiceException {
		try {
			return getCodApplicazione(dominio.getCodDominio(), iuv);
		} catch (NotImplementedException e){
			return applicazioneDefault != null ? applicazioneDefault.getCodApplicazione() : null;
		}
	}
	
	protected String getCodApplicazione(String dominio, String iuv) throws ServiceException, NotImplementedException {
		throw new NotImplementedException();
	}
	
	public String buildPrefix(Applicazione applicazione, Dominio dominio, Map<String, String> values) throws ServiceException, NotImplementedException {
		String prefix = dominio.getIuvPrefix();
		
		if(prefix == null) return "";
		
		StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
		String result = sub.replace(prefix);
	
		// il prefix risultante deve essere numerico, altrimenti lancio eccezione
		try {
			Integer.parseInt(result);
		} catch (NumberFormatException e) {
			throw new ServiceException("La regola IUV [Dominio:"+dominio.getCodDominio()+" PrefixRule:"+dominio.getIuvPrefix()+"] non ha prodotto un prefisso numerico: " + result);
		}
		
		return result;
	}
}
