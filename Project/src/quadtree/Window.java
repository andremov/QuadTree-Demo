/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import javax.swing.JFrame;

/**
 *
 * @author Andres
 */
public class Window extends JFrame {
	
	public Window(int width, int height) {
        setLayout(null);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("QuadTree");
		
		Display screen = new Display();
		screen.setSize(width-Handler.CANVAS_X, height-Handler.CANVAS_Y);
		screen.setLocation(1,1);
		add(screen);
		
        setVisible(true);
		
        new Thread(screen).start();
	}
	
}
	