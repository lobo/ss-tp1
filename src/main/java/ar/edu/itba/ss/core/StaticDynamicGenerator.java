package ar.edu.itba.ss.core;

import static java.util.stream.Collectors.toList;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

import ar.edu.itba.ss.core.UniformGenerator.Builder;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;

public class StaticGenerator implements ParticleGenerator{

	private Integer size;	// N
	private Integer ele;		// L 
	private Double maxRadius;
	List<Particle> particles;
	
	enum EXIT_CODE {
		NOT_A_FILE(-1), 
		UNEXPECTED_ERROR(-2),
		BAD_FILE_FORMAT(-3);
		
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

	public StaticGenerator(final String staticFilename, final String dynamicFilename) throws IOException {
		
		// --- STATIC FILE ---
		
        final File staticFile = new File(staticFilename);
        if (!staticFile.isFile()) {
            System.out.println("[FAIL] - File '" + staticFilename + "' is not a normal file. Aborting...");
            exit(EXIT_CODE.NOT_A_FILE);
        }

        try (final Stream<String> staticStream = Files.lines(staticFile.toPath())) {
            final Iterator<String> staticFileLines = staticStream.iterator();

            // get N
            this.size = Integer.valueOf(staticFileLines.next());
            System.out.println(size);

            // get L
            this.ele = Integer.valueOf(staticFileLines.next());
            System.out.println(ele);

            // get radios   		
	    		List<Double> radii = new ArrayList<Double>();
	    		
			String currentLine;
			Double cRadio;
	        for (int i = 0 ; i < this.size ; i++) {
	            currentLine = staticFileLines.next(); // caught runtime exception
	            cRadio = Double.valueOf(currentLine.split(" ")[0]); // at least it should have one component
	            radii.add(Double.valueOf(cRadio));
	            if (i == 0) {
					this.maxRadius = cRadio;
				} else if(cRadio > this.maxRadius){
					this.maxRadius = cRadio;
				}
	        }
	        
	        // Debugging sysos
	        System.out.println("Information from the Static file: ");
	        System.out.println("* N: " + size.toString());
	        System.out.println("* L: " + ele.toString());
	        System.out.println("* MaxRadius: " + maxRadius.toString());
	        System.out.println("* Radii: " + radii);

        } catch (final IOException e) {
        		System.out.println("An unexpected IO Exception occurred while reading the file {}. Caused by: " + staticFile + e);
        		System.out.println("[FAIL] - An unexpected error occurred while reading the file '" + staticFile + "'. \n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.UNEXPECTED_ERROR);
        } catch (final NumberFormatException e) {
        		System.out.println("[FAIL] - Number expected. Caused by: " + e);
        		System.out.println("[FAIL] - Bad format of file '" + staticFile + "'.\n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.BAD_FILE_FORMAT);
        } catch (final NoSuchElementException e) {
        		System.out.println("[FAIL] - Particle Expected. Caused by: " + e);
        		System.out.println("[FAIL] - Bad format of file '" + staticFile + "'.\n" +
                    "Particle information expected: N is greater than the # of lines with particle information.\n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.BAD_FILE_FORMAT);
        }
        
        
        // --- DYNAMIC FILE ---
        
        final File dynamicFile = new File(dynamicFilename);
        if (!dynamicFile.isFile()) {
            System.out.println("[FAIL] - File '" + dynamicFilename + "' is not a normal file. Aborting...");
            exit(EXIT_CODE.NOT_A_FILE);
        }
        
        try (final Stream<String> staticStream = Files.lines(dynamicFile.toPath())) {
            final Iterator<String> dynamicFileLines = staticStream.iterator();

            dynamicFileLines.next();

            // get radios   		
            List<Point2D.Double> particlePositions = new ArrayList<Point2D.Double>();
	    		
			String currentLine;
			Double cRadio;
	        for (int i = 0 ; i < this.size ; i++) {
	            currentLine = dynamicFileLines.next(); // caught runtime exception
	            Double x = Double.valueOf(currentLine.split(" ")[0]); // at least it should have one component
	            Double y = Double.valueOf(currentLine.split(" ")[1]); // at least it should have one component
	            particlePositions.add(new Point2D.Double(x,y));
	        }
	        
	        // Debugging sysos
	        System.out.println("Information from the Dynamic file: ");
	        System.out.println("* Particles: " + particlePositions);

        } catch (final IOException e) {
        		System.out.println("An unexpected IO Exception occurred while reading the file {}. Caused by: " + dynamicFile + e);
        		System.out.println("[FAIL] - An unexpected error occurred while reading the file '" + dynamicFile + "'. \n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.UNEXPECTED_ERROR);
        } catch (final NumberFormatException e) {
        		System.out.println("[FAIL] - Number expected. Caused by: " + e);
        		System.out.println("[FAIL] - Bad format of file '" + dynamicFile + "'.\n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.BAD_FILE_FORMAT);
        } catch (final NoSuchElementException e) {
        		System.out.println("[FAIL] - Particle Expected. Caused by: " + e);
        		System.out.println("[FAIL] - Bad format of file '" + dynamicFile + "'.\n" +
                    "Particle information expected: N is greater than the # of lines with particle information.\n" +
                    "Check the logs for more info.\n" +
                    "Aborting...");
            exit(EXIT_CODE.BAD_FILE_FORMAT);
        }        
	}

	@Override
	public Stream<Particle> generate() {
		return particles.stream();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public double maxRadius() {
		return maxRadius;
	}

}
