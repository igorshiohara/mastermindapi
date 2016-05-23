package com.vanhack.az.conf;

import com.google.inject.AbstractModule;
import com.vanhack.az.store.GameStore;
import com.vanhack.az.store.InMemoryGameStore;
import com.vanhack.az.store.InMemorySessionStore;
import com.vanhack.az.store.SessionStore;

/**
 * Module related to data store.
 * 
 * @author geiser
 *
 */
public class StoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GameStore.class).to(InMemoryGameStore.class).asEagerSingleton();
		bind(SessionStore.class).to(InMemorySessionStore.class).asEagerSingleton();
	}

}
