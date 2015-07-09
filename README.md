# CSE 7343 Course Project
### OS Process and Resource Management

This program is a visualization of operating system processes and queues as they are managed by a scheduler. It represents a simulation of an operating system as it executes processes, manages multiple queues, and manages memory (TODO Phase 2).

It uses a doubly-linked list to represent process queues, and first-fit, best-fit, and worst-fit algorithms for managing memory (TODO Phase 2).

The visualization is created with the Processing library.

## How to Build and Run the Program

To build from source, execute `ant` from the project root (this directory).
If you have received a zip file, a pre-built jar is ready to run: `java -jar dist/os.jar`.

## Interacting with the Program

There are initially no processes in user space, and both the ready and waiting queues are empty. Spawn a process by hitting the space bar. Spawn as many as you like! Observe how the simulator "executes" one process at a time, places that process at the back of the queue, and then executes the process at the head of the queue.

To fake an interrupt that causes a process to block, such as waiting for an I/O resource, press the B key to cause the executing process to block, resulting in it being placed on the waiting queue. Block as many as you like! Observe how the blocked processes appear dimmer.

To unblock a process, just click on it. However, realize that you can only unblock the one that appears at the head of the wait queue. This is just a naive simulation, and may be improved later. When you click on the blocked process at the head of the wait queue, you'll find that it unblocks and moves back to the ready queue.

## TODO More Documentation 

TODO

## License

<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />This work by Yong Bakos is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.

&copy; 2015 Yong Joseph Bakos. All rights reserved.
