package helio.builder.jld11map.runnable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helio.blueprints.DataProvider;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class FreemarkerHelioRunnable implements Runnable {

	private List<String> queue = new CopyOnWriteArrayList<>();
	private List<String> exceptions = new CopyOnWriteArrayList<>();

	private DataProvider provider;
	public static Logger logger = LoggerFactory.getLogger(FreemarkerHelioRunnable.class);

	public FreemarkerHelioRunnable(DataProvider provider) {
		this.provider = provider;

	}

	public List<String> getQueue() throws TranslationUnitExecutionException{
		if(!exceptions.isEmpty()) {
			String error = exceptions.get(0);
			exceptions.clear();
			throw new TranslationUnitExecutionException(error);
		}
		return queue;
	}

//	@Override
//	public void run() {
//		Flowable<String> source = Flowable.create(provider, BackpressureStrategy.BUFFER);
//		source.subscribe(data -> {
//				queue.add(data);// change this to add for having historical values
//		},e -> {throw new TranslationUnitExecutionException(e.toString());});
//	}

	@Override
	public void run() {
		Flowable<String> source = Flowable.create(provider, BackpressureStrategy.BUFFER);
		source.subscribe(data -> {
				queue.add(data);// change this to add for having historical values
		},e -> {exceptions.add(e.toString());});
		
	}



}
