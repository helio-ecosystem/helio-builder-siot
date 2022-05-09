package helio.builder.siot.experimental.actions;

/**
 * The {@link Action} implementations provide advanced functionalities to be performed after a data translation task.
 * @author Andrea Cimmino Arriaga
 */
public interface Action extends ConfigurableBlueprint{

	public String run(String values);
	
}
