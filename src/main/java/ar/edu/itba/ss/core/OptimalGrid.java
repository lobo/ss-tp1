
	package ar.edu.itba.ss.core;

	import java.util.function.BiFunction;

	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p>Implementa un criterio de selección para una grilla óptima y
		* cuadrada basado en la densidad de partículas en el espacio.
		* Actualmente no implementa otros criterios de selección.</p>
		*/

	public enum OptimalGrid implements BiFunction<Integer, Space, Integer> {

		DENSITY_BASED {
			@Override
			public Integer apply(final Integer n, final Space space) {
				if (space.dimensions().isEmpty())
					throw new IllegalStateException(
							"El espacio proporcionado no puede ser un espacio nulo.");
				final double length = space.dimensions().get(0);
				final double density = n / (length * length);

				// Cómo se calcula?

				return 1; // Brute-force...
			}
		};
	}
