package helio.builder.siot.experimental.actions;

import com.google.gson.JsonObject;

/**
 * Implementations of {@link ConfigurableBlueprint} are blueprints able to configure themselves with a provided {@link JsonObject}.
 * @author Andrea Cimmino Arriaga
 *
 */
interface ConfigurableBlueprint {

	/**
	 * This method expects to receive a {@link JsonObject} with the necessary information for configuration.<p> Check the documentation of the implementations to know more about the different configurations required by implementations.
	 * @param configuration a suitable configuration
	 */
	public void configure(JsonObject configuration);
}
