# CSE 7343 Course Project
### OS Process and Resource Management

This program is a visualization of operating system processes and queues as they are managed by a scheduler. It represents a simulation of an operating system as it executes processes, manages multiple queues, and manages memory.

It uses a doubly-linked list to represent process queues, and first-fit, best-fit, and worst-fit algorithms for managing memory.

The visualization is created with the Processing library.

## How to Build and Run the Program

To build from source, execute `ant` from the project root (this directory).
If you have received a zip file, a pre-built jar is ready to run: `java -jar dist/os.jar`.

## Quick Instructions

Press the **space bar** a bunch to create some processes. Press the **B** key to block some processes, **click** on them to unblock. Press the **K** key to kill some processes and free up some memory segments. Press **space bar** to create more processes. Use the **0**, **1** and **2** keys to change the memory allocation algorithm.

## Interacting with the Program

The simulation describes a CPU, logical memory space, and operating system. Initially, the simulation "fakes" the running of a kernel idle process.

There are initially no processes in user space, and both the ready and waiting queues are empty. Spawn a process by hitting the **space bar**. Spawn as many as you like! Observe how the simulator "executes" one process at a time, places that process at the back of the queue, and then executes the process at the head of the queue.

To fake an interrupt that causes a process to block, such as waiting for an I/O resource, press the **B** key to cause the executing process to block, resulting in it being placed on the waiting queue. Block as many as you like! Observe how the blocked processes appear dimmer.

To unblock a process, just **click** on it. Realize that if two processes visually overlap, the simulator will unblock the process that closer to the head of the wait queue. When you click on the blocked process at the head of the wait queue, you'll find that it unblocks and moves back to the ready queue.

To kill a running process, press the **K** key. You'll see that the memory for the process gets deallocated (appears black), the CPU switches context, and the process has been terminated.

## Memory Allocation

Each process requires a random amount of memory address space. When you start a new process with the **space bar**, you will find that the simulator looks for some free space to allocate for the program. By default, the simulator uses a _first-fit_ algorithm for memory allocation. You can press the **1** or **2** key to select a _best-fit_ or _worst-fit_ algorithm.

Killing a process results in the currently executing process to be immediately terminated, and its allocated memory added to the free list. Fragmentation ensues.

## License

<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />This work by Yong Bakos is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.

&copy; 2015 Yong Joseph Bakos. All rights reserved.
