package ar.edu.itba.ss;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ss.core.BruteForce;
import ar.edu.itba.ss.core.CellIndexMethod;

import ar.edu.itba.ss.core.NearNeighbourList;
import ar.edu.itba.ss.core.OptimalGrid;
import ar.edu.itba.ss.core.Particle;
import ar.edu.itba.ss.core.SquareSpace;
import ar.edu.itba.ss.core.UniformGenerator;

	/**
	* <p>Punto de entrada principal de la simulación.</p>
	*/

public final class Main {
	
	private static final String HELP_TEXT = "Cell Index Method Implementation.\n" +
	
										"Arguments: \n" + 
										"* cellindexmethod <N> <R> <L> <RC> <true | false> \n" +
										"* bruteforce <N> <R> <L> <RC> <true | false> \n";
	
	enum EXIT_CODE {
		NO_ARGS(-1), // LISTO
		BAD_N_ARGUMENTS(-2),
		BAD_ARGUMENT(-3);
		
		private final int code;
		
		EXIT_CODE(final int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	private static void exit(final EXIT_CODE exitCode) {
		System.exit(exitCode.getCode());
	}

	public static void main(final String [] arguments) {
		
		if (arguments.length == 0) {
			System.out.println("[FAIL] - No arguments passed. Try 'help' for more information.");
			exit(EXIT_CODE.NO_ARGS);
		}
		
		final long start = System.nanoTime();
		
		switch (arguments[0]) {
		case "help":
			System.out.println(HELP_TEXT);
			break;
		case "cell":
			cellIndexMethod(arguments, start);
			break;
		case "brute":
			bruteForceMethod(arguments, start);
			break;
		default:
			System.out.println("[FAIL] - Invalid argument. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_ARGUMENT);
			break;
		}
		
		System.out.println("\n[DONE]");			
	}
	
	
	// Order of received parameters: <N> <R> <L> <RC> <true|false>
	private static void cellIndexMethod(String[] args, final long start) {
		if (args.length != 6) {
			System.out.println("[FAIL] - Bad number of arguments. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_N_ARGUMENTS);
		}
		
		System.out.println("Running Cell Index method...");
		final Map<Particle, List<Particle>> nnl = NearNeighbourList
				.from(UniformGenerator.of(Integer.valueOf(args[1])) // N (only used when not having a dynamic)
						.invariant(true) // true will always return the same particle set
						//.spy(p -> System.out.println(p)) // for debugging purposes
						.maxRadius(Double.valueOf(args[2])) // RADIO PARTICULA
						.over(Double.valueOf(args[3])) // L
						.build())
				.with(CellIndexMethod
						.by(OptimalGrid.DENSITY_BASED) // TO DO: only for CellIndexMethod
						//.by(4) // M (only for CellIndexMethod)
						.build())
				.over(SquareSpace.of(Double.valueOf(args[3])) // L
						.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
						.build())
				.interactionRadius(Double.valueOf(args[4])) // RC
				.cluster();

		System.out.println(
				"Execution Time: " + 1E-9*(System.nanoTime() - start) + " sec.\n");
		
		nnl.forEach((particle, neighbours) -> {

			System.out.println(
					"Particle ID: " + 
					particle.hashCode() + "\t- Position: X: " +
					particle.getX() + ";\t Y: " +
					particle.getY() + "\t - R: " +
					particle.getRadius() + "\t- Neighbours IDs: [" +
					list(neighbours) + "]");
		});
	}
	
	// Order of received parameters: <N> <R> <L> <RC> <true|false>
	private static void bruteForceMethod(String[] args, final long start) {
		if (args.length != 6) {
			System.out.println("[FAIL] - Bad number of arguments. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_N_ARGUMENTS);
		}
		
		System.out.println("Running Brute Force method...");
		final Map<Particle, List<Particle>> nnl = NearNeighbourList
				.from(UniformGenerator.of(Integer.valueOf(args[1])) // N (only used when not having a dynamic)
						.invariant(true) // true will always return the same particle set
						//.spy(p -> System.out.println(p)) // for debugging purposes
						.maxRadius(Double.valueOf(args[2])) // RADIO PARTICULA
						.over(Double.valueOf(args[3])) // L
						.build())
				.with(new BruteForce())
				.over(SquareSpace.of(Double.valueOf(args[3])) // L
						.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
						.build())
				.interactionRadius(Double.valueOf(args[4])) // RC
				.cluster();
		
			System.out.println(
					"Execution Time: " + 1E-9*(System.nanoTime() - start) + " sec.\n");
			
			nnl.forEach((particle, neighbours) -> {

				System.out.println(
						"Particle ID: " + 
						particle.hashCode() + "\t- Position: X: " +
						particle.getX() + ";\t Y: " +
						particle.getY() + "\t - R: " +
						particle.getRadius() + "\t- Neighbours IDs: [" +
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