/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Andres
 */
public class Control extends JFrame {
	
	JButton addPointMode;
	JButton addRandomPoints;
	JButton regionSearchMode;
	JButton neighborsSearchMode;
	JButton cleanBtn;
	JButton playMode;
	
	
	public Control() {
        setLayout(null);
        setSize(195, 460);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Control");
		
		init();
		
        setVisible(true);
	}
	
	private void init() {
		int y = 10;
		addPointMode = new JButton("Agregar Puntos");
		addPointMode.setSize(180, 60);
		addPointMode.setLocation(5, y);
		addPointMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.newMode(Handler.MODE_ADD);
			}
		});
		add(addPointMode);
		
		y = y + 70;
		regionSearchMode = new JButton("Busqueda Region");
		regionSearchMode.setSize(180, 60);
		regionSearchMode.setLocation(5, y);
		regionSearchMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.newMode(Handler.MODE_REGION);
			}
		});
		add(regionSearchMode);
		
		y = y + 70;
		neighborsSearchMode = new JButton("Busqueda Vecino");
		neighborsSearchMode.setSize(180, 60);
		neighborsSearchMode.setLocation(5, y);
		neighborsSearchMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.newMode(Handler.MODE_NEIGHBORS);
			}
		});
		add(neighborsSearchMode);
		
		y = y + 70;
		addRandomPoints = new JButton("Agregar 100 Puntos");
		addRandomPoints.setSize(180, 60);
		addRandomPoints.setLocation(5, y);
		addRandomPoints.setSize(180, 60);
		addRandomPoints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.addRandomPoints(100);
			}
		});
		add(addRandomPoints);
		
		y = y + 70;
		cleanBtn = new JButton("Limpiar");
		cleanBtn.setSize(180, 60);
		cleanBtn.setLocation(5, y);
		cleanBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.clean();
			}
		});
		add(cleanBtn);
		
		y = y + 70;
		playMode = new JButton("Jugar");
		playMode.setSize(180, 60);
		playMode.setLocation(5, y);
		playMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Handler.startGame();
			}
		});
		add(playMode);
	}
	
}
