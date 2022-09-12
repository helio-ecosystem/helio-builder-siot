package helio.builder.siot.rx;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import helio.blueprints.AsyncDataProvider;
import helio.blueprints.DataProvider;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class RxThread extends Thread {

	
	private Flowable<String> source;
	private List<String> readings = new CopyOnWriteArrayList<>();
	private Writer exception = new StringWriter();
	private boolean isAsynchronous = false;
	
	public CountDownLatch latch;
	
	public RxThread(DataProvider provider) {
		latch = new CountDownLatch(1);
		isAsynchronous = provider instanceof AsyncDataProvider;
		source = Flowable.create(provider, BackpressureStrategy.BUFFER);
	}
	
	public boolean isAsynchronous() {
		return isAsynchronous;
	}
	
	@Override
	public void run() {
		super.run();
		source.subscribe(data -> {

			if(latch.getCount()==0)
				latch = new CountDownLatch(1);
			readings.add(data);	
			latch.countDown();
		}, e -> {
			exception.write(e.toString());
			latch.countDown();
		});
	}

	public List<String> getReadings() throws TranslationUnitExecutionException {
		awaitFlowable();
		checkException();
		return readings;
	}

	public void setReadings(List<String> readings) {
		this.readings = readings;
	}

	private void awaitFlowable() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void checkException() throws TranslationUnitExecutionException {
		String msg = exception.toString();
		if(!msg.isEmpty()) {
			try {
				exception.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new TranslationUnitExecutionException(msg);
		}
	}

}
