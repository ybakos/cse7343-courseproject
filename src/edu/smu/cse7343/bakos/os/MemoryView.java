/*
    MemoryView.java
    @author Yong Joseph Bakos

    This class encapsulates a visual representation of a computer's memory.
    Locations in memory for processes are indicated with color, that matches the
    color of the process.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class MemoryView {

    private PApplet p;
    private Memory memory;
    int x;
    int y;
    int width;
    final int HEIGHT = 64;
    final int STROKE_COLOR = 150;
    final int FILL_COLOR = 20;
    final int INTERNAL_LABEL_COLOR = 200;
    final int MAIN_LABEL_COLOR = 50;

    public MemoryView(Memory memory, int x, int y, PApplet p) {
        this.memory = memory;
        width = memory.totalSize();
        this.x = x;
        this.y = y;
        this.p = p;
    }

    public void draw() {
        p.pushStyle();
        p.pushMatrix();
        p.translate(x, y);
        // Body
        p.stroke(STROKE_COLOR);
        p.fill(FILL_COLOR);
        p.rectMode(p.CORNER);
        p.rect(0, 0, width, HEIGHT);
        // Registers
        for (int i = 0; i < width; ++i) {
            if (memory.registers[i] != 0) {
            //    System.out.println("address " + i + ": " + memory.registers[i]);
                p.stroke(Float.floatToRawIntBits(memory.registers[i]));
                p.line(i + 1, 0 + 1, i + 1, HEIGHT - 1);            
            }
        }
        // Labels
        p.fill(MAIN_LABEL_COLOR);
        p.textSize(32);
        p.textAlign(p.CENTER);
        p.text("Memory", 100, -20);
        p.textSize(18);
        p.textAlign(p.LEFT);
        p.text("Each vertical column of pixels represents one 64-bit register. 1 pixel is 1 bit.", 200, -20);
        p.textSize(18);
        p.text("Registers", -90, HEIGHT / 2 + 8);
        p.fill(STROKE_COLOR);
        p.textSize(10);
        p.text("Logical Addresses", -100, HEIGHT + 9);
        for (int i = 0; i < width; i += 100) {
            p.text(256000 + i, i, HEIGHT + 10);
        }
        p.popMatrix();
        p.popStyle();
    }

}