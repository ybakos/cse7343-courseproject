package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class Sketch extends PApplet {
    
    ProcessView pv;

    public void setup() {
        size(displayWidth, displayHeight);
        pv = new ProcessView(this, width / 2, height / 2);
    }

    public void draw() {
        background(0);
        pv.update();
        pv.draw();
    }

}