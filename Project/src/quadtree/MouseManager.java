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
	public void mouseClicked(MouseEvent e) {
		Handler.addPoint(e.getX(), e.getY());
	}
	
	
	
}
