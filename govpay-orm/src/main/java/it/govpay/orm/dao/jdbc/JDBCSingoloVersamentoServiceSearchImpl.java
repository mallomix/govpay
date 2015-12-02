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
package it.govpay.orm.dao.jdbc;

import it.govpay.orm.IdSingoloVersamento;
import it.govpay.orm.IdVersamento;
import it.govpay.orm.SingoloVersamento;
import it.govpay.orm.dao.jdbc.converter.SingoloVersamentoFieldConverter;
import it.govpay.orm.dao.jdbc.fetch.SingoloVersamentoFetch;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.utils.sql.ISQLQueryObject;

/**     
 * JDBCSingoloVersamentoServiceSearchImpl
 *
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCSingoloVersamentoServiceSearchImpl implements IJDBCServiceSearchWithId<SingoloVersamento, IdSingoloVersamento, JDBCServiceManager> {

	private SingoloVersamentoFieldConverter _singoloVersamentoFieldConverter = null;
	public SingoloVersamentoFieldConverter getSingoloVersamentoFieldConverter() {
		if(this._singoloVersamentoFieldConverter==null){
			this._singoloVersamentoFieldConverter = new SingoloVersamentoFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._singoloVersamentoFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getSingoloVersamentoFieldConverter();
	}
	
	private SingoloVersamentoFetch singoloVersamentoFetch = new SingoloVersamentoFetch();
	public SingoloVersamentoFetch getSingoloVersamentoFetch() {
		return this.singoloVersamentoFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getSingoloVersamentoFetch();
	}
	
	
	private JDBCServiceManager jdbcServiceManager = null;

	@Override
	public void setServiceManager(JDBCServiceManager serviceManager) throws ServiceException{
		this.jdbcServiceManager = serviceManager;
	}
	
	@Override
	public JDBCServiceManager getServiceManager() throws ServiceException{
		return this.jdbcServiceManager;
	}
	

	@Override
	public IdSingoloVersamento convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, SingoloVersamento singoloVersamento) throws NotImplementedException, ServiceException, Exception{
	
		IdSingoloVersamento idSingoloVersamento = new IdSingoloVersamento();
		idSingoloVersamento.setIdVersamento(singoloVersamento.getIdVersamento());
		idSingoloVersamento.setIndice(singoloVersamento.getIndice());
	
		return idSingoloVersamento;
	}
	
	@Override
	public SingoloVersamento get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_singoloVersamento = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdSingoloVersamento(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_singoloVersamento,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_singoloVersamento = this.findIdSingoloVersamento(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_singoloVersamento != null && id_singoloVersamento > 0;
		
	}
	
	@Override
	public List<IdSingoloVersamento> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        // default behaviour (id-mapping)
        if(idMappingResolutionBehaviour==null){
                idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
        }

		List<IdSingoloVersamento> list = new ArrayList<IdSingoloVersamento>();

		try{
			List<IField> fields = new ArrayList<IField>();
			fields.add(new CustomField("id_versamento", Long.class, "id_versamento", this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())));
			fields.add(SingoloVersamento.model().INDICE);

			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				IdSingoloVersamento idSingoloVersamento = new IdSingoloVersamento();

				it.govpay.orm.IdVersamento id_singoloVersamento_versamento = null;
				Long idFK_singoloVersamento_versamento = (Long) map.get("id_versamento");
				if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
					id_singoloVersamento_versamento = ((JDBCVersamentoServiceSearch)(this.getServiceManager().getVersamentoServiceSearch())).findId(idFK_singoloVersamento_versamento, false);
				}else{
					id_singoloVersamento_versamento = new it.govpay.orm.IdVersamento();
				}
				id_singoloVersamento_versamento.setId(idFK_singoloVersamento_versamento);
				idSingoloVersamento.setIdVersamento(id_singoloVersamento_versamento);

				idSingoloVersamento.setIndice((Integer) map.get("indice"));
				list.add(idSingoloVersamento);
			}
		} catch(NotFoundException e) {}

        return list;
		
	}
	
	@Override
	public List<SingoloVersamento> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        // default behaviour (id-mapping)
        if(idMappingResolutionBehaviour==null){
                idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
        }

        List<SingoloVersamento> list = new ArrayList<SingoloVersamento>();
        
        try{
			List<IField> fields = new ArrayList<IField>();
			fields.add(new CustomField("id", Long.class, "id", this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())));
			fields.add(SingoloVersamento.model().INDICE);
			fields.add(SingoloVersamento.model().COD_SINGOLO_VERSAMENTO_ENTE);
			fields.add(SingoloVersamento.model().ANNO_RIFERIMENTO);
			fields.add(SingoloVersamento.model().IBAN_ACCREDITO);
			fields.add(SingoloVersamento.model().IMPORTO_SINGOLO_VERSAMENTO);
			fields.add(SingoloVersamento.model().IMPORTO_COMMISSIONI_PA);
			fields.add(SingoloVersamento.model().CAUSALE_VERSAMENTO);
			fields.add(SingoloVersamento.model().DATI_SPECIFICI_RISCOSSIONE);
			fields.add(SingoloVersamento.model().STATO_SINGOLO_VERSAMENTO);
			fields.add(SingoloVersamento.model().SINGOLO_IMPORTO_PAGATO);
			fields.add(SingoloVersamento.model().ESITO_SINGOLO_PAGAMENTO);
			fields.add(SingoloVersamento.model().DATA_ESITO_SINGOLO_PAGAMENTO);
			fields.add(SingoloVersamento.model().IUR);

			fields.add(new CustomField("id_tributo", Long.class, "id_tributo", this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())));
			fields.add(new CustomField("id_versamento", Long.class, "id_versamento", this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())));

			List<Map<String, Object>> returnMap = this.select(jdbcProperties, log, connection, sqlQueryObject, expression, fields.toArray(new IField[1]));

			for(Map<String, Object> map: returnMap) {
				Long idTributo = (Long)map.remove("id_tributo");
				Long idVersamento = (Long)map.remove("id_versamento");

				SingoloVersamento singoloVersamento = (SingoloVersamento)this.getSingoloVersamentoFetch().fetch(jdbcProperties.getDatabase(), SingoloVersamento.model(), map);

				it.govpay.orm.IdTributo id_singoloVersamento_tributo = null;
				if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
					id_singoloVersamento_tributo = ((JDBCTributoServiceSearch)(this.getServiceManager().getTributoServiceSearch())).findId(idTributo, false);
				}else{
					id_singoloVersamento_tributo = new it.govpay.orm.IdTributo();
				}
				id_singoloVersamento_tributo.setId(idTributo);
				singoloVersamento.setIdTributo(id_singoloVersamento_tributo);

				it.govpay.orm.IdVersamento id_singoloVersamento_versamento = null;
				if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
					id_singoloVersamento_versamento = ((JDBCVersamentoServiceSearch)(this.getServiceManager().getVersamentoServiceSearch())).findId(idVersamento, false);
				}else{
					id_singoloVersamento_versamento = new it.govpay.orm.IdVersamento();
				}
				id_singoloVersamento_versamento.setId(idVersamento);
				singoloVersamento.setIdVersamento(id_singoloVersamento_versamento);

				list.add(singoloVersamento);
			}
		} catch(NotFoundException e) {}

        return list;      
		
	}
	
	@Override
	public SingoloVersamento find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
		throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {

        long id = this.findTableId(jdbcProperties, log, connection, sqlQueryObject, expression);
        if(id>0){
        	return this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
        }else{
        	throw new NotFoundException("Entry with id["+id+"] not found");
        }
		
	}
	
	@Override
	public NonNegativeNumber count(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareCount(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model());
		
		sqlQueryObject.addSelectCountField(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_singoloVersamento = this.findIdSingoloVersamento(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_singoloVersamento);
		
	}

	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		List<Map<String,Object>> map = 
			this.select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, distinct, new IField[]{field});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectSingleObject(map);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,field);
		try{
		
			ISQLQueryObject sqlQueryObjectDistinct = 
						org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(distinct,sqlQueryObject, paginatedExpression, log,
												this.getSingoloVersamentoFieldConverter(), field);

			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, sqlQueryObjectDistinct);
			
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,field);
		}
	}

	@Override
	public Object aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		Map<String,Object> map = 
			this.aggregate(jdbcProperties, log, connection, sqlQueryObject, expression, new FunctionField[]{functionField});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectAggregateObject(map,functionField);
	}
	
	@Override
	public Map<String,Object> aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {													
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			List<Map<String,Object>> list = _select(jdbcProperties, log, connection, sqlQueryObject, expression);
			return list.get(0);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(expression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, expression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}
	

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(paginatedExpression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,functionField);
		}
	}
	
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return _select(jdbcProperties, log, connection, sqlQueryObject, expression, null);
	}
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression, ISQLQueryObject sqlQueryObjectDistinct) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		List<Object> listaQuery = new ArrayList<Object>();
		List<JDBCObject> listaParams = new ArrayList<JDBCObject>();
		List<Object> returnField = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSelect(jdbcProperties, log, connection, sqlQueryObject, 
        						expression, this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(),
        								listaQuery,listaParams,returnField);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}
	
	@Override
	public List<Map<String,Object>> union(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnion(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}								
	}
	
	@Override
	public NonNegativeNumber unionCount(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnionCount(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(number!=null && number.longValue()>=0){
			return number;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}



	// -- ConstructorExpression	

	@Override
	public JDBCExpression newExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(this.getSingoloVersamentoFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getSingoloVersamentoFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public JDBCExpression toExpression(JDBCPaginatedExpression paginatedExpression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(paginatedExpression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public JDBCPaginatedExpression toPaginatedExpression(JDBCExpression expression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(expression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	// -- DB

	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id, SingoloVersamento obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, SingoloVersamento obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, SingoloVersamento obj, SingoloVersamento imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdVersamento()!=null && 
				imgSaved.getIdVersamento()!=null){
			obj.getIdVersamento().setId(imgSaved.getIdVersamento().getId());
		}
		if(obj.getIdTributo()!=null && 
				imgSaved.getIdTributo()!=null){
			obj.getIdTributo().setId(imgSaved.getIdTributo().getId());
			if(obj.getIdTributo().getIdEnte()!=null && 
					imgSaved.getIdTributo().getIdEnte()!=null){
				obj.getIdTributo().getIdEnte().setId(imgSaved.getIdTributo().getIdEnte().getId());
			}
		}

	}
	
	@Override
	public SingoloVersamento get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private SingoloVersamento _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		
		IField idField = new CustomField("id", Long.class, "id", this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model()));
		JDBCPaginatedExpression expression = this.newPaginatedExpression(log);
		
		expression.equals(idField, tableId);
		expression.offset(0);
		expression.limit(2);expression.addOrder(idField, org.openspcoop2.generic_project.expression.SortOrder.ASC); //per verificare la multiple results

		List<SingoloVersamento> lst = this.findAll(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), expression, idMappingResolutionBehaviour);
		
		if(lst.size() <=0)
			throw new NotFoundException("Id ["+tableId+"]");
		
		if(lst.size() > 1)
			throw new MultipleResultException("Id ["+tableId+"]");

		return lst.get(0);

	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsSingoloVersamento = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model()));
		sqlQueryObject.addSelectField(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().INDICE,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists singoloVersamento
		existsSingoloVersamento = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsSingoloVersamento;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{

		if(expression.inUseModel(SingoloVersamento.model().ID_TRIBUTO,false)){
			String tableName1 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model());
			String tableName2 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model().ID_TRIBUTO);
			sqlQueryObject.addWhereCondition(tableName1+".id_tributo="+tableName2+".id");
		}
		
		if(expression.inUseModel(SingoloVersamento.model().ID_TRIBUTO.ID_ENTE,false)){

			if(!expression.inUseModel(SingoloVersamento.model().ID_TRIBUTO,false)){
				String tableName1 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model());
				String tableName2 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model().ID_TRIBUTO);
				sqlQueryObject.addWhereCondition(tableName1+".id_tributo="+tableName2+".id");
				sqlQueryObject.addFromTable(tableName2);
			}
			
			String tableName1 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model().ID_TRIBUTO);
			String tableName2 = this.getSingoloVersamentoFieldConverter().toAliasTable(SingoloVersamento.model().ID_TRIBUTO.ID_ENTE);
			sqlQueryObject.addWhereCondition(tableName1+".id_ente="+tableName2+".id");
		}
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
		Long longId = this.findIdSingoloVersamento(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		SingoloVersamentoFieldConverter converter = this.getSingoloVersamentoFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		//		  If a table doesn't have a primary key, don't add it to this map

		// SingoloVersamento.model()
		mapTableToPKColumn.put(converter.toTable(SingoloVersamento.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(SingoloVersamento.model()))
			));

		// SingoloVersamento.model().ID_VERSAMENTO
		mapTableToPKColumn.put(converter.toTable(SingoloVersamento.model().ID_VERSAMENTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(SingoloVersamento.model().ID_VERSAMENTO))
			));

		// SingoloVersamento.model().ID_TRIBUTO
		mapTableToPKColumn.put(converter.toTable(SingoloVersamento.model().ID_TRIBUTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(SingoloVersamento.model().ID_TRIBUTO))
			));

		// SingoloVersamento.model().ID_TRIBUTO.ID_ENTE
		mapTableToPKColumn.put(converter.toTable(SingoloVersamento.model().ID_TRIBUTO.ID_ENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(SingoloVersamento.model().ID_TRIBUTO.ID_ENTE))
			));


        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getSingoloVersamentoFieldConverter(), SingoloVersamento.model(), objectIdClass, listaQuery);
		if(res!=null && (((Long) res).longValue()>0) ){
			return ((Long) res).longValue();
		}
		else{
			throw new NotFoundException("Not Found");
		}
		
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {
		return this._inUse(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private InUse _inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {

		InUse inUse = new InUse();
		inUse.setInUse(false);
		
		/* 
		 * TODO: implement code that checks whether the object identified by the id parameter is used by other objects
		*/
		
		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the method

        return inUse;

	}
	
	@Override
	public IdSingoloVersamento findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _singoloVersamento
		sqlQueryObjectGet.addFromTable(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model()));
		sqlQueryObjectGet.addFromTable(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model().ID_VERSAMENTO));
		sqlQueryObjectGet.addSelectField(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().ID_VERSAMENTO.COD_DOMINIO,true));
		sqlQueryObjectGet.addSelectField(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().ID_VERSAMENTO.COD_VERSAMENTO_ENTE,true));
		sqlQueryObjectGet.addSelectField(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().INDICE,true));
		
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id=?");
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id_versamento="+this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model().ID_VERSAMENTO)+".id");

		// Recupero _singoloVersamento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_singoloVersamento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_singoloVersamento = new ArrayList<Class<?>>();
		listaFieldIdReturnType_singoloVersamento.add(SingoloVersamento.model().ID_VERSAMENTO.COD_DOMINIO.getFieldType());
		listaFieldIdReturnType_singoloVersamento.add(SingoloVersamento.model().ID_VERSAMENTO.COD_VERSAMENTO_ENTE.getFieldType());
		listaFieldIdReturnType_singoloVersamento.add(SingoloVersamento.model().INDICE.getFieldType());

		it.govpay.orm.IdSingoloVersamento id_singoloVersamento = null;
		List<Object> listaFieldId_singoloVersamento = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_singoloVersamento, searchParams_singoloVersamento);
		if(listaFieldId_singoloVersamento==null || listaFieldId_singoloVersamento.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			id_singoloVersamento = new it.govpay.orm.IdSingoloVersamento();
			IdVersamento idVersamento = new IdVersamento();
			idVersamento.setCodDominio((String)listaFieldId_singoloVersamento.get(0));
			idVersamento.setCodVersamentoEnte((String)listaFieldId_singoloVersamento.get(1));
			id_singoloVersamento.setIdVersamento(idVersamento);
			id_singoloVersamento.setIndice((Integer) listaFieldId_singoloVersamento.get(2));
		}
		
		return id_singoloVersamento;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdSingoloVersamento(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdSingoloVersamento(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdSingoloVersamento id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		// Object _singoloVersamento
		sqlQueryObjectGet.addFromTable(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model()));
		sqlQueryObjectGet.addFromTable(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model().ID_VERSAMENTO));
		sqlQueryObjectGet.addSelectField(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id");
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().ID_VERSAMENTO.COD_DOMINIO,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().ID_VERSAMENTO.COD_VERSAMENTO_ENTE,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toColumn(SingoloVersamento.model().INDICE,true)+"=?");
		sqlQueryObjectGet.addWhereCondition(this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model())+".id_versamento="+this.getSingoloVersamentoFieldConverter().toTable(SingoloVersamento.model().ID_VERSAMENTO)+".id");

		// Recupero _singoloVersamento
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_singoloVersamento = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdVersamento().getCodDominio(),SingoloVersamento.model().ID_VERSAMENTO.COD_DOMINIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIdVersamento().getCodVersamentoEnte(),SingoloVersamento.model().ID_VERSAMENTO.COD_VERSAMENTO_ENTE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id.getIndice(),SingoloVersamento.model().INDICE.getFieldType())
		};
		Long id_singoloVersamento = null;
		try{
			id_singoloVersamento = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_singoloVersamento);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_singoloVersamento==null || id_singoloVersamento<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_singoloVersamento;
	}
}
