
	package ar.edu.itba.ss.core.interfaces;

	import java.util.HashMap;
	import java.util.List;

	import ar.edu.itba.ss.core.Particle;

		/**
		* <p></p>
		*/

	public interface DistanceProcessor {

		public HashMap<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space);
	}
