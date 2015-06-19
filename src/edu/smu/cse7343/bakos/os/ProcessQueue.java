/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.

*/

package edu.smu.cse7343.bakos.os;

import java.util.*;

public abstract class ProcessQueue {
    
    private LinkedList<ProcessControlBlock> queue;

}