package com.ochafik.lang.jnaerator.runtime;

import com.sun.jna.Pointer;

public interface StructureType {
	int size();
	void useMemory(Pointer p);
	Pointer getPointer();
	void read();
	void write();
}
