package ar.edu.itba.ss.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import ar.edu.itba.ss.core.interfaces.ParticleGenerator;

public class StaticDynamicGenerator implements ParticleGenerator{
	
	private static double maxRadius = 0;
	private static int N = 0;
	private static int L = 0;
	static List<Particle> particles = new ArrayList<Particle>();
	
	public StaticDynamicGenerator (String staticFilePath, String dynamicFilePath) {

		try {
			
			File dynamicFIle = new File(dynamicFilePath);
			File staticFile = new File(staticFilePath);
			
			try { 
				double radius;
				Scanner dynamicRead = new Scanner(dynamicFIle);
				Scanner staticRead = new Scanner(staticFile);
				
				N = Integer.parseInt(staticRead.next());
				L = Integer.parseInt(staticRead.next());
				
				dynamicRead.next(); // avoid t0 in Dynamic File
			
				while(dynamicRead.hasNext() && staticRead.hasNext()){
					particles.add(new Particle(
							Double.parseDouble(dynamicRead.next()),
							Double.parseDouble(dynamicRead.next()),
							radius = Double.parseDouble(staticRead.next())
							));
					
					staticRead.next(); // avoid the second column in static file
					if(radius>maxRadius) maxRadius = radius;
				}
				
				dynamicRead.close();
				staticRead.close();				
			} catch (Exception e) {
				System.out.println("Error scanning file");
			}
			
		} catch (Exception e) {
			System.out.println("Error opening or finding file");
		}
		
	}
	
	public int getL() {
		return L;
	}
	
	@Override
	public Stream<Particle> generate() {
		return particles.stream();
	}

	@Override
	public int size() {
		return N;
	}

	@Override
	public double maxRadius() {
		return maxRadius;
	}

	

}
