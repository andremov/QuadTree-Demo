/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Color;

/**
 *
 * @author Andres
 */
public abstract class Handler {

	public static final int MODE_ADD = 0;
	public static final int MODE_REGION = 1;
	public static final int MODE_NEIGHBORS = 2;
	
	public static int SCREEN_SIZE = 600;

	public static int CANVAS_X = 8;
	public static int CANVAS_Y = 31;
	static Window window;
	static Region tree;
	public static Point selectRegionStart;
	public static Point selectRegionEnd;
	public static Point neighborCheck;
	public static int currentMode;
	private static int cooldown = 10;
	
	public static void init() {
		window = new Window(SCREEN_SIZE+CANVAS_X, SCREEN_SIZE+CANVAS_Y);
		new Control();
		currentMode = MODE_ADD;
		tree = new Region(0,0,0);
	}
	
	public static void clean() {
		tree = new Region(0,0,0);
	}
	
	public static void addRandomPoints(int numPoints) {
		neighborCheck = null;
		selectRegionEnd = null;
		selectRegionStart = null;
		clearColors(tree);
		java.util.Random rnd = new java.util.Random();
		for (int i = 0; i < numPoints; i++) {
			int x = rnd.nextInt(SCREEN_SIZE);
			int y = rnd.nextInt(SCREEN_SIZE);
			tree.addChild(new Point(x,y));
		}
	}
	
	private static void clearColors(Node search) {
		if (search instanceof Point) {
			((Point) search).setColor(Color.white);
		} else if (search instanceof Region){
			for (int i = 0; i < ((Region) search).getChildren().length; i++) {
				if (((Region) search).getChildren()[i] != null) {
					clearColors(((Region) search).getChildren()[i]);
				}
			}
		}
	}
	
	public static void newMode(int newMode) {
		currentMode = newMode;
		neighborCheck = null;
		selectRegionEnd = null;
		selectRegionStart = null;
		clearColors(tree);
	}
	
	public static void mousePress(int x, int y) {
		if (currentMode == MODE_ADD) {
			tree.addChild(new Point(x,y));
		} else if (currentMode == MODE_REGION) {
			selectRegionEnd = null;
			selectRegionStart = new Point(x, y);
			clearColors(tree);
		} else if (currentMode == MODE_NEIGHBORS) {
			neighborCheck = new Point(x,y);
			neighborCheck.setColor(Color.red);
			clearColors(tree);
			try {
				nearestNeighbor(tree,0).setColor(Color.green);
			}catch(Exception e) {
				
			}
		}
	}
	
	public static void mouseDrag(int x, int y) {
		if (currentMode == MODE_REGION) {
			selectRegionEnd = new Point(x, y);
			clearColors(tree);
			regionCheck(tree);
		} else if (currentMode == MODE_ADD) {
			if (cooldown <= 0) {
				tree.addChild(new Point(x,y));
				cooldown = 10;
			} else {
				cooldown--;
			}
		}
	}
	
	private static Point nearestNeighbor(Node search, int depth) {
		Point bestPoint = null;
		if (search instanceof Point) {
			((Point) search).setColor(Color.blue);
			bestPoint = (Point)search;
		} else if (search instanceof Region){
			if (search.distanceTo(neighborCheck) < SCREEN_SIZE/Math.pow(2,depth)) {
				Point thisRegionBest = null;
				for (int i = 0; i < ((Region) search).getChildren().length; i++) {
					if (((Region) search).getChildren()[i] != null) {
						Point temp = nearestNeighbor(((Region) search).getChildren()[i],depth+1);
						if (thisRegionBest == null) {
							thisRegionBest = temp;
						} else if (temp != null) {
							if (temp.distanceTo(neighborCheck) < thisRegionBest.distanceTo(neighborCheck)) {
								thisRegionBest = temp;
							}
						}
					}
				}
				bestPoint = thisRegionBest;
			}
		}
		return bestPoint;
	}
	
	private static void regionCheck(Node search) {
		if (search.isContained(selectRegionStart, selectRegionEnd)) {
			if (search instanceof Point) {
				((Point) search).setColor(Color.green);
			} else if (search instanceof Region){
				for (int i = 0; i < ((Region) search).getChildren().length; i++) {
					if (((Region) search).getChildren()[i] != null) {
						regionCheck(((Region) search).getChildren()[i]);
					}
				}
			}
		} else {
			if (search instanceof Point) {
				((Point) search).setColor(Color.blue);
			}
		}
	}
	
	public static void printTree(Region node) {
		System.out.println(node.getChildrenDescription());
		for (int i = 0; i < node.getChildren().length; i++) {
			if (node.getChildren()[i] instanceof Region) {
				printTree((Region)node.getChildren()[i]);
			}
		}
	}
	
}
