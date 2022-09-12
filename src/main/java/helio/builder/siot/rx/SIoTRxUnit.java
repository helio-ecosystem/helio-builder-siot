package helio.builder.siot.rx;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.google.gson.JsonObject;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitType;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;

public class SIoTRxUnit implements TranslationUnit {

	private Template template;
	private Integer refreshTime;
	private UnitType type;
	private String id;
	
	public SIoTRxUnit(Template template) {
		this.template = template;
		this.id = UUID.randomUUID().toString();
		this.type = UnitType.Sync;
	}
	
	
	@Override
	public UnitType getUnitType() {
		return type;
	}

	@Override
	public void setUnitType(UnitType type) {
		this.type = type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Callable<String> getTask(Map<String, Object> arguments) throws TranslationUnitExecutionException {
		
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				try (Writer out = new StringWriter()) {
					Map<String, Object> model = new HashMap<>();
					if(arguments!=null)
						model = arguments;
					template.process(model, out);
					return out.toString();
				} catch (IOException e) {
					throw new IncompatibleMappingException(e.toString());
				} catch (TemplateException e) {
					throw new IncorrectMappingException(e.toString());
				}
			}
		};
	}



	@Override
	public int getScheduledTime() {
		return refreshTime;
	}

	@Override
	public void setScheduledTime(int scheduledTime) {
		this.refreshTime = scheduledTime;
	}
	
	@Override
	public void configure(JsonObject configuration) {
		// TODO Auto-generated method stub
		
	}



	



}
