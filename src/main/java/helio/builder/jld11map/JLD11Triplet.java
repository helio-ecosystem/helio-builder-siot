package helio.builder.jld11map;

import java.util.Objects;
import java.util.concurrent.Future;

import helio.blueprints.DataProvider;
import helio.builder.jld11map.runnable.FreemarkerHelioRunnable;

class JLD11Triplet {

	private Future<?> future;
	private FreemarkerHelioRunnable runnable;
	private String variable;
	private DataProvider provider;

	public JLD11Triplet(String variable, DataProvider provider) {
		super();
		this.runnable = new FreemarkerHelioRunnable(provider);
		this.variable = variable;
		this.provider = provider;
	}


	public Future<?> getFuture() {
		return future;
	}

	public void setFuture(Future<?> future) {
		this.future = future;
	}

	public FreemarkerHelioRunnable getRunnable() {
		return runnable;
	}

	public void setRunnable(FreemarkerHelioRunnable runnable) {
		this.runnable = runnable;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public DataProvider getProvider() {
		return provider;
	}

	public void setProvider(DataProvider provider) {
		this.provider = provider;
	}

	@Override
	public int hashCode() {
		return Objects.hash(variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		JLD11Triplet other = (JLD11Triplet) obj;
		return Objects.equals(variable, other.variable);
	}


	@Override
	public String toString() {
		return "JLD11Triplet [variable=" + variable + ", provider=" + provider + "]";
	}


}
