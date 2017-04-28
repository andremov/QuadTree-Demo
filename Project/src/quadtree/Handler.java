/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

/**
 *
 * @author Andres
 */
public abstract class Handler {

	public static int SCREEN_SIZE = 600;

	public static int CANVAS_X = 8;
	public static int CANVAS_Y = 31;
	static Window window;
	static Region tree;
	
	public static void init() {
		window = new Window(SCREEN_SIZE+CANVAS_X, SCREEN_SIZE+CANVAS_Y);
		tree = new Region(0,0,0);
	}
	
	public static void addPoint(int x, int y) {
//		System.out.println("Adding point at (" + x + ", "+ y +").");
		tree.addChild(new Point(x,y));
//		printTree(tree);
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
