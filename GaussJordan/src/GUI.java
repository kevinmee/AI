/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinmee
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GUI extends JPanel {

	private JTextArea area;
	private JLabel label;
	private JButton button;

//-----------------------------------------------------------------
// Sets up the panel, including the timer for the animation.
//-----------------------------------------------------------------
    public GUI() {
    	area = new JTextArea(50,50);
    	label = new JLabel("<html>Enter your matrix in the following form: <br><br>col1row1 , col2row1, col3row1 <br>col1row2 , col2row2, col3row2</html>");
    	button = new JButton("Compute!");
    	
    	add(label);
    	add(area);
    	add(button);
    	

    }

    @Override
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
    }
//*****************************************************************
// Represents the action listener for the timer.
//*****************************************************************

    private class ReboundListener implements ActionListener {
//-----------------------------------------------------------------
// Updates the position of the image and possibly the direction
// of movement whenever the timer fires an action event.
//-----------------------------------------------------------------

        public void actionPerformed(ActionEvent event) {
            repaint();
        }
    }

    private class CoordinatesListener implements MouseListener {

        public void mousePressed(MouseEvent event) {
        }

        public void mouseClicked(MouseEvent event) {  
        }

        public void mouseReleased(MouseEvent event) {
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }
    }
}
