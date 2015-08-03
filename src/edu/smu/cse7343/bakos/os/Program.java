/*
    Program.java
    @author Yong Joseph Bakos

    This class represents a program stored on disk, that can be "loaded" into memory and
    then executed by a CPU. When the CPU "runs" a program instance, it appears on the screen
    as a moving circle.

    The instance members of Program objects correspond to CPU register data, and register data
    within PCBs. The color member corresponds with the color of the circle, the color of data
    in the CPU registers, and the color of its allocated memory.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class Program {

    public static PApplet p; // For accessing Processing functions within a Program

    private final float DEFAULT_MEMORY_FOOTPRINT = 36;
    private final float MAX_VELOCITY = 2;
    private final float ACCELERATION_SCALE = 0.4f;
    private final float NOISE_DELTA = 0.01f;
    private final float GROWTH_DELTA = 0.01f;
    private final float DEFAULT_X_OFFSET = 0.0f;
    private final float PROBABILITY_OF_FOLLOWING_MOUSE = 0.8f;

    public PVector location;
    public PVector velocity;
    public float xoff;
    public float size;

    public int color;

    public Program() {
        location = new PVector(p.width / 2 + p.random(-50, 50), p.height / 2 + p.random(-50, 50));
        velocity = new PVector(0, 0);
        size = p.random(10, 50);
        xoff = p.random(-1, 1);
        color = p.color(p.random(150, 255), p.random(150, 255), p.random(150, 255), 220);
        size = p.random(DEFAULT_MEMORY_FOOTPRINT, DEFAULT_MEMORY_FOOTPRINT * 4);
    }

    public Program(float x, float y, float vX, float vY, float xoff, float size, int color) {
        location = new PVector(x, y);
        velocity = new PVector(vX, vY);
        this.xoff = xoff;
        this.size = size;
        this.color = color;
    }

    public void step() {
        PVector acceleration = acceleration();
        acceleration.mult(ACCELERATION_SCALE);
        velocity.add(acceleration);
        velocity.limit(MAX_VELOCITY);
        location.add(velocity);
        if (location.x > p.width) location.x = 0;
        if (location.x < 0) location.x = p.width;
        if (location.y > p.height) location.y = 0;
        if (location.y < 0) location.y = p.height;
        xoff += NOISE_DELTA;
        //size += GROWTH_DELTA;
    }

    private PVector acceleration() {
        if (p.random(0, 1) < PROBABILITY_OF_FOLLOWING_MOUSE) {
            PVector mouseVector = PVector.sub(new PVector(p.mouseX, p.mouseY), location);
            mouseVector.normalize(); // Fuck you, Processing!
            return mouseVector;      // This whole thing could be a one-liner.
        } else return PVector.fromAngle(p.noise(xoff) * p.TWO_PI);
    }

    public static void initialize(PApplet p) {
        if (Program.p == null) Program.p = p;
    }

}