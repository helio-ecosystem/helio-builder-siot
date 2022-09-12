package helio.builder.siot.utils;

import com.google.gson.JsonObject;

import helio.blueprints.AsyncDataProvider;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableEmitter;

public class DummyProvider implements AsyncDataProvider{

	private int time = 3000;
	
	@Override
	public void configure(JsonObject configuration) {
		if(configuration.has("time"))
			time = configuration.get("time").getAsInt();

	}

	@Override
	public void subscribe(@NonNull FlowableEmitter<@NonNull String> emitter) throws Throwable {
		while(true) {
			try {
			String values = String.valueOf(Math.random());
			System.out.println("(Event generated: "+values+" )");
			emitter.onNext(values);
			Thread.sleep(time);
			}catch(Exception e) {
				emitter.onError(e);
			}
		}


	}

}
