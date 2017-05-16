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
public class Region extends Node {

	public static int BORDER_WIDTH = 1;
	public static int MAX_DEPTH = 5;
	
	private final Node[] children;
	private final float width;
	private final float height;
	private final float midPointX;
	private final float midPointY;
	private final int depth;

	public Region(int regionX, int regionY, int depth) {
		this.children = new Node[4];
		this.depth = depth;
		
		int maxWidth = (Handler.SCREEN_SIZE);
		int maxHeight = (Handler.SCREEN_SIZE);
		
		this.width = (float)(maxWidth/(Math.pow(2, depth)));
		this.height = (float)(maxHeight/(Math.pow(2, depth)));
		
		this.setX(regionX*this.width);
		this.setY(regionY*this.height);
		
		this.midPointX = this.getX() + (this.width/2);
		this.midPointY = this.getY() + (this.height/2);
	}
	
	public void doDamage(int damage) {
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Point) {
				Handler.score = Handler.score + (int)(Math.pow(damage,2));
				((Point)children[i]).setLife(((Point)children[i]).getLife()-damage);
				if (((Point)children[i]).getLife() <= 0) {
					children[i] = null;
					Handler.score = Handler.score + 10;
				}
			} else if (children[i] instanceof Region) {
				((Region) children[i]).doDamage(damage);
			}
		}
	}
	
	/**
	 * @return the description of children
	 */
	public String getChildrenDescription() {
		String description = "";
		for (int i = 0; i < getChildren().length; i++) {
			if (getChildren()[i] == null) {
				description = description + "null";
			} else {
				description = description + getChildren()[i].toString();
			}
			if (i+1 == getChildren().length) {
				description = description + ".";
			} else {
				description = description + ", ";
			}
		}
		return description;
	}
	
	public void addChild(Point child) {
		int childNum = 0;
		
		if (child.getX() > this.getMidPointX()) {
			childNum++;
		}
		
		if (child.getY() > this.getMidPointY()) {
			childNum++;
			childNum++;
		}
		
		if (getChildren()[childNum] == null) {
			children[childNum] = child;
		} else if (getDepth()+1 <= MAX_DEPTH) {
			if (getChildren()[childNum] instanceof Point) {
				Point oldChild = (Point)getChildren()[childNum];
				int regionX = (int)((childNum%2) + (this.getX()*2/this.getWidth()));
				int regionY = (int)((childNum/2) + (this.getY()*2/this.getHeight()));

				children[childNum] = new Region(regionX, regionY, getDepth()+1);
				((Region) getChildren()[childNum]).addChild(oldChild);
			}
			((Region) getChildren()[childNum]).addChild(child);
		}
	}
	
	@Override
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage((int)this.getWidth(), (int)this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		if (!(Handler.currentMode == Handler.MODE_GAME)) {
			g.setColor(Color.white);
			g.drawRect(0,0, (int)this.getWidth()-1, (int)this.getHeight()-1);
		}
		
//		g.setColor(Color.black);
//		int finalWidth = (int)(this.getWidth()-(BORDER_WIDTH*2));
//		int finalHeight = (int)(this.getWidth()-(BORDER_WIDTH*2));
//		g.fillRect(BORDER_WIDTH,BORDER_WIDTH,finalWidth,finalHeight);
		
		for (int i = 0; i < getChildren().length; i++) {
			if (getChildren()[i] != null) {
				int x = (int)(getChildren()[i].getX()-this.getX());
				int y = (int)(getChildren()[i].getY()-this.getY());
				g.drawImage(getChildren()[i].getImage(),x,y,null);
			}
		}
		
		return image;		
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return the children
	 */
	public Node[] getChildren() {
		return children;
	}

	/**
	 * @return the midPointX
	 */
	public float getMidPointX() {
		return midPointX;
	}

	/**
	 * @return the midPointY
	 */
	public float getMidPointY() {
		return midPointY;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public float distanceTo(Point trigger) {
		float x = trigger.getX();
		float y = trigger.getY();
		if (x < this.getX()) {
			x = this.getX();
		} else if (this.getX() +this.getWidth() < x){
			x = this.getX() + this.getWidth();
		}
		if (y < this.getY()) {
			y = this.getY();
		} else if (this.getY() +this.getHeight() < y){
			y = this.getY() + this.getHeight();
		}
		return (new Point(x,y)).distanceTo(trigger);
	}
	
	@Override
	public boolean isContained(Point p1, Point p2) {
		int minX = Integer.min((int)p1.getX(),(int)p2.getX());
		int maxX = Integer.max((int)p1.getX(),(int)p2.getX());
		int minY = Integer.min((int)p1.getY(),(int)p2.getY());
		int maxY = Integer.max((int)p1.getY(),(int)p2.getY());
		
		boolean top = maxY > this.getY();
		boolean bottom = minY < this.getY() + this.getHeight();
		boolean left = maxX > this.getX();
		boolean right = minX < this.getX() + this.getWidth();
		return top && bottom && left && right;
	}
}
