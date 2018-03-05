package ar.edu.itba.ss;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ss.core.BruteForce;
//import ar.edu.itba.ss.core.CellIndexMethod;
import ar.edu.itba.ss.core.NearNeighbourList;
//import ar.edu.itba.ss.core.OptimalGrid;
import ar.edu.itba.ss.core.Particle;
import ar.edu.itba.ss.core.SquareSpace;
import ar.edu.itba.ss.core.UniformGenerator;

	/**
	* <p>Punto de entrada principal de la simulación.</p>
	*/

public final class Main {

	public static void main(final String [] arguments) {

		System.out.println("(2018) Cell Index Method.");

		// Agregar un método intermedio de serialización (útil para generaciones random!)
		// Necesitamos un detector de cercanía!!! No debería ser interno?
		// Hay que tener en cuenta partículas superpuestas?
		//		si están superpuestas, la distancia es negativa.

		final Map<Particle, List<Particle>> nnl = NearNeighbourList
				.from(new UniformGenerator(5, 1.0, 0.2))            // Genera 5 partículas...
				.with(new BruteForce()/*CellIndexMethod
						.by(OptimalGrid.DENSITY_BASED)
						.build()*/)
				.over(SquareSpace.of(1.0)
						.periodicBoundary(true)
						.build())
				.interactionRadius(0.1)
				.cluster();

		nnl.forEach((particle, neighbours) -> {

			System.out.println(
					particle.hashCode() + ":(" +
					particle.getX() + ", " +
					particle.getY() + ", r:" +
					particle.getRadius() + ") -> [" +
					list(neighbours) + "]");
		});
	}

	private static String list(final List<Particle> neighbours) {
		String list = "";
		for (final Particle particle : neighbours) {
			list += particle.hashCode() + ", ";
		}
		if (!neighbours.isEmpty()) {
			return list.substring(0, list.length() - 2);
		}
		else return list;
	}
}