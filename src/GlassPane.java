/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GlassPane extends JPanel implements AWTEventListener {

    /**
     * 
     */
    private static final long serialVersionUID = 5110857185182004819L;

    private final Window target;


    public GlassPane(Window target) {
        super(null);
        this.target = target;

    }



    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {

            MouseEvent originalEvent = (MouseEvent) event;

            MouseEvent e = originalEvent;
        if (target instanceof JDialog) {
                e = SwingUtilities.convertMouseEvent(
                        ((MouseEvent) event).getComponent(),
                        (MouseEvent) event, this);
            }


            if (e.getID() == MouseEvent.MOUSE_PRESSED) {

                Point p  = target.getLocationOnScreen();
                System.out.println(p.getX());
            }
        }

        }



}