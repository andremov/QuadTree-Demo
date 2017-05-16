/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_1) {
					Handler.attack = Handler.attack + "1";
				} else if (e.getKeyCode() == KeyEvent.VK_2) {
					Handler.attack = Handler.attack + "2";
				} else if (e.getKeyCode() == KeyEvent.VK_3) {
					Handler.attack = Handler.attack + "3";
				} else if (e.getKeyCode() == KeyEvent.VK_4) {
					Handler.attack = Handler.attack + "4";
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Handler.doAttack();
				}
			}
			
});
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
					int x1 = (int)Handler.selectRegionStart.getX();
					int y1 = (int)Handler.selectRegionStart.getY();
					int x2 = (int)Handler.selectRegionEnd.getX();
					int y2 = (int)Handler.selectRegionEnd.getY();
					
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
			
			if (Handler.currentMode == Handler.MODE_GAME) {
				g.setColor(Color.red);
				g.fillRect(0,0,(int)(getWidth()*(Handler.life/50f)),20);
				
				g.setColor(Color.green);
				g.setFont(new Font("Arial",Font.BOLD,30));
				g.drawString(Handler.attack,Handler.SCREEN_SIZE/2+20,getHeight()-10);
				
				g.setColor(Color.green);
				g.setFont(new Font("Arial",Font.BOLD,13));
				g.drawString("Score: "+Handler.score,10,getHeight()-10);
				
				g.setColor(Color.white);
				g.fillRect((getWidth()/2)-10,(getHeight()/2)-10,20,20);
			}
			
			if (Handler.currentMode == Handler.MODE_NEIGHBORS) {
				try {
					java.awt.image.BufferedImage img = Handler.neighborCheck.getImage();
					int x = (int)Handler.neighborCheck.getX();
					int y = (int)Handler.neighborCheck.getY();
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
