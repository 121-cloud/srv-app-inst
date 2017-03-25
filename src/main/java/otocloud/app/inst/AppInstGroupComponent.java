package otocloud.app.inst;


import java.util.ArrayList;
import java.util.List;


import otocloud.framework.core.OtoCloudComponentImpl;
import otocloud.framework.core.OtoCloudEventHandlerRegistry;

/**
 * TODO: 
 * @date 2016年11月15日
 * @author lijing
 */
public class AppInstGroupComponent extends OtoCloudComponentImpl {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "app-inst-group";
	}

	@Override
	public List<OtoCloudEventHandlerRegistry> registerEventHandlers() {
		
		List<OtoCloudEventHandlerRegistry> ret = new ArrayList<OtoCloudEventHandlerRegistry>();
		
		AppInstGroupListGetHandler appInstGroupListGetHandler = new AppInstGroupListGetHandler(this);
		ret.add(appInstGroupListGetHandler);		
		
		return ret;
	}

}
