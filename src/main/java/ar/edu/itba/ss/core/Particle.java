
	package ar.edu.itba.ss.core;

	import java.util.function.Function;

	import ar.edu.itba.ss.core.interfaces.Space;

		/**
		* <p>Modela una partícula básica del sistema.</p>
		* <p>Esta clase es <b>inmutable</b>.</p>
		*/

	public class Particle {

		protected final double x;
		protected final double y;
		protected final double radius;

		public Particle(final double x, final double y, final double radius) {
			this.x = x;
			this.y = y;
			this.radius = radius;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getRadius() {
			return radius;
		}

		public double distance(final Particle particle) {
			return Math.hypot(x - particle.x, y - particle.y)
					- radius
					- particle.radius;
		}

		public double periodicDistance(
				final Particle particle, final Space space) {
			final double Lx = space.dimensions().get(0);
			final double Ly = space.dimensions().get(1);
			final double mcx = Math.abs(x - particle.x);
			final double mcy = Math.abs(y - particle.y);
			final double r = radius + particle.radius;
			double dx = mcx;
			double dy = mcy;
			if ((Lx - mcx - r) < (mcx - r)) dx = Lx - mcx;
			if ((Ly - mcy - r) < (mcy - r)) dy = Ly - mcy;
			return Math.hypot(dx, dy) - r;
		}

		public static Function<Particle, Double> distanceFactory(
				final Particle p1, final Space space) {
			return space.hasPeriodicBoundary()?
					p2 -> p1.periodicDistance(p2, space) :
					p2 -> p1.distance(p2);
		}
	}
