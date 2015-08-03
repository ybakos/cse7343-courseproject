/*
    Memory.java
    @author Yong Joseph Bakos

    This class represent a contiguous logical memory address space. Its size is determined
    by the simulator upon instantiation.
*/

package edu.smu.cse7343.bakos.os;

public class Memory {

    float[] registers;

    public Memory(int size) {
        registers = new float[size];
    }

    public int totalSize() {
        return registers.length;
    }

    public void write(int address, float value) {
        if (address >= registers.length) {
            System.out.println("TRAP: Address out of bounds."); // TODO
        } else {
            registers[address] = value;
        }
    }

}