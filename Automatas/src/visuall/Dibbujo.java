/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author fanat
 */
public class Dibbujo extends Canvas{
    
    int yeai;

    public Dibbujo(String firstState) {
        yeai=0;
    }
    
    @Override
    public void paint(Graphics g){
        yeai++;
        System.out.println("ddd"+yeai);
        g.setColor(Color.red);
        g.drawRect(yeai, 10,100, 100);
        /*if(yeai<400){
            
            repaint();
        }*/
        
    }
    
}
