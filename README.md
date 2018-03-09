![...](resources/image/readme-header.png)

# System Simulation: Cell Index Method

The program receives the following parameters:

1. The positions (x,y) coordinates of the N particles that the system has.
2. The radii of those particles
3. N = number of particles
4. L = length of the side
5. M = number of cells
6. RC
7. Walls included: this is a boolean value. If true, the algorithm considers the wall

The program outputs the following:

* For every particle, a list with all the particles that are near in less than RC
* Execution time
* A picture that shows the position of all the particles, which can also enable the selection of one of them and its neighbors.



## Build

To build the project, it is necessary to have Maven and Java 1.8 installed. Then, run

```
$ mvn clean package
```

This will generate a jar in the `target` folder.

3. Execute

```
$ java -jar nameOfTheJarFile [N] [R] [L] [RC] [true|false]
```

Where:

* [N] is the number of particles
* [R] is the radius
* [L] is the length of the side of the square
* [M] 
* [RC] is the radius
* The fifth parameter can be true or false: true to include particle walls, false to exclude them.


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

Note: the first line represents the time. Given the fact that the detection of the neighbors occurs at a given instant of the system, there's only one time in this file. Like a snapshot.

```
t0
x1 y1
x2 y2
x3 y3
...
xN yN
```

## Output file format

```
[id-of-particle-i id's of the particles which border-border distance is less than rc]
```

## Developers

This project has been built, designed and maintained by the following authors:

* [Daniel Lobo](https://github.com/lobo)
* [Agustín Golmar](https://github.com/agustin-golmar)
