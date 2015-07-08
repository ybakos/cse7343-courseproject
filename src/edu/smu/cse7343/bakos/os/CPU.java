/*
    CPU.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class CPU extends Drawable {

    public static final int NUMBER_OF_REGISTERS = 10;

    int pc = 0;
    int[] registers = new int[NUMBER_OF_REGISTERS];

    public CPU(PApplet p) {
        this.p = p;
        this.width = 200;
        this.height = 200;
        this.x = width / 2 + 20;
        this.y = height / 2 + 20;
        // Registers are initially empty, so let's make them look empty.
        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            registers[i] = this.fillColor;
        }
    }

    public void tickTock() {
        ++pc;
    }

    public void draw() {
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

        for (int i = 0; i < NUMBER_OF_REGISTERS; ++i) {
            p.fill(registers[i]);
            p.rect(-width / 2 + 10 + (i * 20), -height / 2 + 60, (int)(0.1 * width), (int)(0.1 * height));
        }
        // Labels
        p.fill(labelColor);
        p.text("CPU", 0, height / 2 + 20);
        p.textAlign(p.LEFT);
        p.text("PC: " + pc, -width / 2 + 10, -height / 2 + 20);
        p.text("Registers", -width / 2 + 10, -height / 2 + 40);
        p.popMatrix();
        p.popStyle();
    }

    public void update() {
        tickTock();
    }

}