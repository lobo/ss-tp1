package ar.edu.itba.ss.core;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
import ar.edu.itba.ss.core.interfaces.Space;

	/**
	* <p>Computa la lista de vecinos cercanos utilizando
	* <i>fuerza bruta</i>. Si el sistema posee <i>N</i> partículas,
	* entonces el algoritmo presenta una complejidad temporal proporcional
	* a <i>O(N^2)</i>.</p>
	*/

public class BruteForce implements DistanceProcessor {

	@Override
	public Map<Particle, List<Particle>> compute(
			final ParticleGenerator generator,
			final Space space,
			final double interactionRadius) {

		final List<Particle> particles = generator
				.generate()
				.collect(toList());

		return particles.stream()
				.collect(toMap(
						identity(),
						particle1 -> particles.stream()
							.filter(particle2 -> particle1 != particle2)
							.filter(particle2 -> particle1.distance(particle2) <= interactionRadius)
							.collect(toList())
						));
	}
}