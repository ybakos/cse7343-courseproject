/*
    OperatingSystem.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class OperatingSystem {

    public static final int ROUND_ROBIN_CYCLE_LIMIT = 30;
    private static final int FAUX_INITIAL_USERSPACE_PID = 10;

    private CPU cpu;
    public int nextPid = FAUX_INITIAL_USERSPACE_PID;
    public ProcessQueue readyQueue;
    public ProcessQueue waitQueue;
    public ProcessControlBlock currentProcess;

    public OperatingSystem(CPU cpu) {
        this.cpu = cpu;
        readyQueue = new ProcessQueue(ProcessState.READY);
        waitQueue = new ProcessQueue(ProcessState.WAITING);
        currentProcess = null; // TODO: necessary after new CPU integration?
    }

    public void syscall(String code, int arg) {
        if ("wait".equals(code)) {
        //    wait(arg);
        }
    }

    // Adds a new PCB to the tail of the ready queue, and also creates a ProcessView
    // to visually represent the process for that PCB.
    public ProcessControlBlock createNewProcess() {
        ProcessControlBlock pcb = new ProcessControlBlock(nextPid);
        readyQueue.add(pcb);
        ++nextPid;
        return pcb;
    }

    // Executes the current process. If it has been given enough CPU time, then conduct a
    // context switch by placing the current PCB at the tail of the ready queue, and use the
    // restore execution of the process represented by the PCB at the head of the queue.
    public void run(int elapsedCycles) {
        if (!readyQueue.isEmpty() && roundRobinCycleLimitReached(elapsedCycles)) {
            switchContext();
        } else if (currentProcess != null) {
            execute(currentProcess);
        } else {
            // idle
        }
    }

    // Place the currently executing process' PCB at the tail of the ready queue,
    // and restore the execution of the process represented by the PCB at the head
    // of the queue.
    private void switchContext() {
        if (currentProcess != null) {
            readyQueue.add(currentProcess);
            currentProcess.state = ProcessState.READY;
        }
        if (!readyQueue.isEmpty()) {
            currentProcess = readyQueue.remove();
            currentProcess.state = ProcessState.RUNNING;
        }
    }

    // Simulates the execution of a process represented by the particular PCB,
    // and updates the corresponding ProcessView that visually represents the
    // process associated with that PCB.
    private void execute(ProcessControlBlock pcb) {
        pcb.programCounter++;
        // TODO: extract ProcessView view = processViews.get(new Integer(pcb.pid));
        // if (view != null) view.update();
    }

    // Simulates the self-blocking of a process, as if it is waiting for a resource.
    // Once the process is blocked, it is placed on the waiting queue, a context switch
    // occurs, and the corresponding view is dimmed to indicate that the process is blocked.
    public void blockCurrentProcess() {
        if (currentProcess == null) return;
        currentProcess.state = ProcessState.WAITING;
        waitQueue.add(currentProcess);
        // TODO: extract ProcessView view = processViews.get(new Integer(currentProcess.pid));
        // if (view != null) view.dim();
        currentProcess = null;
        switchContext();
    }

    // Simulates the availability of a resource and an interrupt that allows the process
    // corresponding with the PCB at the head of the queue to be placed back in the ready
    // queue.
    // private void interruptAndUnblock(int pid) {
    //     ProcessControlBlock waitHead = os.waitQueue.peek();
    //     if (waitHead != null && pid == waitHead.pid) {
    //         ProcessControlBlock pcb = os.waitQueue.remove();
    //         pcb.state = ProcessState.READY;
    //         os.readyQueue.add(waitHead);
    //         processViews.get(new Integer(pid)).light();
    //     }
    // }

    // A naive simulation of determining if a process has received enough CPU time.
    private boolean roundRobinCycleLimitReached(int elapsedCycles) {
        return elapsedCycles % ROUND_ROBIN_CYCLE_LIMIT == 0;
    }

}