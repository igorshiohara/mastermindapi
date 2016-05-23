package com.vanhack.az.store;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.vanhack.az.domain.Session;

/**
 * In memory implementation of {@link SessionStore}
 * 
 * @author Igor K. Shiohara
 */
public class InMemorySessionStore implements SessionStore {

	private Cache<Long, Session> sessions = CacheBuilder.newBuilder().concurrencyLevel(4).weakKeys().maximumSize(10000)
			.expireAfterWrite(10, TimeUnit.MINUTES).build();
	
	private AtomicInteger seq = new AtomicInteger();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session save(final String user) {
		long id = new Integer(seq.incrementAndGet()).longValue();
		Session session = new Session.Builder().withCreatedTime(LocalDateTime.now()).withId(id).withUsers(Lists.newArrayList(user)).build(); 
		sessions.put(id, session);
		return session;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Session> get(long id) {
		return Optional.of(sessions.getIfPresent(id));
	}

}
