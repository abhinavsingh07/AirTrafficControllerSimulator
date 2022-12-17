# AirTrafficControllerSimulator
The Application demonstrates ATC simulation by using concepts of Concurrency in Java.

### Problem Statement
- There is only 1 runway for all planes to land and depart.
- Ensure that the aircraft does not collide with another aircraft on the runway or gates
- Once an aircraft obtains permission to land, it should land on the runway, coast to the
assigned gate, dock to the gate, allow passengers to disembark, refill supplies and fuel,
receive new passengers, undock, coast to the assigned runway and take-off.
- Each step should take some time.
- A congested scenario should be simulated where planes are waiting to land while the 2
gates are occupied.
- As the airport is small, there is no waiting area on the ground for the planes to wait for a
gate to become available.

![image](https://user-images.githubusercontent.com/67514678/208241183-56acbb7e-c0ad-4cc1-9476-7151bc8005d0.png)

### Additional Requirements
**These events should happen concurrently:**
- Passengers disembarking/embarking
- Refill supplies and cleaning of aircraft
**As there is only 1 refuelling truck, this event should happen exclusively:**
- Refuelling of aircraft

### The Statistics
**At the end of the simulation, i.e., when all planes have left the airport, the ATC manager
should do some sanity checks of the airport and print out some statistics on the run. The result
of the sanity checks must be printed. You must**
- Check that all gates are indeed empty.
- Print out statistics on
- Maximum/Average/Minimum waiting time for a plane.
- Number of planes served/Passengers boarded.
