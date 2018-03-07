
	package ar.edu.itba.ss.core.interfaces;

		/**
		* <p>Un estimador de granularidad permite establecer las dimensiones
		* de la grilla utilizada por el algoritmo <i>Cell Index</i>. Los
		* parámetros utilizados durante la estimación dependen del
		* estimador.</p>
		*/

	@FunctionalInterface
	public interface GranularityEstimator {

		public int estimate(
				final ParticleGenerator generator,
				final Space space, final double interactionRadius);
	}
