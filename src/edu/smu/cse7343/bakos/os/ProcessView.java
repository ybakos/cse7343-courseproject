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

    
    private PApplet p;
    private ProcessControlBlock pcb;

    public ProcessView(PApplet p, ProcessControlBlock pcb) {
        this.p = p;
        this.pcb = pcb;
    }

    public void update() {

    }

    public void draw() {

    }

    public void dim() {
        //color = p.color(p.red(color), p.green(color), p.blue(color), 100);
    }

    public void light() {
        //color = p.color(p.red(color), p.green(color), p.blue(color), 220);   
    }

    public boolean isClicked(int x, int y) {
        return false; //p.dist(location.x, location.y, x, y) < (size / 2);
    }

}