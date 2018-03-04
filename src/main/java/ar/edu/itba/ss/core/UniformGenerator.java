
	package ar.edu.itba.ss.core;

	import java.util.stream.Stream;

	import ar.edu.itba.ss.core.interfaces.ParticleGenerator;

		/**
		* <p>Provee un generador aleatorio de partículas con distribución
		* uniforme, sobre un espacio bidimensional cuadrado. Si no se
		* especifica el radio máximo de las partículas, el generador provee
		* partículas puntuales.</p>
		* <p>La generación de partículas es <b>lazy</b>.</p>
		*/

	public class UniformGenerator implements ParticleGenerator {

		protected final int size;
		protected final double maxLength;
		protected final double maxRadius;

		public UniformGenerator(
				final int size,
				final double maxLength,
				final double maxRadius) {
			this.size = size;
			this.maxLength = maxLength;
			this.maxRadius = maxRadius;
		}

		public UniformGenerator(final int size, final double maxLength) {
			this(size, maxLength, 0);
		}

		@Override
		public Stream<Particle> generate() {
			return Stream.generate(() -> {
				return new Particle(
						Math.random() * maxLength,
						Math.random() * maxLength,
						Math.random() * maxRadius);
			}).limit(size);
		}

		@Override
		public int size() {
			return size;
		}
	}
