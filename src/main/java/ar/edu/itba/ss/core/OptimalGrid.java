
package ar.edu.itba.ss.core;

import ar.edu.itba.ss.core.interfaces.GranularityEstimator;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
import ar.edu.itba.ss.core.interfaces.Space;

	/**
	* <p>Implementa un criterio de selección para la cantidad de celdas en
	* una grilla óptima y cuadrada, basado en diferentes factores.</p>
	*/

public enum OptimalGrid implements GranularityEstimator {

	AUTOMATIC {
		@Override
		public int estimate(
				final ParticleGenerator generator,
				final Space space, double interactionRadius) {

			if (space.dimensions().isEmpty())
				throw new IllegalStateException(
						"El espacio proporcionado no puede ser un espacio nulo.");
			final double L = space.dimensions().get(0);
			final double bound = L/(interactionRadius + 2 * generator.maxRadius());
			final double M = Math.floor(bound);
			return (int) Math.max(1, (M < bound? M : M - 1));
		}
	},

	DENSITY_BASED {
		@Override
		public int estimate(
				final ParticleGenerator generator,
				final Space space, final double interactionRadius) {
			if (space.dimensions().isEmpty())
				throw new IllegalStateException(
						"El espacio proporcionado no puede ser un espacio nulo.");
			final double L = space.dimensions().get(0);
			final double N = generator.size();
			final double δ = N / (L*L);

			// Cómo se calcula?
			/**/System.out.println("Density (δ): " + δ);
			/**/System.out.println("FALTA IMPLEMENTAR");
			return 1; // Brute-force...
		}
	};
}