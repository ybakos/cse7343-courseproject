/*
    ProcessView.java
    @author Yong Joseph Bakos
    
    A visual representation of a Process. A ProcessView will move every time
    it its corresponding process executes its next 'instruction'. TODO: It also increases
    its size each time it is `update`d to visually indicate that it has been given
    more execution time by the scheduler, the idea being that higher priority
    processes are seen as larger ProcessViews.

    TODO: When the ProcessView touches the mouse, its Process should be notified
    that it should terminate.

    Uses Perlin Noise with a 50% chance of following the mouse, to simulate
    a behavior that follows the mouse, but not in a straight line.

    Some motion ideas influenced by Nature of Code by Dan Shiffman. (http://natureofcode.com/)
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class ProcessView {

    private final float MAX_VELOCITY = 2;
    private final float ACCELERATION_SCALE = 0.4f;
    private final float NOISE_DELTA = 0.01f;
    private final float GROWTH_DELTA = 0.01f;
    private final float DEFAULT_X_OFFSET = 0.0f;
    private final float PROBABILITY_OF_FOLLOWING_MOUSE = 0.8f;
    
    private PApplet p;
    private PVector location;
    private PVector velocity;
    private float xoff = DEFAULT_X_OFFSET;
    public float size = 10;

    public int color;

    public ProcessView(PApplet p) {
        this(p, p.width / 2 + p.random(-50, 50), p.height / 2 + p.random(-50, 50));
    }

    public ProcessView(PApplet p, float x, float y) {
        this.p = p;
        location = new PVector(x, y);
        velocity = new PVector(0, 0);
        color = p.color(p.random(150, 255), p.random(150, 255), p.random(150, 255), 220);
        size = p.random(10, 50);
        xoff = p.random(-1, 1);
    }

    public void update() {
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
        size += GROWTH_DELTA;
    }

    public void draw() {
        p.pushStyle();
        p.pushMatrix();
        p.translate(location.x, location.y);
        p.fill(color);
        p.ellipse(0, 0, size, size);
        p.popMatrix();
        p.popStyle();
    }

    public void dim() {
        color = p.color(p.red(color), p.green(color), p.blue(color), 100);
    }

    public void light() {
        color = p.color(p.red(color), p.green(color), p.blue(color), 220);   
    }

    public boolean isClicked(int x, int y) {
        return p.dist(location.x, location.y, x, y) < (size / 2);
    }

    private PVector acceleration() {
        if (p.random(0, 1) < PROBABILITY_OF_FOLLOWING_MOUSE) {
            PVector mouseVector = PVector.sub(new PVector(p.mouseX, p.mouseY), location);
            mouseVector.normalize(); // Fuck you, Processing!
            return mouseVector;      // This whole thing could be a one-liner.
        } else return PVector.fromAngle(p.noise(xoff) * p.TWO_PI);
    }

}