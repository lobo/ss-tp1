package ar.edu.itba.ss.core;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
import ar.edu.itba.ss.core.interfaces.Space;

	/**
	* <p>Permite computar la lista de vecinos cercanos, es decir, los
	* conjuntos (clusters) de partículas que interactúan entre sí,
	* permitiendo establecer los parámetros de operación de forma
	* declarativa.</p>
	* <p>Esta clase no es instanciable directamente, y ya que sólo
	* describe un procedimiento, es <b>stateless</b>.</p>
	*/

public final class NearNeighbourList {

	private NearNeighbourList() {
		throw new IllegalStateException(
			"No puede construir una lista sin un procedimiento.");
	}

	public static Builder from(final ParticleGenerator generator) {
		return new Builder(generator);
	}

	public static final class Builder {

		private final ParticleGenerator generator;
		private DistanceProcessor processor;
		private Space space;
		private double interactionRadius;

		public Builder(final ParticleGenerator generator) {
			this.generator = generator;
		}

		public Builder with(final DistanceProcessor processor) {
			this.processor = processor;
			return this;
		}

		public Builder over(final Space space) {
			if (space.dimensions().size() < 2)
				throw new IllegalArgumentException(
					"El espacio debe poseer una dimensión superior a 2.");
			this.space = space;
			return this;
		}

		public Builder interactionRadius(final double radius) {
			this.interactionRadius = radius;
			return this;
		}

		public Map<Particle, List<Particle>> cluster() {
			return processor.compute(generator, space, interactionRadius);
		}
	}
}