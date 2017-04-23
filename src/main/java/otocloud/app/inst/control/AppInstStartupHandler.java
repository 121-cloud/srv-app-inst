package otocloud.app.inst.control;

import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import otocloud.app.inst.dao.AppInstDAO;
import otocloud.common.ActionURI;
import otocloud.framework.common.IgnoreAuthVerify;
import otocloud.framework.core.CommandMessage;
import otocloud.framework.core.HandlerDescriptor;
import otocloud.framework.core.OtoCloudComponentImpl;
import otocloud.framework.core.OtoCloudEventHandlerImpl;


/**
 * TODO: 应用模块查询
 * @date 2016年11月15日
 * @author lijing
 */
@IgnoreAuthVerify
public class AppInstStartupHandler extends OtoCloudEventHandlerImpl<JsonObject> {
	
	public static final String ADDRESS = "startup";

	public AppInstStartupHandler(OtoCloudComponentImpl componentImpl) {
		super(componentImpl);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HandlerDescriptor getHanlderDesc() {		
		
		HandlerDescriptor handlerDescriptor = super.getHanlderDesc();
		
		//参数
/*		List<ApiParameterDescriptor> paramsDesc = new ArrayList<ApiParameterDescriptor>();
		paramsDesc.add(new ApiParameterDescriptor("targetacc",""));		
		paramsDesc.add(new ApiParameterDescriptor("soid",""));		
		handlerDescriptor.setParamsDesc(paramsDesc);	*/
		
		ActionURI uri = new ActionURI(ADDRESS, HttpMethod.POST);
		handlerDescriptor.setRestApiURI(uri);
		
		return handlerDescriptor;		

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEventAddress() {
		return ADDRESS;
	}
	

	/**
	 * {
	 *    is_platform:
	 *    acct_id;
	 *    app_code:
	 *    
	 * 	  acct_app_id:
	 * 	  app_inst_group:
	 * }
	 */
	@Override
	public void handle(CommandMessage<JsonObject> msg) {
		
		JsonObject content = msg.body().getJsonObject("content");
		
		Long acct_id = content.getLong("acct_id");
		String app_code = content.getString("app_code");		
		
		Long acct_app_id = content.getLong("acct_app_id");
		String app_inst_group = content.getString("app_inst_group", "");
		Boolean is_platform = content.getBoolean("is_platform", true);

		
		Future<UpdateResult> getFuture = Future.future();
        
		AppInstDAO appInstDAO = new AppInstDAO(this.componentImpl.getSysDatasource());
		appInstDAO.startupApp(acct_app_id, app_inst_group, getFuture);
        
        getFuture.setHandler(ret -> {
            if (ret.succeeded()) {
            	
            	if(!is_platform){		
            	
					//启动账户应用实例											
					String srvAddress = app_code + "." + app_inst_group + ".platform.appinst_status.control"; 
						
					JsonObject instLoadMsg = new JsonObject()
						.put("account", acct_id.toString())
						.put("status", "startup");
	
					componentImpl.getEventBus().publish(srvAddress,
							instLoadMsg);	
            	}
            	
            	msg.reply(ret.result().getUpdated());
            	
            } else {
            	Throwable errThrowable = ret.cause();
    			String errMsgString = errThrowable.getMessage();
    			this.componentImpl.getLogger().error(errMsgString, errThrowable);
    			msg.fail(100, errMsgString);
            }
        });
	}
	
}
