/*
    MemoryView.java
    @author Yong Joseph Bakos

    This class encapsulates a visual representation of a computer's memory.
    TODO
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

        // Labels
        p.fill(STROKE_COLOR);
        p.textSize(10);
        p.text("Virtual Addresses", -53, HEIGHT + 9);
        for (int i = 0; i < width; i += 100) {
            p.text(i, i, HEIGHT + 10);
        }
        p.fill(MAIN_LABEL_COLOR);
        p.textSize(32);
        p.textAlign(p.CENTER);
        p.text("Memory", 100, -20);
        p.textSize(18);
        p.textAlign(p.LEFT);
        p.text("Each vertical column of pixels represents one 64-bit register. 1 pixel is 1 bit.", 200, -20);
        
        p.textSize(18);
        p.text("Registers", -90, HEIGHT / 2 + 8);
        // TODO draw register labels
        p.popMatrix();
        p.popStyle();
    }

}