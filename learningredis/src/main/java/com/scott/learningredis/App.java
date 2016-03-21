package com.scott.learningredis;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		keyValue();
		increment();
		ttlNExpire();
		set();
	}

	private static void keyValue() {
		Jedis jedis = new Jedis("localhost");
		jedis.set("key", "value");
		System.out.println(jedis.get("key"));
	}

	private static void increment() {
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.get("counter"));
		jedis.incr("counter");
		System.out.println(jedis.get("counter"));
	}

	private static void ttlNExpire() throws InterruptedException {
		String cacheKey = "cachekey";
		Jedis jedis = new Jedis("localhost");
		jedis.set(cacheKey, "cached value");
		// setting the TTL in seconds
		jedis.expire(cacheKey, 5);
		// Getting the remaining ttl
		System.out.println("TTL:" + jedis.ttl(cacheKey));
		Thread.sleep(1000);
		System.out.println("TTL:" + jedis.ttl(cacheKey));
		// Getting the cache value
		System.out.println("Cached Value:" + jedis.get(cacheKey));

		// Wait for the TTL finishs
		Thread.sleep(5000);

		// trying to get the expired key
		System.out.println("Expired Key:" + jedis.get(cacheKey));
	}

	private static void set() {
		String cacheKey = "languages";
		Jedis jedis = new Jedis("localhost");
		jedis.sadd(cacheKey, "Java", "C#", "Python");// SADD

		// Getting all values in the set: SMEMBERS
		System.out.println("Languages: " + jedis.smembers(cacheKey));
		// Adding new values
		jedis.sadd(cacheKey, "Java", "Ruby");
		// Getting the values... it doesn't allow duplicates
		System.out.println("Languages: " + jedis.smembers(cacheKey));
	}
}
