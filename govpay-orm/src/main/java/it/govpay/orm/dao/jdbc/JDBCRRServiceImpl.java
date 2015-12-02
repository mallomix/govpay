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

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import it.govpay.orm.IdRr;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;

import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;

import it.govpay.orm.RR;
import it.govpay.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCRRServiceImpl
 *
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCRRServiceImpl extends JDBCRRServiceSearchImpl
	implements IJDBCServiceCRUDWithId<RR, IdRr, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, RR rr, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		// default behaviour (id-mapping)
		if(idMappingResolutionBehaviour==null){
			idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
		}
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _rt
		Long id_rt = null;
		it.govpay.orm.IdRt idLogic_rt = null;
		idLogic_rt = rr.getIdRT();
		if(idLogic_rt!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_rt = ((JDBCRTServiceSearch)(this.getServiceManager().getRTServiceSearch())).findTableId(idLogic_rt, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_rt = idLogic_rt.getId();
				if(id_rt==null || id_rt<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _tracciatoXML
		Long id_tracciatoXML = null;
		it.govpay.orm.IdTracciato idLogic_tracciatoXML = null;
		idLogic_tracciatoXML = rr.getIdTracciatoXML();
		if(idLogic_tracciatoXML!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_tracciatoXML = ((JDBCTracciatoXMLServiceSearch)(this.getServiceManager().getTracciatoXMLServiceSearch())).findTableId(idLogic_tracciatoXML, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_tracciatoXML = idLogic_tracciatoXML.getId();
				if(id_tracciatoXML==null || id_tracciatoXML<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object rr
		sqlQueryObjectInsert.addInsertTable(this.getRRFieldConverter().toTable(RR.model()));
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().COD_MSG_REVOCA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().DATA_ORA_MSG_REVOCA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().IMPORTO_TOTALE_REVOCATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().STATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().DESCRIZIONE_STATO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getRRFieldConverter().toColumn(RR.model().DATA_ORA_CREAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField("id_rt","?");
		sqlQueryObjectInsert.addInsertField("id_tracciato_xml","?");

		// Insert rr
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getRRFetch().getKeyGeneratorObject(RR.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getCodMsgRevoca(),RR.model().COD_MSG_REVOCA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getDataOraMsgRevoca(),RR.model().DATA_ORA_MSG_REVOCA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getImportoTotaleRevocato(),RR.model().IMPORTO_TOTALE_REVOCATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getStato(),RR.model().STATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getDescrizioneStato(),RR.model().DESCRIZIONE_STATO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(rr.getDataOraCreazione(),RR.model().DATA_ORA_CREAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_rt,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_tracciatoXML,Long.class)
		);
		rr.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr oldId, RR rr, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdRR(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = rr.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: rr.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			rr.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}
		
		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, rr, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, RR rr, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		// default behaviour (id-mapping)
		if(idMappingResolutionBehaviour==null){
			idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
		}
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		
		boolean setIdMappingResolutionBehaviour = 
			(idMappingResolutionBehaviour==null) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour);
			

		// Object _rr_rt
		Long id_rr_rt = null;
		it.govpay.orm.IdRt idLogic_rr_rt = null;
		idLogic_rr_rt = rr.getIdRT();
		if(idLogic_rr_rt!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_rr_rt = ((JDBCRTServiceSearch)(this.getServiceManager().getRTServiceSearch())).findTableId(idLogic_rr_rt, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_rr_rt = idLogic_rr_rt.getId();
				if(id_rr_rt==null || id_rr_rt<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _rr_tracciatoXML
		Long id_rr_tracciatoXML = null;
		it.govpay.orm.IdTracciato idLogic_rr_tracciatoXML = null;
		idLogic_rr_tracciatoXML = rr.getIdTracciatoXML();
		if(idLogic_rr_tracciatoXML!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_rr_tracciatoXML = ((JDBCTracciatoXMLServiceSearch)(this.getServiceManager().getTracciatoXMLServiceSearch())).findTableId(idLogic_rr_tracciatoXML, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_rr_tracciatoXML = idLogic_rr_tracciatoXML.getId();
				if(id_rr_tracciatoXML==null || id_rr_tracciatoXML<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object rr
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getRRFieldConverter().toTable(RR.model()));
		boolean isUpdate_rr = true;
		java.util.List<JDBCObject> lstObjects_rr = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().COD_MSG_REVOCA,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getCodMsgRevoca(), RR.model().COD_MSG_REVOCA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().DATA_ORA_MSG_REVOCA,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getDataOraMsgRevoca(), RR.model().DATA_ORA_MSG_REVOCA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().IMPORTO_TOTALE_REVOCATO,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getImportoTotaleRevocato(), RR.model().IMPORTO_TOTALE_REVOCATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().STATO,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getStato(), RR.model().STATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().DESCRIZIONE_STATO,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getDescrizioneStato(), RR.model().DESCRIZIONE_STATO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getRRFieldConverter().toColumn(RR.model().DATA_ORA_CREAZIONE,false), "?");
		lstObjects_rr.add(new JDBCObject(rr.getDataOraCreazione(), RR.model().DATA_ORA_CREAZIONE.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_rt","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_tracciato_xml","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_rr.add(new JDBCObject(id_rr_rt, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_rr.add(new JDBCObject(id_rr_tracciatoXML, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_rr.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_rr) {
			// Update rr
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_rr.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRRFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRRFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getRRFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRRFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRRFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getRRFieldConverter().toTable(RR.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getRRFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr oldId, RR rr, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		// default behaviour (id-mapping)
		if(idMappingResolutionBehaviour==null){
			idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
		}
		
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, rr,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, rr,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, RR rr, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		// default behaviour (id-mapping)
		if(idMappingResolutionBehaviour==null){
			idMappingResolutionBehaviour = org.openspcoop2.generic_project.beans.IDMappingBehaviour.valueOf("USE_TABLE_ID");
		}
		
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, rr,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, rr,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, RR rr) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (rr.getId()!=null) && (rr.getId()>0) ){
			longId = rr.getId();
		}
		else{
			IdRr idRR = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,rr);
			longId = this.findIdRR(jdbcProperties,log,connection,sqlQueryObject,idRR,false);
			if(longId == null){
				return; // entry not exists
			}
		}		
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object rr
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getRRFieldConverter().toTable(RR.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete rr
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdRr idRR) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdRR(jdbcProperties, log, connection, sqlQueryObject, idRR, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getRRFieldConverter()));

	}

	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {

		java.util.List<Long> lst = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, new JDBCPaginatedExpression(expression));
		
		for(Long id : lst) {
			this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		}
		
		return new NonNegativeNumber(lst.size());
	
	}



	// -- DB
	
	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotImplementedException, Exception {
		this._delete(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
}
