
	package ar.edu.itba.ss.core;

	import java.util.Arrays;
	import java.util.List;

	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p>Modela un espacio bidimensional cuadrado y finito, con o sin
		* condiciones de contorno periódicas.</p>
		*/

	public class SquareSpace implements Space {

		protected final List<Double> dimensions;
		protected final boolean periodic;

		public SquareSpace(final Builder builder) {
			this.dimensions = builder.dimensions;
			this.periodic = builder.periodic;
		}

		public static Builder of(final double length) {
			return new Builder(Arrays.asList(length, length));
		}

		@Override
		public List<Double> dimensions() {
			return dimensions;
		}

		@Override
		public boolean hasPeriodicBoundary() {
			return periodic;
		}

		public static final class Builder {

			private final List<Double> dimensions;
			private boolean periodic;

			public Builder(final List<Double> dimensions) {
				this.dimensions = dimensions;
				this.periodic = false;
			}

			public Builder periodicBoundary(final boolean periodic) {
				this.periodic = periodic;
				return this;
			}

			public SquareSpace build() {
				return new SquareSpace(this);
			}
		}
	}
