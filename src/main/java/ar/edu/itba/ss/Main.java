package ar.edu.itba.ss;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ss.core.BruteForce;
import ar.edu.itba.ss.core.CellIndexMethod;

import ar.edu.itba.ss.core.NearNeighbourList;
import ar.edu.itba.ss.core.OptimalGrid;
import ar.edu.itba.ss.core.Particle;
import ar.edu.itba.ss.core.SquareSpace;
import ar.edu.itba.ss.core.StaticDynamicGenerator;
import ar.edu.itba.ss.core.UniformGenerator;

	/**
	* <p>Punto de entrada principal de la simulación.</p>
	*/

public final class Main {
	
	private static final String HELP_TEXT = "Cell Index Method Implementation.\n" +
	
										"Arguments: \n" + 
										"* cell <N> <R> <L> <RC> <true | false> <M> <filename> \n" +
										"* cellfile <staticFile> <dynamicFile> <RC> <true | false> <M> <filename> \n" +
										"* brute <N> <R> <L> <RC> <true | false> <filename> \n" +
										"* brutefile <staticFile> <dynamicFile> <RC> <true | false> <filename> \n";
	
	enum EXIT_CODE {
		NO_ARGS(-1), 
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

	public static void main(final String [] args) throws NumberFormatException, IOException {		
		if (args.length == 0) {
			System.out.println("[FAIL] - No arguments passed. Try 'help' for more information.");
			exit(EXIT_CODE.NO_ARGS);
		}
		
		final long start = System.nanoTime();
		
		switch (args[0]) {
			case "help":
				System.out.println(HELP_TEXT);
				break;
			case "cell":
				// cell <N> <R> <L> <RC> <true | false> <M> <filename>
				cellIndexMethod(args, start);
				break;
			case "brute":
				// brute <N> <R> <L> <RC> <true | false> <filename>
				bruteForceMethod(args, start);
				break;
			case "cellfile":
				// cellfile <staticFile> <dynamicFile> <L> <RC> <true | false> <M> <filename>
				cellIndexMethodFile(args, start);
				break;
			case "brutefile":
				// brutefile <staticFile> <dynamicFile> <L> <RC> <true | false> <M> <filename>
				bruteForceMethodFile(args, start);
				break;
			default:
				System.out.println("[FAIL] - Invalid argument. Try 'help' for more information.");
				exit(EXIT_CODE.BAD_ARGUMENT);
				break;
		}

		printExecutionTime(start);		
	}
	
	// cellfile <staticFile> <dynamicFile> <RC> <true | false> <M> <filename>
	// READY
	private static void cellIndexMethodFile(String[] args, final long start) throws NumberFormatException, IOException {
		if (args.length != 7 ) {
			System.out.println("[FAIL] - Bad number of arguments. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_N_ARGUMENTS);
		}
		
		// if M is not given by the user
		if ((int)Integer.valueOf(args[5]) == 0) {
			System.out.println("Running Cell Index method...");
			StaticDynamicGenerator generator = new StaticDynamicGenerator(args[1], args[2]);
			final Map<Particle, List<Particle>> nnl = NearNeighbourList
					.from(generator)
					.with(CellIndexMethod
							.by(OptimalGrid.AUTOMATIC)
							.build())
					.over(SquareSpace.of(generator.getL()) 
							.periodicBoundary(Boolean.valueOf(args[4])) // include border or not
							.build())
					.interactionRadius(Double.valueOf(args[3])) // RC
					.cluster();
			
			smartLogging(nnl, start, args[6], args[3], "");
			
		} else {
			System.out.println("Running Cell Index method...");
			StaticDynamicGenerator generator = new StaticDynamicGenerator(args[1], args[2]);
			final Map<Particle, List<Particle>> nnl = NearNeighbourList
					.from(generator)
					.with(CellIndexMethod
							.by(Integer.valueOf(args[7])) // M
							.build())
					.over(SquareSpace.of(generator.getL()) 
							.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
							.build())
					.interactionRadius(Double.valueOf(args[4])) // RC
					.cluster();
			
			smartLogging(nnl, start, args[7], args[3], "");
		}
	}
	
	
	// cell <N> <R> <L> <RC> <true | false> <M> <filename>
	//	READY
	private static void cellIndexMethod(String[] args, final long start) throws FileNotFoundException {
		if (args.length != 8 ) {
			System.out.println("[FAIL] - Bad number of arguments. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_N_ARGUMENTS);
		}
		
		// if M is not given by the user
		if (Integer.valueOf(args[6]).equals(0)) {
			System.out.println("Running Cell Index method...");
			final Map<Particle, List<Particle>> nnl = NearNeighbourList
					.from(UniformGenerator.of(Integer.valueOf(args[1])) // N 
							.invariant(true) // true will always return the same particle set
							//.spy(p -> System.out.println(p)) // for debugging purposes
							.maxRadius(Double.valueOf(args[2])) // particle radius
							.over(Double.valueOf(args[3])) // L
							.build())
					.with(CellIndexMethod
							.by(OptimalGrid.AUTOMATIC)
							.build())
					.over(SquareSpace.of(Double.valueOf(args[3])) // L
							.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
							.build())
					.interactionRadius(Double.valueOf(args[4])) // RC
					.cluster();
			
			smartLogging(nnl, start, args[7], args[3], args[2]);			
		} else {
			System.out.println("Running Cell Index method...");
			final Map<Particle, List<Particle>> nnl = NearNeighbourList
					.from(UniformGenerator.of(Integer.valueOf(args[1])) // N (only used when not having a dynamic)
							.invariant(true) // true will always return the same particle set
							//.spy(p -> System.out.println(p)) // for debugging purposes
							.maxRadius(Double.valueOf(args[2])) // RADIO PARTICULA
							.over(Double.valueOf(args[3])) // L
							.build())
					.with(CellIndexMethod
							.by(Integer.valueOf(args[6])) 
							.build())
					.over(SquareSpace.of(Double.valueOf(args[3])) // L
							.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
							.build())
					.interactionRadius(Double.valueOf(args[4])) // RC
					.cluster();
			
			smartLogging(nnl, start, args[7], args[3], args[2]);
		}	
	}
	
	// brute <N> <R> <L> <RC> <true | false> <filename>
	// READY
	private static void bruteForceMethod(String[] args, final long start) throws FileNotFoundException {
		if (args.length != 7) {
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
		
		smartLogging(nnl, start, args[6], args[3], args[2]);			
	}
	
	// brutefile <staticFile> <dynamicFile> <RC> <true | false> <filename>
	// READY
	private static void bruteForceMethodFile(String[] args, final long start) throws NumberFormatException, IOException {
		if (args.length != 8) {
			System.out.println("[FAIL] - Bad number of arguments. Try 'help' for more information.");
			exit(EXIT_CODE.BAD_N_ARGUMENTS);
		}
		
		System.out.println("Running Brute Force method...");
		StaticDynamicGenerator generator = new StaticDynamicGenerator(args[1], args[2]);
		final Map<Particle, List<Particle>> nnl = NearNeighbourList
				.from(generator)
				.with(new BruteForce())
				.over(SquareSpace.of(generator.getL()) 
						.periodicBoundary(Boolean.valueOf(args[5])) // include border or not
						.build())
				.interactionRadius(Double.valueOf(args[4])) // RC
				.cluster();
		
		smartLogging(nnl, start, args[6], args[3],"");
	}
	
	private static void consoleLogging(final Map<Particle, List<Particle>> nnl, final long start) {		
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
	
	private static void fileLogging(final Map<Particle, List<Particle>> nnl, final long start, final String output_filename, final String L, final String Rc) throws FileNotFoundException {
		System.out.println("The output has been written into a file.");
		final String filename = "./" + output_filename + ".txt";
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		
		System.out.println(L + " " + Rc);
		nnl.forEach((particle, neighbours) -> {
			System.out.println( 
					particle.hashCode() + " " +
					particle.getX() + " " + 
					particle.getY() + " " +
					particle.getRadius() + " " + 
					list(neighbours)
					);
		});
	}
	
	private static void smartLogging(Map<Particle, List<Particle>> nnl, final long start, String filename, final String L, final String Rc) throws FileNotFoundException {
		if (filename.equals("null")) {
			consoleLogging(nnl, start);
			
		} else {
			fileLogging(nnl, start, filename, L, Rc);
		}
	}
	
	private static void printExecutionTime(final long start) {
		PrintStream consoleStream = new PrintStream(
                new FileOutputStream(FileDescriptor.out));
				
		System.setOut(consoleStream);
		System.out.println(
				"Execution Time: " + 1E-9*(System.nanoTime() - start) + " sec.\n");
		System.out.println("\n[DONE]");	
	}
	
	
	private static String list(final List<Particle> neighbours) {
		String list = "";
		for (final Particle particle : neighbours) {
			list += particle.hashCode() + " ";
		}
		if (!neighbours.isEmpty()) {
			return list.substring(0, list.length() - 2);
		}
		else return list;
	}
}