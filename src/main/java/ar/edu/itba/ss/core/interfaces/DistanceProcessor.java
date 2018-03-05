
	package ar.edu.itba.ss.core.interfaces;

	import java.util.List;
	import java.util.Map;

	import ar.edu.itba.ss.core.Particle;

		/**
		* <p>Un procesador de distancias recibe una fuente de partículas, el
		* espacio sobre el que se encuentran, y el radio de interacción entre
		* ellas. Se encarga de computar los clusters de partículas más
		* cercanas, es decir, aquellas que efectivamente interactúan entre
		* sí.</p>
		*/

	public interface DistanceProcessor {

		public Map<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space,
				final double interactionRadius);
	}
