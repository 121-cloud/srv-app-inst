package otocloud.app.inst.control;


import java.util.ArrayList;
import java.util.List;


import otocloud.framework.core.OtoCloudComponentImpl;
import otocloud.framework.core.OtoCloudEventHandlerRegistry;

/**
 * TODO: 
 * @date 2016年11月15日
 * @author lijing
 */
public class AppInstControlComponent extends OtoCloudComponentImpl {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "app-inst-control";
	}

	@Override
	public List<OtoCloudEventHandlerRegistry> registerEventHandlers() {
		
		List<OtoCloudEventHandlerRegistry> ret = new ArrayList<OtoCloudEventHandlerRegistry>();
		
		AppInstStopHandler appInstGroupListGetHandler = new AppInstStopHandler(this);
		ret.add(appInstGroupListGetHandler);		
		
		AppInstStartupHandler appInstStartupHandler = new AppInstStartupHandler(this);
		ret.add(appInstStartupHandler);		
		
		return ret;
	}

}
