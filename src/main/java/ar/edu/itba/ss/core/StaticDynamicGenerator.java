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
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

import ar.edu.itba.ss.core.UniformGenerator.Builder;
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
			
				while(dynamicRead.hasNext() && staticRead.hasNext()){
					particles.add(new Particle(
							radius = Double.parseDouble(staticRead.next()),
							Double.parseDouble(dynamicRead.next()),
							Double.parseDouble(dynamicRead.next()))
							);
					
					if(radius>maxRadius) maxRadius = radius;
				}
				
				dynamicRead.close();
				staticRead.close();
				
				System.out.println(particles);
				
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
		// TODO Auto-generated method stub
		return null;
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
