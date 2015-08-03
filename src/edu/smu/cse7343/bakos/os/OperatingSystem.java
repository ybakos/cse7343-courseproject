/*
    OperatingSystem.java
    @author Yong Joseph Bakos

    This class represents a simple, simulated operating system that provides "system calls"
    for executing new processes, blocking, unblocking, interrupting, and allocating memory.

    It leverages a brute-force first-fit, best-fit, and worst-fit allocation algorithm for
    memory. Two queues, ready and wait, are maintained for managing PCBs.
*/

package edu.smu.cse7343.bakos.os;

import java.util.*;

public class OperatingSystem {

    public static final int ROUND_ROBIN_CYCLE_LIMIT = 30;
    private static final int FAUX_INITIAL_USERSPACE_PID = 10;
    private static final int DEFAULT_HEAP_SIZE = 10;
    private static final int DEFAULT_STACK_SIZE = 10;

    private CPU cpu;
    private Memory memory;
    private int currentPid;
    private int nextAvailablePid;
    public ProcessQueue readyQueue;
    public ProcessQueue waitQueue;

    private TreeMap<Integer, Integer> freeMap;
    public MemoryAllocationAlgorithm allocationAlgorithm;

    private Random rand;

    public OperatingSystem(CPU cpu, Memory memory) {
        this.cpu = cpu;
        this.memory = memory;
        currentPid = 0; // idle
        nextAvailablePid = FAUX_INITIAL_USERSPACE_PID;
        readyQueue = new ProcessQueue(ProcessState.READY);
        waitQueue = new ProcessQueue(ProcessState.WAITING);
        freeMap = new TreeMap<Integer, Integer>();
        freeMap.put(new Integer(0), new Integer(memory.totalSize()));
        allocationAlgorithm = MemoryAllocationAlgorithm.FIRST_FIT;
        rand = new Random();
    }

    // Simulates the cycles of a scheduler thread. Switches context when the round-robin cycle
    // period ends and there are other PCBs in the ready queue.
    public void manageProcesses() {
        if (!readyQueue.isEmpty() && (cpu.isIdle || roundRobinCycleLimitReached())) {
            switchContext();
        } // else idle
    }

    // Execute a new process, by adding a PCB for the new process to the tail
    // of the ready queue.
    public void exec() {
        Program program = new Program(); // load program from disk
        // allocate memory
        int memoryNeeded = (int)program.size;
        int base = alloc(memoryNeeded);
        // store in memory
        storeInMemory(base, memoryNeeded, program);
        ProcessControlBlock pcb = new ProcessControlBlock(nextAvailablePid++, base, base + memoryNeeded - 1, program);
        readyQueue.add(pcb);
    }

    // Simulates the loading of a program into allocated memory.
    private void storeInMemory(int baseAddress, int memoryNeeded, Program p) {
        for (int i = baseAddress; i < baseAddress + memoryNeeded; ++i) {
            memory.write(i, Float.intBitsToFloat(p.color));
        }
    }

    // A simulated `malloc` system call. Uses either a FIRST_FIT, BEST_FIT, or WORST_FIT
    // algorithm.
    // TODO: Replace with Strategy pattern.
    private int alloc(int memoryNeeded) {
        // first fit
        System.out.println("ALLOC " + memoryNeeded);
        if (allocationAlgorithm == MemoryAllocationAlgorithm.FIRST_FIT) {
            for (Integer key : freeMap.keySet()) {
                int baseAddress = key.intValue();
                int size = freeMap.get(key).intValue();
                System.out.println("Checking: " + baseAddress + "->" + size);
                if (size > memoryNeeded) {
                    freeMap.remove(key);
                    int newFreeBaseAddress = baseAddress + memoryNeeded;
                    int newFreeSize = baseAddress + size - newFreeBaseAddress;
                    System.out.println("Found space at: " + baseAddress);
                    System.out.println("Remaining: " + newFreeBaseAddress + "->" + newFreeSize);
                    freeMap.put(new Integer(newFreeBaseAddress), new Integer(newFreeSize));
                    return baseAddress;
                }
            }
        } else if (allocationAlgorithm == MemoryAllocationAlgorithm.BEST_FIT) {
            System.out.println("BEST FIT");
            System.out.println("Segment size - >Address");
            TreeMap<Integer, Integer> segmentSizes = new TreeMap<Integer, Integer>(); // size -> address
            for (Integer key : freeMap.keySet()) {
                segmentSizes.put(freeMap.get(key), key);
                System.out.println(freeMap.get(key) + " -> " + key);
            }
            Integer bestFittingSegmentSize = segmentSizes.ceilingKey(new Integer(memoryNeeded));
            if (bestFittingSegmentSize != null) {
                int baseAddress = segmentSizes.get(bestFittingSegmentSize).intValue();
                int newFreeBaseAddress = baseAddress + memoryNeeded;
                int newFreeSize = baseAddress + bestFittingSegmentSize.intValue() - newFreeBaseAddress;
                System.out.println("Found space at: " + baseAddress);
                System.out.println("Remaining: " + newFreeBaseAddress + "->" + newFreeSize);
                freeMap.remove(new Integer(baseAddress));
                freeMap.put(new Integer(newFreeBaseAddress), new Integer(newFreeSize));
                return baseAddress;
            }
        } else if (allocationAlgorithm == MemoryAllocationAlgorithm.WORST_FIT) {
            System.out.println("WORST FIT");
            System.out.println("Segment size - >Address");
            TreeMap<Integer, Integer> segmentSizes = new TreeMap<Integer, Integer>(); // size -> address
            for (Integer key : freeMap.keySet()) {
                segmentSizes.put(freeMap.get(key), key);
                System.out.println(freeMap.get(key) + " -> " + key);
            }
            Integer worstFittingSegmentSize = segmentSizes.lastKey();
            if (worstFittingSegmentSize.intValue() >= memoryNeeded) {
                int baseAddress = segmentSizes.get(worstFittingSegmentSize).intValue();
                int newFreeBaseAddress = baseAddress + memoryNeeded;
                int newFreeSize = baseAddress + worstFittingSegmentSize.intValue() - newFreeBaseAddress;
                System.out.println("Found space at: " + baseAddress);
                System.out.println("Remaining: " + newFreeBaseAddress + "->" + newFreeSize);
                freeMap.remove(new Integer(baseAddress));
                freeMap.put(new Integer(newFreeBaseAddress), new Integer(newFreeSize));
                return baseAddress;
            }
        }
        return 0; // TODO: Swap!
    }

    // Simulation of a `free` system call. Adds unallocated process memory to the free list.
    // Merges any adjacent free segments in the free list.
    private void free(int start, int end) {
        System.out.println("FREEDOOOOMMMMM!!!");
        freeMap.put(new Integer(start), new Integer(end - start + 1));
        // Merge contiguous blocks, from back to front.
        ArrayList<Integer> mergedSegmentsToDelete = new ArrayList<Integer>();
        for (Integer key : freeMap.descendingKeySet()) {
            Integer nextFreeSegmentBaseAddress = freeMap.lowerKey(key);
            // System.out.println("Inspecting: " + key + " | floorKey: " + nextFreeSegmentBaseAddress);
            if (nextFreeSegmentBaseAddress != null && nextFreeSegmentBaseAddress.intValue() + freeMap.get(nextFreeSegmentBaseAddress).intValue() == key.intValue()) {
                freeMap.put(nextFreeSegmentBaseAddress, new Integer(freeMap.get(key).intValue() + freeMap.get(nextFreeSegmentBaseAddress).intValue()));
                mergedSegmentsToDelete.add(key);
            }
        }
        // Delete the upper part of all merged segments.
        for (Integer deletableMergedSegmentAddress : mergedSegmentsToDelete)
            freeMap.remove(deletableMergedSegmentAddress);
        // Show the freelist, for sanity checking (and demo).
        System.out.println("Freelist:");
        for (Integer key : freeMap.keySet()) {
            System.out.println(key.intValue() + "->\t" + freeMap.get(key).intValue());
        }
        // Zero-out the memory, just so we can see visually that it is free.
        for (int i = start; i <= end; ++i) {
            memory.write(i, 0);
        }
    }

    // Place the currently executing process' PCB at the tail of the ready queue,
    // and restore the execution of the process represented by the PCB at the head
    // of the queue.
    private void switchContext() {
        if (readyQueue.isEmpty()) return;
        if (cpuIsExecutingAUserspaceProcess()) {
            ProcessControlBlock pcb = new ProcessControlBlock(currentPid, cpu.baseRegister, cpu.limitRegister, cpu.currentProgram);
            pcb.state = ProcessState.READY;
            pcb.programCounter = cpu.programCounter;
            // pcb.registers = cpu.registers.clone();
            readyQueue.add(pcb);
        }
        dispatch(readyQueue.remove());
    }

    // Simulation of an OS dispatcher. Restores the state of the CPU from a PCB.
    private void dispatch(ProcessControlBlock pcb) {
        currentPid = pcb.pid;
        pcb.state = ProcessState.RUNNING;
        cpu.exec(pcb);
    }

    // Simulates the self-blocking of a process, as if it is waiting for a resource.
    // Once the process is blocked, it is placed on the waiting queue, a context switch
    // occurs, and the corresponding view is dimmed to indicate that the process is blocked.
    public void blockCurrentProcess() {
        if (!cpuIsExecutingAUserspaceProcess()) return;
        ProcessControlBlock pcb = new ProcessControlBlock(currentPid, cpu.baseRegister, cpu.limitRegister, cpu.currentProgram);
        pcb.state = ProcessState.WAITING;
        pcb.programCounter = cpu.programCounter;
        waitQueue.add(pcb);
        if (readyQueue.isEmpty()) {
            cpu.currentProgram = null;
            cpu.isIdle = true;
        } else {
            dispatch(readyQueue.remove());
        }
    }

    // Simulates the availability of a resource and an interrupt that allows the process
    // corresponding with the PCB at the head of the queue to be placed back in the ready
    // queue.
    public void interruptAndUnblock(ProcessControlBlock pcb) {
        if (waitQueue.remove(pcb)) {
            pcb.state = ProcessState.READY;
            readyQueue.add(pcb);
        }
    }

    // Simulates a `kill` system call. Frees the memory for a process, terminates it, and
    // interrupts the CPU to dispatch the next ready process.
    public void killCurrentProcess() {
        if (cpu.isIdle) return;
        free(cpu.baseRegister, cpu.limitRegister);
        if (readyQueue.isEmpty()) {
            cpu.isIdle = true;
            cpu.currentProgram = null;
        } else {
            dispatch(readyQueue.remove());
        }
    }

    // A naive simulation of determining if a process has received enough CPU time.
    private boolean roundRobinCycleLimitReached() {
        return cpu.cycleCount % ROUND_ROBIN_CYCLE_LIMIT == 0;
    }

    // In this simulation, process id 0 is the kernel idle process; all other
    // processes are userspace processes.
    private boolean cpuIsExecutingAUserspaceProcess() {
        return !cpu.isIdle;
    }

}