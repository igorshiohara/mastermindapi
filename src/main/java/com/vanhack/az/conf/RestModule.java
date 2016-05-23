package com.vanhack.az.conf;

import com.google.common.collect.Maps;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Creates the jersey rest context with initial configuration.
 * 
 * @author geiser
 *
 */
public class RestModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {

		ResourceConfig rc = new PackagesResourceConfig("com.vanhack.az.rest");
		
		for (Class<?> resource : rc.getClasses()) {
			bind(resource);
		}

		serve("/*").with(GuiceContainer.class, Maps.newHashMap());
	}

}
