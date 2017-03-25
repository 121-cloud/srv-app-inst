/*
 * Copyright (C) 2015 121Cloud Project Group  All rights reserved.
 */
package otocloud.app.inst.dao;

import otocloud.persistence.dao.JdbcDataSource;
import otocloud.persistence.dao.OperatorDAO;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;


public class AppInstDAO extends OperatorDAO{
	
    public AppInstDAO(JdbcDataSource dataSource) {
        super(dataSource);
    }
	
    public void getAppInstGroupListByApp(Long appId, Future<ResultSet> future) {
        
	   final String sql = "SELECT * FROM app_inst_group WHERE app_id=?";
	   JsonArray params = new JsonArray();
	   params.add(appId);
	
	   this.queryWithParams(sql, params, future);
    	
    }
    
    
	
    public void stopApp(Long acct_app_id, Future<UpdateResult> future) {
        
	   final String sql = "update acct_app set status='U' WHERE id=?";
	   JsonArray params = new JsonArray();
	   params.add(acct_app_id);
	
	   this.updateWithParams(sql, params, future);
    	
    }
    
    public void startupApp(Long acct_app_id, String app_inst_group, Future<UpdateResult> future) {
       String groupString = "";
       if(app_inst_group != null && !app_inst_group.isEmpty()){
    	   groupString = app_inst_group;
       }
        
	   final String sql = "update acct_app set status='A',app_inst_group=? WHERE id=?";
	   JsonArray params = new JsonArray();
	   params.add(groupString);
	   params.add(acct_app_id);
	
	   this.updateWithParams(sql, params, future);
    	
    }
    

}
