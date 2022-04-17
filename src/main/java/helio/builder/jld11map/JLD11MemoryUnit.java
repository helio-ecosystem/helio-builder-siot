package helio.builder.jld11map;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import helio.blueprints.AsyncDataProvider;
import helio.blueprints.DataProvider;
import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitType;
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.jld11map.runnable.SyncFreemarkerRunnable;

public class JLD11MemoryUnit implements TranslationUnit {

	public static Logger logger = LoggerFactory.getLogger(JLD11MemoryUnit.class);

	private Map<String, String> translations = new ConcurrentHashMap<>(); // Change the value to List<String> for
																			// historical values in async providers
	private ExecutorService service = null;
	private Template template;
	private UnitType type;
	private List<JLD11Triplet> triplets = new ArrayList<>();
	private int refreshTime;
	
	public JLD11MemoryUnit(Template template, Map<String, DataProvider> providers) {
		this.template = template;
		this.triplets = providers.entrySet().parallelStream()
				.map(entry -> new JLD11Triplet(entry.getKey(), entry.getValue())).collect(Collectors.toList());

		int threads = (int) this.triplets.parallelStream()
				.filter(triplet -> !(triplet.getProvider() instanceof AsyncDataProvider)).count();
		this.service = Executors.newFixedThreadPool(threads + 1);

		loadRunnables(false);

		type = UnitType.Sync;
	}

	private void loadRunnables(boolean onlySync) {
		if (!onlySync) {
			this.triplets.parallelStream().filter(elem -> (elem.getProvider() instanceof AsyncDataProvider))
					.forEach(elem -> elem.setFuture(service.submit(elem.getRunnable())));
		} else {
			this.triplets.parallelStream().filter(elem -> !(elem.getProvider() instanceof AsyncDataProvider))
					.forEach(elem -> elem.setFuture(service.submit(elem.getRunnable())));
		}
	}

	@Override
	public List<String> getDataTranslated() throws TranslationUnitExecutionException {
		List<String> templates = new ArrayList<>();
		try {
			if(translations.containsKey(CODE_EXCEPTION)) {
				StringBuilder error = new StringBuilder();
				error.append(translations.get(CODE_EXCEPTION));
				throw new TranslationUnitExecutionException(error.toString());
			}
			templates.add(solveTemplate());
		} catch (Exception e) {
			throw new TranslationUnitExecutionException(e.toString());
		}
		return templates;
	}

	private String solveTemplate()
			throws IncompatibleMappingException, IncorrectMappingException, TranslationUnitExecutionException {
		Map<String, Object> model = new HashMap<>();
		try (Writer out = new StringWriter()) {
			if (!translations.isEmpty()) {
				model.putAll(translations);
				translations.clear();

				this.template.process(model, out);
				return out.toString();
			} else {
				throw new TranslationUnitExecutionException("No data for inserting in the template");
			}
		} catch (IOException e) {
			throw new IncompatibleMappingException(e.toString());
		} catch (TemplateException e) {
			throw new IncorrectMappingException(e.toString());
		}
	}

	@Override
	public void configure(JsonObject configuration) {
		// empty

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
		return template.getName();
	}

	private static final String CODE_EXCEPTION = "translation_exception_internal_error-AAABBB";

	@Override
	public Runnable getTask() throws TranslationUnitExecutionException {
		loadRunnables(true);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				
					for (int index = 0; index < triplets.size(); index++) {
						JLD11Triplet triplet = triplets.get(index);
						boolean sync = !(triplet.getProvider() instanceof AsyncDataProvider);
						try {
						if (sync) {
							SyncFreemarkerRunnable runable = new SyncFreemarkerRunnable(triplet.getProvider());
							String result = runable.run();
							translations.put(triplet.getVariable(), result);
						} else {
							List<String> results = new ArrayList<>();
							results.addAll(triplet.getRunnable().getQueue());
							triplet.getRunnable().getQueue().clear();
							if (!results.isEmpty()) {
								translations.put(triplet.getVariable(), results.get(results.size() - 1));
							} else {
								translations.put(triplet.getVariable(), "");

							}
						}
						} catch (Exception e) {
							translations.put(CODE_EXCEPTION, e.toString());
							
						}
					}
				
			}

		};
		try {
			service.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new TranslationUnitExecutionException(e.toString());
		}
		return runnable;
	}

	@Override
	public void flushDataTranslated() {
		translations.clear();
	}

	@Override
	public int getScheduledTime() {
		return refreshTime;
	}

	@Override
	public void setScheduledTime(int scheduledTime) {
		this.refreshTime= scheduledTime;
		this.setUnitType(UnitType.Scheduled);
		
	}

}
