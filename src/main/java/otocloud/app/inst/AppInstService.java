package otocloud.app.inst;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import otocloud.app.inst.control.AppInstControlComponent;
import otocloud.framework.core.OtoCloudComponent;
import otocloud.framework.core.OtoCloudServiceForVerticleImpl;
import otocloud.persistence.dao.MongoDataSource;


/**
 * TODO: 
 * @date 2016年11月26日
 * @author lijing@yonyou.com
 */
public class AppInstService extends OtoCloudServiceForVerticleImpl {	
	
    private MongoDataSource portalMongoDataSource;
    
	public MongoDataSource getPortalMongoDataSource() {
		return portalMongoDataSource;
	}
	
	@Override
	public void afterInit(Future<Void> initFuture) {		
        //如果有mongo_client配置,放入上下文当中.
        if (this.srvCfg.containsKey("mongo_client")) {
            JsonObject mongoClientCfg = this.srvCfg.getJsonObject("mongo_client");
	        if(mongoClientCfg != null){
	        	portalMongoDataSource = new MongoDataSource();
	        	portalMongoDataSource.init(vertxInstance, mongoClientCfg);				
	        }
        }
        
        super.afterInit(initFuture);        
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OtoCloudComponent> createServiceComponents() {
		
		List<OtoCloudComponent> components = new ArrayList<OtoCloudComponent>();
		
		AppInstGroupComponent appInstGroupComponent = new AppInstGroupComponent();
		components.add(appInstGroupComponent);
		
		AppInstControlComponent appInstControlComponent = new AppInstControlComponent();
		components.add(appInstControlComponent);
				
		return components;
	}  
    
	@Override
	public String getServiceName() {
		return "otocloud-app-inst";
	}
}
