package it.govpay.core.rs.v1.beans;

import java.net.URI;
import java.util.List;

public class ListaUnitaOperative extends Lista<it.govpay.core.rs.v1.beans.base.UnitaOperativa> {

	public ListaUnitaOperative(List<it.govpay.core.rs.v1.beans.base.UnitaOperativa> risultati, URI requestUri, long count, long pagina, long limit) {
		super(risultati, requestUri, count, pagina, limit);
	}
	
}
