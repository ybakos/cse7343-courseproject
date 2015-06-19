/*
    Process.java
    @author Yong Joseph Bakos

    This class represents an operating system process. TODO

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class Process {

    public ProcessView view;

    public Process(ProcessView view) {
        this.view = view;
    }

    public void execute() {
        // TODO
        view.update();
    }

}