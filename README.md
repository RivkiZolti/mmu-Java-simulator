# MMU Project
Memory Management Unit(MMU) in Java.
 
 ## Purpose of the project
The purpose of the project is to create a system that simulates the activity of a memory management unit.
The project is written in JAVA language.

## What it is MMU
Memory Management Unit - This unit functions as an integral part of the processor and is responsible for managing memory on the computer.
Memory management on the computer is done by the Paging method - a method that allows the operating system to transfer memory segments
between the main memory (RAM) and the secondary (Hard Disk) and vice versa.
The guideline for this memory management method is the dedicated algorithms that the system uses to determine which page to replace.

## Paging algorithms that has been used
There are several different paging algorithms where the pages are mapped to memory, we were asked to implement:
* LRU - Least Recently Used.
* MFU - Least/Most Frequently Used (LFU/MFU).
* Random.

## Application and system architecture
In the project we created a client-server interface where we manage to send requests from the client using the GUI.
Requests such as data access for the "browse" operation, update and delete information.

This system is built on OOP principles and has been used in common Design Pattern:
* Observe Pattern.
* Strategy Pattern.

Also, the GUI is used with the help of SWING and working with JSON files.

The system is structured to hold pages in memory identified by ID.
The system transfers memory clips between the secondary memory and the head according to user requests.
With the help of multi-threading, the system is able to receive requests from several customers at the same time.

A video showing how the project works can be found at [this link](https://www.youtube.com/watch?v=ORghKozoJnE)
(Hebrew language) 
