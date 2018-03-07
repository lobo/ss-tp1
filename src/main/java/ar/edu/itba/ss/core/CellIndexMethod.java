
	package ar.edu.itba.ss.core;

	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Objects;
	import java.util.function.Function;
	import java.util.stream.Stream;

	import static java.util.function.Function.identity;
	import static java.util.stream.Collectors.groupingBy;
	import static java.util.stream.Collectors.toList;
	import static java.util.stream.Collectors.toMap;

	import java.awt.Point;

	import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
	import ar.edu.itba.ss.core.interfaces.GranularityEstimator;
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

		protected final GranularityEstimator granularity;

		public CellIndexMethod(final Builder builder) {
			this.granularity = builder.granularity;
		}

		@Override
		public Map<Particle, List<Particle>> compute(
				final ParticleGenerator generator,
				final Space space,
				final double interactionRadius) {

			final int M = granularity.estimate(
					generator, space, interactionRadius);
			final double C = space.dimensions().get(0)/M;

			if (!isValid(generator, C, interactionRadius))
				throw new IllegalStateException(
					"Las dimensiones de la grilla no son consistentes con el radio de interacción.");

			System.out.println("M: " + M);

			final Map<Point, List<Particle>> cells = generator
					.generate()
					.collect(groupingBy(p -> {
						final int x = (int) Math.min(Math.floor(p.getX()/C), M - 1);
						final int y = (int) Math.min(Math.floor(p.getY()/C), M - 1);
						return new Point(x, y);
					}));

			final Map<Point, List<Point>> adjacents = cells
					.keySet().stream()
					.collect(toMap(
							identity(),
							p -> adjacent(p, space, M)));

			// Resultados:
			final Map<Particle, List<Particle>> result = new HashMap<>();

			cells.entrySet().stream()
				.forEach(e -> {
					final List<Particle> c1 = e.getValue();				// Por cada celda
					final List<Point> a = adjacents.get(e.getKey());	// Calculo celdas adyacentes

					// Cada celda adyacente se convierte en una lista de vecinos para 'p1':
					for (final Particle p1 : c1) {						// Y por cada partícula en ella
						final Function<Particle, Double> distance = Particle
							.distanceFactory(p1, space);
						final List<Particle> neighbours = a.stream()
							.map(cell -> cells.get(cell))				// Obtengo las partículas de cada celda
							.filter(Objects::nonNull)					// Quito las celdas vacías
							.flatMap(List::stream)						// Hago una 'bolsa' de partículas
							.filter(p2 -> p1 != p2)						// Distintas a 'p1'
							.filter(p2 -> distance.apply(p2) <= interactionRadius)	// Que interaccionan
							.peek(p2 -> addOn(p2, p1, result))			// Simetría
							.collect(toList());

						result.merge(p1, neighbours, CellIndexMethod::merge);
					}
				});

			return result;
		}

		public static Builder by(
				final GranularityEstimator granularity) {
			return new Builder(granularity);
		}

		public static Builder by(final int granularity) {
			return by((g, s, r) -> granularity);
		}

		public static final class Builder {

			private final GranularityEstimator granularity;

			public Builder(final GranularityEstimator granularity) {
				this.granularity = granularity;
			}

			public CellIndexMethod build() {
				return new CellIndexMethod(this);
			}
		}

		private static boolean isValid(
				final ParticleGenerator generator,
				final double cellSize,
				final double interactionRadius) {
			return interactionRadius < (cellSize - 2 * generator.maxRadius());
		}

		private static void addOn(
				final Particle key, final Particle particle,
				final Map<Particle, List<Particle>> result) {

			result.merge(key,
					new ArrayList<>(Arrays.asList(particle)),
					CellIndexMethod::merge);
		}

		private static List<Particle> merge(
				final List<Particle> l1, final List<Particle> l2) {
			l1.addAll(l2);
			return l1.stream().distinct().collect(toList());
		}

		// Memoization?
		private static List<Point> adjacent(
				final Point cell, final Space space, final int M) {

			final Stream<Direction> directions = Stream.of(
					Direction.NONE,
					Direction.UP, Direction.UP_RIGHT,
					Direction.RIGHT, Direction.DOWN_RIGHT);

			if (space.hasPeriodicBoundary())
				return directions.map(dir -> dir.wrapAdd(cell, M))
						.distinct()
						.collect(toList());

			return directions.map(dir -> dir.add(cell, M))
					.filter(Objects::nonNull)
					.collect(toList());
		}
	}
