/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class Handler implements Runnable {

	public static final int MODE_ADD = 0;
	public static final int MODE_REGION = 1;
	public static final int MODE_NEIGHBORS = 2;
	public static final int MODE_GAME = 3;
	
	public static int SCREEN_SIZE = 600;

	public static int CANVAS_X = 8;
	public static int CANVAS_Y = 31;
	static Window window;
	static Region tree;
	public static Point selectRegionStart;
	public static Point selectRegionEnd;
	public static Point neighborCheck;
	public static int currentMode;
	private static int paintCooldown = 10;
	private static int spawnCooldown = 0;
	private static Thread gameThread;
	public static int life;
	public static String attack;
	public static int score;

	public Handler() {
		init();
		life = 50;
		score = 0;
		gameThread = new Thread(this);
		attack = "";
	}
	
	public static void doAttack() {
		if (attack.isEmpty()) {
			for (int i = 0; i < tree.getChildren().length; i++) {
				if (tree.getChildren()[i] instanceof Point) {
					((Point) tree.getChildren()[i]).setLife(((Point) tree.getChildren()[i]).getLife()-1);
                                        ((Point) tree.getChildren()[i]).setColorChange(5);
				}
			}
		} else {
			Region attackZone = tree; 
			for (int i = 0; i < attack.split("|").length; i++) {
				int quad = (Integer.parseInt(attack.split("|")[i]))-1;
				try {
					attackZone = (Region) attackZone.getChildren()[quad];
					if (i == attack.split("|").length-1) {
						attackZone.doDamage((i+1)*2);
					}
				} catch (Exception e) { }
			}
			attack = "";
		}
	}
	
	public static void init() {
		window = new Window(SCREEN_SIZE+CANVAS_X, SCREEN_SIZE+CANVAS_Y);
		new Control();
		currentMode = MODE_ADD;
		tree = new Region(0,0,0);
	}
	
	public static void clean() {
		tree = new Region(0,0,0);
	}
	
	public static void startGame() {
		clean();
		clearColors(tree);
		newMode(MODE_GAME);
		attack = "";
		life = 50;
		score = 0;
		gameThread.start();
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
			if (paintCooldown <= 0) {
				tree.addChild(new Point(x,y));
				paintCooldown = 10;
			} else {
				paintCooldown--;
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
	
	public static void remakeTree() {
		ArrayList<Point> points = getAllPoints(tree);
		tree = new Region(0, 0, 0);
		for (int i = 0; i < points.size(); i++) {
			
			points.get(i).approachX(SCREEN_SIZE/2);
			points.get(i).approachY(SCREEN_SIZE/2);
			
			
			if (points.get(i).distanceTo(new Point(SCREEN_SIZE/2,SCREEN_SIZE/2))< 10) {
				life = life-1;
				if (life == 0) {
					newMode(MODE_ADD);
					clean();
					clearColors(tree);
				}
			} else {
				tree.addChild(points.get(i));
			}
		}
	}
	public static ArrayList<Point> getAllPoints(Node tree) {
		ArrayList<Point> points = new ArrayList<>();
		if (tree instanceof Region) {
			for (int i = 0; i < ((Region) tree).getChildren().length; i++) {
				if (((Region) tree).getChildren()[i] instanceof Point) {
					points.add( (Point) ((Region) tree).getChildren()[i] );
				} else {
					points.addAll(getAllPoints(((Region) tree).getChildren()[i]));
				}
			}
		}
		return points;
	}

	@Override
	public void run() {
		while(currentMode == MODE_GAME) {
			if (spawnCooldown <= 0) {
				int x = (int)(Math.random()*SCREEN_SIZE);
				int y;
				if (x > SCREEN_SIZE-50 || x < 50) {
					y = (int)(Math.random()*SCREEN_SIZE);
				} else {
					y = (int)(Math.random()*50);
					if (Math.random()*5 > 3.5) {
						y = y + (SCREEN_SIZE-100);
					}
				}
				tree.addChild(new Point(x,y));
				spawnCooldown = 10;
			} else {
				spawnCooldown--;
			}
			
			remakeTree();
			
			try {
				Thread.sleep(100);
			} catch (Exception e) { }
		}
	}
	
}
