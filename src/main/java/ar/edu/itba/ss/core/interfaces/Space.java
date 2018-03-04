
	package ar.edu.itba.ss.core.interfaces;

	import java.util.List;

		/**
		* <p>Un espacio debe exponer su dimensión (cardinalidad de la base), y
		* la extensión de cada una de sus componentes. Se espera (pero no se
		* fuerza), que las extensiones sean números reales positivos.</p>
		* <p><b>Nunca</b> definir esta interfaz como
		* {@link java.lang.FunctionalInterface @FunctionalInterface}, ya que
		* la característica de un espacio no es proveer funcionalidad.</p>
		*/

	public interface Space {

		public List<Double> dimensions();
	}
