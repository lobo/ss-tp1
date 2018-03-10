package ar.edu.itba.ss.core;

import static java.util.stream.Collectors.toList;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

import ar.edu.itba.ss.core.UniformGenerator.Builder;
import ar.edu.itba.ss.core.interfaces.ParticleGenerator;

public class StaticGenerator implements ParticleGenerator{

	private int size;	// N
	private double ele;	// L 
	private double maxRadius;
	List<Particle> particles;

	public StaticGenerator(final String staticFilename, final String dynamicFilename) throws IOException {
		final Pattern pattern = Pattern.compile("\\s+");
		
		// --- Static file --- 
		List<String> staticElements = Files.lines(Paths.get(staticFilename), Charset.defaultCharset())
	            .map(line -> pattern.split(line, 2)[0])
	            .collect(Collectors.toList());
	        System.out.println(staticElements);

	        
	     // get maxRadius    
		this.size = Integer.valueOf(staticElements.get(0));
		this.ele = Double.valueOf(staticElements.get(1));
		
		staticElements.remove(0);
		staticElements.remove(0);
		this.maxRadius = Double.valueOf(staticElements.get(0));
		
		List<Double> radii = new ArrayList<Double>();
		
		for (String string : staticElements) {
			if (Double.valueOf(string) > this.maxRadius) {
				this.maxRadius = Double.valueOf(string);
			}
			
			radii.add(Double.valueOf(string));
		}
		
		// --- Dynamic file --- 
		List<Point2D.Double> dynamicElements = Files.lines(Paths.get(dynamicFilename), Charset.defaultCharset())
				.skip(1)
	            .map(
	            		line -> new Point2D.Double(
	            				Double.valueOf(pattern.split(line, 2)[0]), 
	            				Double.valueOf(pattern.split(line, 2)[1])
	            				))
	            .collect(Collectors.toList());
	        System.out.println(dynamicElements);     
	        
	        
        for (int i = 0; i < radii.size(); i++) {
			particles.add(new Particle(dynamicElements.get(i).getX(), dynamicElements.get(i).getY(), radii.get(i)));
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
