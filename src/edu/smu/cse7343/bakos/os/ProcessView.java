/*
    ProcessView.java
    @author Yong Joseph Bakos
    
    A visual representation of a Process. A ProcessView will move every time
    it is `updated` by its corresponding Process. It also increases its size
    each time it is `updated` to visually indicate that it has been given
    more execution time by the scheduler, the idea being that higher priority
    processes are seen as larger ProcessViews.

    When the ProcessView touches the mouse, its Process should be notified
    that it should terminate.

    Uses Perlin Noise with a 50% chance of following the mouse, to simulate
    a behavior that follows the mouse, but not in a straight line.

    Some motion ideas influenced by Nature of Code by Dan Shiffman. (http://natureofcode.com/)

*/
package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class ProcessView {

    final float MAX_VELOCITY = 2;
    final float ACCELERATION_SCALE = 0.4f;
    final float NOISE_DELTA = 0.01f;
    final float GROWTH_DELTA = 0.01f;
    final float DEFAULT_X_OFFSET = 0.0f;
    final float PROBABILITY_OF_FOLLOWING_MOUSE = 0.5f;
    
    PApplet p;
    PVector location;
    PVector velocity;
    float xoff = DEFAULT_X_OFFSET;
    float size = 10;

    public ProcessView(PApplet p) {
        this(p, p.width / 2, p.height / 2);
    }

    public ProcessView(PApplet p, float x, float y) {
        this.p = p;
        location = new PVector(x, y);
        velocity = new PVector(0, 0);
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
        p.pushMatrix();
        p.translate(location.x, location.y);
        p.ellipse(0, 0, size, size);
        p.popMatrix();
    }

    private PVector acceleration() {
        if (p.random(0, 1) < PROBABILITY_OF_FOLLOWING_MOUSE) {
            PVector mouseVector = PVector.sub(new PVector(p.mouseX, p.mouseY), location);
            mouseVector.normalize(); // Fuck you, Processing!
            return mouseVector;      // This whole thing could be a one-liner.
        } else return PVector.fromAngle(p.noise(xoff) * p.TWO_PI);
    }

}