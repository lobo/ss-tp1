
	package ar.edu.itba.ss.core;

	import java.util.HashMap;
	import java.util.List;

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
		public HashMap<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space,
				final double interactionRadius) {

			// Calcular distancias...

			return new HashMap<Particle, List<Particle>>();
		}
	}
