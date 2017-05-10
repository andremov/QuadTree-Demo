/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class Display extends Canvas implements Runnable {

	public Display() {
//		setBackground(Color.black);
		java.awt.event.MouseAdapter m = new MouseManager();
		addMouseListener(m);
		addMouseMotionListener(m);
		setBackground(Color.black);
	}
	
	@Override
	public void paint(Graphics g) {
	}
	
	@Override
	public void run() {
		createBufferStrategy(2);
		while (true) {
			Graphics g = getBufferStrategy().getDrawGraphics();
			g.clearRect(0, 0, getWidth(), getHeight());
			if (Handler.currentMode == Handler.MODE_REGION) {
				try {
					int x1 = (int)Handler.selectRegionStart.x;
					int y1 = (int)Handler.selectRegionStart.y;
					int x2 = (int)Handler.selectRegionEnd.x;
					int y2 = (int)Handler.selectRegionEnd.y;
					
					g.setColor(Color.DARK_GRAY);
					g.fillRect(Integer.min(x1, x2), Integer.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));

					g.setColor(Color.GRAY);
					g.drawRect(Integer.min(x1, x2), Integer.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));

				} catch(Exception e) {
					
				}
			}
			
			if (Handler.tree != null) {
				g.drawImage(Handler.tree.getImage(),0,0,null);
			}
			
			if (Handler.currentMode == Handler.MODE_NEIGHBORS) {
				try {
					java.awt.image.BufferedImage img = Handler.neighborCheck.getImage();
					int x = (int)Handler.neighborCheck.x;
					int y = (int)Handler.neighborCheck.y;
					g.drawImage(img, x, y, null);
				} catch (Exception e) {
					
				}
			}
			
			getBufferStrategy().show();
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				
			}
		}
	}
	
}
