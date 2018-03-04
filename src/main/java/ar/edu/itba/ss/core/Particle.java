
	package ar.edu.itba.ss.core;

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
	}
