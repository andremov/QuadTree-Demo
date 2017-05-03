/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class Display extends Canvas implements Runnable {

	public Display() {
//		setBackground(Color.black);
		addMouseListener(new MouseManager());
	}
	
	@Override
	public void paint(Graphics g) {
	}
	
	@Override
	public void run() {
		createBufferStrategy(2);
		while (true) {
			Graphics g = getBufferStrategy().getDrawGraphics();
			if (Handler.tree != null) {
				g.drawImage(Handler.tree.getImage(),0,0,null);
			}
			getBufferStrategy().show();
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				
			}
		}
	}
	
}
