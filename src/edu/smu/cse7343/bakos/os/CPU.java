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
    public int[] registers = new int[NUMBER_OF_REGISTERS];
    public boolean isIdle = true;

    public CPU() {
    }

    public void tickTock() {
        if (isIdle) {
            programCounter = (programCounter + 1) % 5;
        } else {
            ++programCounter;
        }
    }

    public void exec(ProcessControlBlock pcb) {
        isIdle = false;
        programCounter = pcb.programCounter;
        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            registers[i] = pcb.registers[i];
        }
    }

}