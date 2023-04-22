



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.jdesktop.swingx.JXPanel;
//import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;
import org.jdesktop.swingx.util.GraphicsUtilities;

public class GlassPane3 implements ActionListener
{
    JFrame jf;
    JPanel background;
    JXPanel glassPanel,detailsPanel;
    JButton button,another;
    BufferedImage blurBuffer,backBuffer,currentGraphics;
    float alpha=0.0f;
    public GlassPane3()
    {
        jf=new JFrame("Example");
        background=new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                Color c1=new Color(41,59,102);
                Color c2=new Color(2,2,2);
                GradientPaint gp=new GradientPaint(0,0,c1,0,h-20,c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        button=new JButton("Click me!");
        another=new JButton("Close");
        button.addActionListener(this);
        another.addActionListener(this);
        jf.setSize(900,400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(background);
        jf.add(button,BorderLayout.NORTH);
        glassPanel = new BlurPanel();
        jf.setGlassPane(glassPanel);
        jf.setVisible(true);
    }
    public static void main(String []args)
    {
        GlassPane3 n=new GlassPane3();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==button)
        {
            createBlur();
            jf.getGlassPane().setVisible(true);
            JRootPane root=SwingUtilities.getRootPane(jf);
            currentGraphics = new BufferedImage(root.getWidth(),root.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
            jf.getGlassPane().setVisible(false);
            glassPanel = new GlassPanel();
            jf.getGlassPane().setVisible(true);
        }
        if(e.getSource()==another)
        {
            jf.getGlassPane().setVisible(false);
        }
    }
    class BlurPanel extends JXPanel
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            if(isVisible() && blurBuffer != null)
            {
                Graphics2D g2d=(Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(backBuffer, 0, 0,null);
                g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
                System.out.println(alpha);
                System.out.println("Function called 1");
                g2d.drawImage(blurBuffer, 0, 0, jf.getWidth(),jf.getHeight(),null);
                g2d.dispose();
            }
        }
    }
    void createBlur()
    {
        alpha=1.0f;
        JRootPane root = SwingUtilities.getRootPane(jf);
        blurBuffer = GraphicsUtilities.createCompatibleImage(jf.getWidth(),jf.getHeight());
        Graphics2D g2d = blurBuffer.createGraphics();
        root.paint(g2d);
        g2d.dispose();
        
        backBuffer = blurBuffer;
        blurBuffer = GraphicsUtilities.createThumbnailFast(blurBuffer, jf.getWidth()/2);
        blurBuffer = new GaussianBlurFilter(5).filter(blurBuffer, null);
    }
    class GlassPanel extends JXPanel
    {
        public GlassPanel()
        {
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g)
        {
            int x=34;
            int y = 34;
            int w = getWidth() - 68;
            int h = getHeight() - 68;
            int arc = 30;
            
            Graphics2D g2 = currentGraphics.createGraphics();
            g2.drawImage(currentGraphics, 0, 0, null);
            g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fillRoundRect(x, y, w, h, arc, arc);

            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, w, h, arc, arc); 

            g2.dispose();
        }
    }
}