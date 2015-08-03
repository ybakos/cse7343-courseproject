/*
    MemoryAlgorithm.java
    @author Yong Joseph Bakos
    
    An enumeration that provides semantics for different memory allocation algorithms.
    TODO this should be totally deprecated in lieue of the Strategy pattern.
*/

package edu.smu.cse7343.bakos.os;

public enum MemoryAllocationAlgorithm {
    FIRST_FIT,
    BEST_FIT,
    WORST_FIT;
}
