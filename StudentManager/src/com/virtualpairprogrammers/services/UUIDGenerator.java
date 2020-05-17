package com.virtualpairprogrammers.services;

import java.util.UUID;

/** 
 * This is a deliberate attempt to generate a "Memory Leak" - it's very unsubtle, in real
 * life leaks are much harder to spot.
 * @author cybrandian
 */
public class UUIDGenerator 
{
	// VERY BAD METHOD IMPLEMENTATION!
	public static String newUUID() {
		return UUID.randomUUID().toString();
	}
}
