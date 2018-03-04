
	package ar.edu.itba.ss.core;

	import java.util.HashMap;
	import java.util.List;

	import ar.edu.itba.ss.core.interfaces.DistanceProcessor;
	import ar.edu.itba.ss.core.interfaces.ParticleGenerator;
	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p></p>
		* <p>Esta clase no es instanciable directamente.</p>
		*/

	public final class NearNeighbourList {

		private final ParticleGenerator generator;
		private DistanceProcessor processor;
		private boolean periodic;
		private double interactionRadius;	//default?
		private Space space;	//...

		private NearNeighbourList(final ParticleGenerator generator) {
			this.generator = generator;
			this.periodic = false;
		}

		public static NearNeighbourList from(final ParticleGenerator generator) {
			return new NearNeighbourList(generator);
		}

		public ParticleGenerator getGenerator() {
			return generator;
		}

		public Space getSpace() {
			return space;
		}

		public boolean hasPeriodicBoundary() {
			return periodic;
		}

		public NearNeighbourList with(final DistanceProcessor processor) {
			this.processor = processor;
			return this;
		}

		public NearNeighbourList periodicBoundary(final boolean periodic) {
			this.periodic = periodic;
			return this;
		}

		public NearNeighbourList periodicBoundary() {
			return periodicBoundary(true);
		}

		public NearNeighbourList interactionRadius(final double radius) {
			this.interactionRadius = radius;
			return this;
		}

		public NearNeighbourList over(final Space space) {
			this.space = space;
			return this;
		}

		public HashMap<Particle, List<Particle>> collect() {
			return processor.compute(generator, space);
		}
	}
