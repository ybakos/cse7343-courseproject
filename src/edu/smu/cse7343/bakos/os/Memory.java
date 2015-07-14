/*
    Memory.java
    @author Yong Joseph Bakos

    TODO
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

}