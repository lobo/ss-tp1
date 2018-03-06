package ar.edu.itba.ss.core;

import java.awt.Point;

	/**
	* <p>Permite especificar direcciones de forma semántica, trasladar
	* coordenadas fácilmente dentro de un espacio cuadriculado y limitado,
	* inclusive de contorno periódico.</p>
	*/

public enum Direction {

	UP(0, 1),
	UP_LEFT(-1, 1),
	UP_RIGHT(1, 1),

	DOWN(0, -1),
	DOWN_LEFT(-1, -1),
	DOWN_RIGHT(1, -1),

	NONE(0, 0),
	LEFT(-1, 0),
	RIGHT(1, 0);

	private final int x;
	private final int y;

	private Direction(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Point add(final Point point, final int wrap) {
		final Point p = new Point(point);
		p.translate(x, y);
		if (p.x < 0 || wrap <= p.x ||
			p.y < 0 || wrap <= p.y) return null;
		return p;
	}

	public Point wrapAdd(final Point point, final int wrap) {
		final Point p = new Point(point);
		p.translate(x, y);
		if (p.x < 0) p.x = wrap - 1;
		else if (wrap <= p.x) p.x = 0;
		if (p.y < 0) p.y = wrap - 1;
		else if (wrap <= p.y) p.y = 0;
		return p;
	}
}