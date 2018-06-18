/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Andres
 */
public class MouseManager extends MouseAdapter {

	@Override
	public void mouseDragged(MouseEvent e) {
		Handler.mouseDrag(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Handler.mousePress(e.getX(), e.getY());
	}
	
	
	
}
