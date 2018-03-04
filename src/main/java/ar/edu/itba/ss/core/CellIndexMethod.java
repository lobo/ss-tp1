
	package ar.edu.itba.ss.core;

	import java.util.HashMap;
	import java.util.List;
	import java.util.function.BiFunction;

	import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
	import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p></p>
		*/

	public final class CellIndexMethod implements DistanceProcessor {

		private final BiFunction<Integer, Space, Integer> grid;

		private CellIndexMethod(final Builder builder) {
			this.grid = builder.grid;
		}

		public static Builder by(
				final BiFunction<Integer, Space, Integer> grid) {
			return new Builder(grid);
		}

		public static Builder by(final int gridSize) {
			return by((n, s) -> gridSize);
		}

		@Override
		public HashMap<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space) {
			return new HashMap<Particle, List<Particle>>();
		}

		public static final class Builder {

			private final BiFunction<Integer, Space, Integer> grid;

			public Builder(final BiFunction<Integer, Space, Integer> grid) {
				this.grid = grid;
			}

			public DistanceProcessor build() {
				return new CellIndexMethod(this);
			}
		}
	}
