package helio.builder.jld11map.runnable;

import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helio.blueprints.DataProvider;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class SyncFreemarkerRunnable {

	private DataProvider provider;
	public static Logger logger = LoggerFactory.getLogger(SyncFreemarkerRunnable.class);

	public SyncFreemarkerRunnable(DataProvider provider) {
		this.provider = provider;

	}

	public String run() throws TranslationUnitExecutionException {
		try (Writer result = new StringWriter()) {
			Flowable<String> source = Flowable.create(provider, BackpressureStrategy.BUFFER);
			source.blockingForEach(data -> {
				result.write(data);// change this to add for having historical values
			});
			return result.toString();
		} catch (Exception e1) {
			throw new TranslationUnitExecutionException(e1.toString());
		}
	}

}
