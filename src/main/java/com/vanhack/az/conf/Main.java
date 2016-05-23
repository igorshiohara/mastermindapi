package com.vanhack.az.conf;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Main class of the project. This starts up the jersey rest
 * endpoints by creating the Guice injector and modules.
 *  
 * @author geiser
 *
 */
public class Main extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		List<Module> modules = Lists.newArrayList();
		
		modules.add(new RestModule());
		modules.add(new StoreModule());
		
		return Guice.createInjector(modules);
	}

}
