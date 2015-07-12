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
    int programCounter = 0;
    int[] registers = new int[NUMBER_OF_REGISTERS];
    boolean isIdle = true;

    int x;
    int y;
    int width;
    int height;
    int strokeColor = 150;
    int fillColor = 20;
    int internalLabelColor = 200;
    int mainLabelColor = 50;

    public CPU(int x, int y) {
        this.width = 200;
        this.height = 200;
        this.x = x;
        this.y = y;
        // Registers are initially empty, so let's make them look empty.
        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            registers[i] = 0;
        }
    }

    public void tickTock() {
        if (isIdle) {
            programCounter = (programCounter + 1) % 5;
        } else {
            ++programCounter;
        }
    }

    public void draw(PApplet p) {
        p.pushStyle();
        p.stroke(strokeColor);
        p.fill(fillColor);
        p.rectMode(p.CENTER);
        p.pushMatrix();
        p.translate(x, y);
        // Body
        p.rect(0, 0, width, height);
        p.textAlign(p.CENTER);
        // Registers
        p.rectMode(p.CORNER);
        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            p.fill(registers[i]);
            p.rect(-width / 2 + 70 + (i * 10), -height / 2 + 30, 10, 15);
        }
        // Labels
        p.fill(mainLabelColor);
        p.textSize(32);
        p.textAlign(p.CENTER);
        p.text("CPU", 0, 20);
        p.fill(internalLabelColor);
        p.textSize(12);
        p.textAlign(p.LEFT);
        p.text("PC: " + programCounter, -width / 2 + 10, -height / 2 + 20);
        if (isIdle) p.text("(kernel idle process)", -width / 2 + 50, -height / 2 + 20);
        p.text("Registers", -width / 2 + 10, -height / 2 + 40);
        p.popMatrix();
        p.popStyle();
    }

    public void update() {
        tickTock();
    }

    public void exec(ProcessControlBlock pcb) {
        isIdle = false;
        programCounter = pcb.programCounter;
        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            registers[i] = pcb.registers[i];
        }
    }

}