package it.govpay.rs.v1.beans.ragioneria.converter;

import javax.xml.bind.JAXBException;

import org.openspcoop2.generic_project.exception.ServiceException;
import org.xml.sax.SAXException;

import it.govpay.core.rs.v1.beans.ragioneria.RppIndex;
import it.govpay.core.utils.JaxbUtils;

public class RptConverter {



	public static RppIndex toRsModelIndex(it.govpay.bd.model.Rpt rpt, it.govpay.bd.model.Versamento versamento, it.govpay.bd.model.Applicazione applicazione) throws ServiceException {
		RppIndex rsModel = new RppIndex();

		rsModel.setStato(rpt.getStato().toString());
		rsModel.setDettaglioStato(rpt.getDescrizioneStato());

		try {
			rsModel.setRpt(JaxbUtils.toRPT(rpt.getXmlRpt()));

			if(rpt.getXmlRt() != null) {
				rsModel.setRt(JaxbUtils.toRT(rpt.getXmlRt()));
			}
		} catch(SAXException e) {
			throw new ServiceException(e);
		} catch (JAXBException e) {
			throw new ServiceException(e);
		}

		return rsModel;
	}
}
