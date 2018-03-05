
	package ar.edu.itba.ss.core;

	import java.util.function.Consumer;
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
		protected final Consumer<Particle> consumer;

		private UniformGenerator(final Builder builder) {
			this.size = builder.size;
			this.maxLength = builder.maxLength;
			this.maxRadius = builder.maxRadius;
			this.consumer = builder.consumer;
		}

		public static Builder of(final int size) {
			return new Builder(size);
		}

		@Override
		public Stream<Particle> generate() {
			return Stream.generate(() -> {
				return new Particle(
						Math.random() * maxLength,
						Math.random() * maxLength,
						Math.random() * maxRadius);
			}).limit(size).peek(consumer);
		}

		@Override
		public int size() {
			return size;
		}

		public static final class Builder {

			private final int size;
			private double maxLength;
			private double maxRadius;
			private Consumer<Particle> consumer;

			public Builder(final int size) {
				this.size = size;
				this.maxRadius = 0;
				this.consumer = p -> {};
			}

			public Builder spy(final Consumer<Particle> consumer) {
				this.consumer = consumer;
				return this;
			}

			public Builder maxRadius(final double radius) {
				this.maxRadius = radius;
				return this;
			}

			public Builder over(final double maxLength) {
				this.maxLength = maxLength;
				return this;
			}

			public UniformGenerator build() {
				return new UniformGenerator(this);
			}
		}
	}
