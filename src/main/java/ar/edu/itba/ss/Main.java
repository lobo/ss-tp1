/**
* <p>Punto de entrada principal de la simulación.</p>
*/

public final class Main {
		
	public static void generateOvitoInput(int N, double L, double R) {
		System.out.println(N);
		System.out.println("Uniform random particles");
		for (int i = 0; i < N; i++) {
			System.out
					.println((i + 1) + "\t" + Math.random() * L + "\t" + Math.random() * L + "\t" 
					+ Math.random() * R);
		}
	}

	public static void main(final String [] arguments) {
		String runningOption = arguments[0];
		
		if (runningOption.equals("generate")) {
			try {
				
				int N = Integer.valueOf(arguments[1]);
				double L = Double.valueOf(arguments[2]);
				double R = Double.valueOf(arguments[3]);
				
				generateOvitoInput(N, L, R);
				
			} catch(Exception e) {
				System.err.println("Wrong parameters");
			}
		} else if (runningOption.equals("find")) {
			try {
				
			} catch(Exception e) {
				System.err.println("Wrong parameters");
			}
		} else {
			System.err.println("Must provide a valid instruction: 'generate' or 'find'");
		}

		System.out.println("(2018) Cell Index Method.");
	}
}
