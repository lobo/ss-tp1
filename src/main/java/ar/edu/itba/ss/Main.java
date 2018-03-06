package ar.edu.itba.ss;

import java.util.List;
import java.util.Map;

//import java.util.List;
//import java.util.Map;

//import ar.edu.itba.ss.core.BruteForce;
import ar.edu.itba.ss.core.CellIndexMethod;
import ar.edu.itba.ss.core.NearNeighbourList;
import ar.edu.itba.ss.core.Particle;
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

		final Map<Particle, List<Particle>> nnl = NearNeighbourList
				.from(UniformGenerator.of(10000) // N (only used when not having a dynamic)
						.invariant(true) // true will always return the same particle set
						//.spy(p -> System.out.println(p)) // for debugging purposes
						.maxRadius(0.0) // RADIO PARTICULA
						.over(1.0) // L
						.build())
				.with(/*new BruteForce()*/CellIndexMethod // METHOD
						//.by(OptimalGrid.DENSITY_BASED) // TO DO: only for CellIndexMethod
						.by(400) // M (only for CellIndexMethod)
						.build())
				.over(SquareSpace.of(1.0) // L
						.periodicBoundary(true) // borde o no
						.build())
				.interactionRadius(0.3) // RC
				.cluster();

		System.out.println(
				"\n\tTime: " + 1E-9*(System.nanoTime() - start) + " sec.");
		
		System.out.println(nnl);
	}
}