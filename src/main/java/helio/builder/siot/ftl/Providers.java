package helio.builder.siot.ftl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import helio.blueprints.DataProvider;
import helio.blueprints.components.Components;
import helio.builder.siot.rx.RxThread;

public class Providers implements TemplateMethodModelEx {

	public static Map<String, RxThread> tasks = new ConcurrentHashMap<>();
	public static ExecutorService service = Executors.newCachedThreadPool();
	
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		try {
			String provider = String.valueOf(arguments.get(0));
			JsonObject config = new JsonObject();
			if(arguments.size()>1)
				config = (new Gson()).fromJson(String.valueOf(arguments.get(1)), JsonObject.class);
			
			int type = retrieveType(config);
			// DONE: add onlyLast and onlyFist
			String id = String.valueOf(provider.hashCode())+String.valueOf(config.hashCode())+String.valueOf(type);
			
			
			RxThread task = tasks.get(id);
			
			if(task==null) {
				DataProvider providerObj = Components.newProviderInstance(provider);
				providerObj.configure(config);
				task = new RxThread(providerObj);
				service.submit(task);
				if(task.isAsynchronous())
					tasks.put(id, task);
			}
			List<String> aux = task.getReadings();
			List<String> data = copyResults(aux, type);
			aux.clear();
			if(type==3)
				return data;
			else if(type==1 || type==2)
				return data.get(0); // previously the last element was allocated in position 0
			else 
				throw new Exception("No data retrieved from source");
			
			
						
		}catch(Exception e) {
			throw new TemplateModelException(e.toString());
		}
	}
	
	private List<String> copyResults(List<String> aux, int type){
		List<String> list = new ArrayList<>();
		if(type ==1 ) {
			list.add(aux.get(0));
		}else if(type == 2) {
			list.add(aux.get(aux.size()-1));
		}else {
			list.addAll(aux);
		}
		return list;
	}

	private int retrieveType(JsonObject config) {
		if(config.has("asQueue"))
			return 3;
		else if(config.has("onlyLast"))
			return 2;
		else if(config.has("onlyFirst"))
			return 1;// onlyFirst
		else
			return 1;
	}

}
