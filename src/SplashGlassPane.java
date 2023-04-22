/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Glass pane used to blur the content of the window.
 *
 * @author SMedvynskyy
 */
@SuppressWarnings("serial")
public class SplashGlassPane extends JPanel implements FocusListener {

    /**
     * Creates new GlassPane.
     */
    public SplashGlassPane() {
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseAdapter() {});
        addFocusListener(this);
        setOpaque(false);
        setFocusable(true);
        setBackground(new Color(0, 0, 0, 190));
    }

    @Override
    public final void setVisible(boolean v) {
        // Make sure we grab the focus so that key events don't go astray.
        if (v) {
            requestFocus();
        }
        super.setVisible(v);
    }

    // Once we have focus, keep it if we're visible
    @Override
    public final void focusLost(FocusEvent fe) {
        if (isVisible()) {
            requestFocus();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void paint(Graphics g) {
        final Color old = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
        g.setColor(old);
        super.paint(g);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        // nothing to do
    }

    public static void main(String[] args) {
        final JFrame frm = new JFrame("Test blurring");
        frm.add(new JTextField("It's first component"), BorderLayout.NORTH);
        frm.add(new JTextField("It's second component"), BorderLayout.SOUTH);
        final JButton btn = new JButton("Start blur");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frm.getGlassPane().setVisible(true);
                final Timer t = new Timer(5000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frm.getGlassPane().setVisible(false);
                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });
        frm.add(btn);
        frm.setSize(500, 400);
        frm.setGlassPane(new SplashGlassPane());

        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}