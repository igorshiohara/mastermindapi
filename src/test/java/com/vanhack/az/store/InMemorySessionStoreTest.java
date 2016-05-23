package com.vanhack.az.store;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vanhack.az.domain.Session;


/**
 * 
 * @author Igor K. Shiohara
 *
 */
public class InMemorySessionStoreTest {

	@Test
	public void testSaveAndGet() {

		InMemorySessionStore store = new InMemorySessionStore();

		Session session = store.save("SomeUser");
		
		Session loadedSession = store.get(session.getId()).get();
		
		assertEquals(session, loadedSession);

	}

}
