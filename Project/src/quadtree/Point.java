/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Point extends Node {

	public static int BORDER_WIDTH = 1;
	public static int POINT_SIZE = 5;
	private Region parent;
	private int life;
	private Color color;
        private int colorChange;
	
	public Point(float x, float y) {
		this.setX(x-(POINT_SIZE/2f));
		this.setY(y-(POINT_SIZE/2f));
		this.color = Color.white;
		this.life = 5;
                this.colorChange = 0;
	}

	public void approachX(int target) {
		if ((int)Math.abs(target - getX()) > 2) {
			if (target > getX()) {
				setX(getX()+0.25f);
			} else {
				setX(getX()-0.25f);
			}
		}
	}

	public void approachY(int target) {
		if ((int)Math.abs(target - getY()) > 2) {
			if (target > getY()) {
				setY(getY()+0.25f);
			} else {
				setY(getY()-0.25f);
			}
		}
	}
	
	@Override
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(POINT_SIZE, POINT_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,POINT_SIZE,POINT_SIZE);
		if (this.colorChange > 0) {
                    this.colorChange--;
                    g.setColor(Color.red);
                } else {
                    g.setColor(this.color);
                }
		g.fillRect(BORDER_WIDTH,BORDER_WIDTH,POINT_SIZE-(BORDER_WIDTH*2),POINT_SIZE-(BORDER_WIDTH*2));
		
		return image;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public float distanceTo(Point trigger) {
		float deltaX = Math.abs(trigger.getX() - this.getX());
		float deltaY = Math.abs(trigger.getY() - this.getY());
		return (float)Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
	}
	
	@Override
	public boolean isContained(Point p1, Point p2) {
		int minX = Integer.min((int)p1.getX(),(int)p2.getX());
		int maxX = Integer.max((int)p1.getX(),(int)p2.getX());
		int minY = Integer.min((int)p1.getY(),(int)p2.getY());
		int maxY = Integer.max((int)p1.getY(),(int)p2.getY());
		boolean inX = minX <= this.getX() && this.getX() <= maxX;
		boolean inY = minY <= this.getY() && this.getY() <= maxY;
		return inX && inY;
	}

	/**
	 * @return the parent
	 */
	public Region getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Region parent) {
		this.parent = parent;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

    /**
     * @param colorChange the colorChange to set
     */
    public void setColorChange(int colorChange) {
        this.colorChange = colorChange;
    }
}
