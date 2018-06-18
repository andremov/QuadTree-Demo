/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public abstract class Node {

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	private float x;
	private float y;
	
	public abstract BufferedImage getImage();
	public abstract float distanceTo(Point trigger);
	public abstract boolean isContained(Point p1, Point p2);

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * @return true if object is Region
	 */
	public boolean isRegion() {
		return (this instanceof Region);
	}
	
	/**
	 * @return true if object is Point
	 */
	public boolean isPoint() {
		return (this instanceof Point);
	}
	
}
