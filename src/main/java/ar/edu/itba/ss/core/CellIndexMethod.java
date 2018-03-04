
	package ar.edu.itba.ss.core;

	import java.util.HashMap;
	import java.util.List;
	import java.util.function.BiFunction;

	import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
	import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p>Computa la lista de vecinos cercanos utilizando el método
		* <i>Cell Index</i>. Requiere que se especifique la forma en la cual
		* subdividir el espacio en el que se encuentran las partículas. Este
		* algoritmo posee una complejidad temporal proporcional a <i>O(N)</i>,
		* donde <i>N</i> es la cantidad de partículas.</p>
		*/

	public class CellIndexMethod implements DistanceProcessor {

		protected final BiFunction<Integer, Space, Integer> grid;

		public CellIndexMethod(final Builder builder) {
			this.grid = builder.grid;
		}

		@Override
		public HashMap<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space,
				final double interactionRadius) {

			final int n = generator.size();
			final int gridSize = grid.apply(n, space);

			// Calcular distancias...

			return new HashMap<Particle, List<Particle>>();
		}

		public static Builder by(
				final BiFunction<Integer, Space, Integer> grid) {
			return new Builder(grid);
		}

		public static Builder by(final int gridSize) {
			return by((n, s) -> gridSize);
		}

		public static final class Builder {

			private final BiFunction<Integer, Space, Integer> grid;

			public Builder(final BiFunction<Integer, Space, Integer> grid) {
				this.grid = grid;
			}

			public CellIndexMethod build() {
				return new CellIndexMethod(this);
			}
		}
	}
