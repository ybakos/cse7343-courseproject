/*
    ProcessState.java
    @author Yong Joseph Bakos
    
    An enumeration that provides semantics for different process states.
*/

package edu.smu.cse7343.bakos.os;

public enum ProcessState {
    NEW,
    READY,
    RUNNING,
    WAITING,
    TERMINATED;
}
