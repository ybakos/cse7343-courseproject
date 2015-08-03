/*
    ProcessControlBlock.java
    @author Yong Joseph Bakos

    This class represents an operating system PCB.
    Program state (color, location, velocity, etc) are stored in the registers, to simulate
    runtime data registers from the CPU. Memory base and limit correspond to logical addresses
    in the Memory class.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class ProcessControlBlock {

    private int parentPid; // Ignoring this in simulator,
    private int userId;    // just providing for consistency
    private int groupId;   // and understanding.

    private int priority;    // TODO
    private int lastCycle;   // The last time this pcb was executed
    private int waitEventId; // Resource of id waiting for when in WAIT
    private int cycleCount;  // Amount of CPU time accumulated
    public int memoryBaseAddress;
    public int memoryLimitAddress;

    public int pid;
    public ProcessState state;
    public int programCounter;
    public float[] registers;

    public ProcessControlBlock(int pid, int base, int limit, Program program) {
        this.pid = pid;
        memoryBaseAddress = base;
        memoryLimitAddress = limit;
        state = ProcessState.NEW;
        programCounter = 0;
        registers = new float[CPU.NUMBER_OF_REGISTERS];
        registers[0] = Float.intBitsToFloat(program.color);
        registers[1] = program.size;
        registers[2] = program.xoff;
        registers[3] = program.velocity.x;
        registers[4] = program.velocity.y;
        registers[5] = program.location.x;
        registers[6] = program.location.y;
    }

    // Base address - limit. A convenience method.
    public int size() {
        return memoryLimitAddress - memoryBaseAddress;
    }

}