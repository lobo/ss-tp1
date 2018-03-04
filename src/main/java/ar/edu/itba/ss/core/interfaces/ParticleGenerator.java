
	package ar.edu.itba.ss.core.interfaces;

	import java.util.stream.Stream;

	import ar.edu.itba.ss.core.Particle;

		/**
		* <p>Provee un único método cuyo objetivo es construir un
		* <i>Stream</i> de partículas a modo de abstraer la fuente de origen
		* de las mismas.</p>
		*/

	@FunctionalInterface
	public interface ParticleGenerator {

		public Stream<Particle> generate();
	}
