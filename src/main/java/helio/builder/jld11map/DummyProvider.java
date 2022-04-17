package helio.builder.jld11map;

import com.google.gson.JsonObject;

import helio.blueprints.AsyncDataProvider;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableEmitter;

public class DummyProvider implements AsyncDataProvider{

	@Override
	public void configure(JsonObject configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribe(@NonNull FlowableEmitter<@NonNull String> emitter) throws Throwable {
		while(true) {
			try {
			String values = String.valueOf(Math.random());
			System.out.println("(Event generated: "+values+" )");
			emitter.onNext(values);
			Thread.sleep(3000);
			}catch(Exception e) {
				emitter.onError(e);
			}
		}


	}

}
