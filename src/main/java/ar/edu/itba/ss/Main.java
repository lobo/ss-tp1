package ar.edu.itba.ss;

//import java.util.List;
//import java.util.Map;

//import ar.edu.itba.ss.core.BruteForce;
import ar.edu.itba.ss.core.CellIndexMethod;
import ar.edu.itba.ss.core.NearNeighbourList;
//import ar.edu.itba.ss.core.OptimalGrid;
//import ar.edu.itba.ss.core.Particle;
import ar.edu.itba.ss.core.SquareSpace;
import ar.edu.itba.ss.core.UniformGenerator;

	/**
	* <p>Punto de entrada principal de la simulación.</p>
	*/

public final class Main {

	public static void main(final String [] arguments) {

		/**/System.out.println("(2018) Cell Index Method.");
		/**/final long start = System.nanoTime();

		/*final Map<Particle, List<Particle>> nnl =*/ NearNeighbourList
				.from(UniformGenerator.of(10000)
						.invariant(true)
						//.spy(p -> System.out.println(p))
						.maxRadius(0.0)
						.over(1.0)
						.build())
				.with(/*new BruteForce()*/CellIndexMethod
						//.by(OptimalGrid.DENSITY_BASED)
						.by(400)
						.build())
				.over(SquareSpace.of(1.0)
						.periodicBoundary(true)
						.build())
				.interactionRadius(0.3)
				.cluster();

		/**/System.out.println(
				"\n\tTime: " + 1E-9*(System.nanoTime() - start) + " sec.");
	}
}