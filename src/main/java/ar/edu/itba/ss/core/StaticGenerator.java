package ar.edu.itba.ss.core;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ar.edu.itba.ss.core.UniformGenerator.Builder;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;

//new Particle(x,y,z)
//List<Particle> list
// list.generate()
public class StaticGenerator implements ParticleGenerator{

	private int size;
	private double maxRadius;
	List<Particle> particles;

	private StaticGenerator(final String staticFilename, final String dynamicFilename) {
		
		//this.size = N

	}

	@Override
	public Stream<Particle> generate() {
		return particles.stream();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public double maxRadius() {
		return maxRadius;
	}

}
