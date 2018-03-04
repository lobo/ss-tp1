
	package ar.edu.itba.ss.core;

	import java.util.Arrays;
	import java.util.List;

	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p>Modela un espacio bidimensional cuadrado y finito.</p>
		*/

	public final class SquareSpace implements Space {

		private final List<Double> dimensions;

		private SquareSpace(final double length) {
			this.dimensions = Arrays.asList(length, length);
		}

		public static Space of(final double length) {
			return new SquareSpace(length);
		}

		@Override
		public List<Double> dimensions() {
			return dimensions;
		}
	}
