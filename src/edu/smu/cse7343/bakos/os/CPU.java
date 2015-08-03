/*
    CPU.java
    @author Yong Joseph Bakos

    This class naively represents a CPU with a program counter, stack pointer and
    register data.

    Upon instantiation, a CPU will be assumed to execute the kernel idle process.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class CPU {

    public static final int NUMBER_OF_REGISTERS = 10;
    public int programCounter = 0;
    public int cycleCount = 0;
    public int baseRegister = 0;
    public int limitRegister = 0;
    public float[] registers = new float[NUMBER_OF_REGISTERS];
    public boolean isIdle = true;

    public Program currentProgram;

    public CPU() {
    }

    // Execute the next step of the current process
    public void tickTock() {
        ++cycleCount;
        if (isIdle) {
            programCounter = (programCounter + 1) % 5;
        } else if (currentProgram != null) {
            ++programCounter;
            currentProgram.step();
            registers[0] = Float.intBitsToFloat(currentProgram.color);
            registers[1] = currentProgram.size;
            registers[2] = currentProgram.xoff;
            registers[3] = currentProgram.velocity.x;
            registers[4] = currentProgram.velocity.y;
            registers[5] = currentProgram.location.x;
            registers[6] = currentProgram.location.y;
        }
    }

    // Restore CPU state from a PCB in preparation for next clock cycle.
    public void exec(ProcessControlBlock pcb) {
        isIdle = false;
        programCounter = pcb.programCounter;
        baseRegister = pcb.memoryBaseAddress;
        limitRegister = pcb.memoryLimitAddress;
        registers = pcb.registers.clone();
        currentProgram = new Program(registers[5], registers[6], registers[3], registers[4], registers[2], registers[1], Float.floatToRawIntBits(registers[0]));
    }

}