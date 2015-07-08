package edu.smu.cse7343.bakos.os;

import processing.core.*;

public abstract class Drawable {

    PApplet p;
    int x;
    int y;
    int width;
    int height;
    int strokeColor = 200;
    int fillColor = 100;
    int labelColor = 255;

    public abstract void update();
    public abstract void draw();

}