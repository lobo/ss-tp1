![...](resources/image/readme-header.png)

# System Simulation: Cell Index Method

The program receives the following parameters:

1. The positions (x,y) coordinates of the N particles that the system has.
2. The radii of those particles
3. N = number of particles
4. L
5. M
6. rc
7. COMPLETE THISWalls included: this is a boolean value. If true, the algorithm considers the wall

The program outputs the following:

* For every particle, a list with all the particles that are near in less than rc
* Execution time
* A picture that shows the position of all the particles, which can also enable the selection of one of them and its neighbors.



## Running instructions [TO DO]

1. Clone this repository.
2. On the root folder, compile the project by running

```
$ mvn package
```

This will generate a jar in the `target` folder.

3. To run, execute the following command:

```
$ java -jar nameOfTheJarFile [N] [L] [M] [rc] [1|0]
```

Where:

* [N] is the number of particles
* [L] is the length of the side of the square
* [M] 
* [rc] is the radius
* The fifth parameter can be 1 or 0: 1 to include walls, 0 to exclude them.


## Input files format

### Static

```
N
L
r1 pr1
r2 pr2
r3 pr3
...
rn prN
```

### Dynamic

Note: the first line represents the time. There's only one time given that the detection of the neighbors occurs at a given instant of the system. Like a snapshot.

```
t0
x1 y1
x2 y2
x3 y3
...
xN yN
```

## Output file

[id-of-particle-i id's of the particles which border-border distance is less than rc]



## Developers

This project has been built, designed and maintained by the following authors:

* @lobo
* @agustin-golmar
