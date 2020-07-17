/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * la clase Dibbujo extiende de un canvas para representar un dibujo grafico de un automata finito determinista
 * @author fanat
 */
public class Dibbujo extends Canvas {
    private final String Ini;
    private String actP;
    private String actN;
    private boolean fiP;
    private boolean fiN;
    private boolean comeP;
    private boolean comeN;
    private int ye=0;
    private char next;
    private anim ani;

    private enum anim{
        estatic,moveto,white,backto
    }
    
    /**
     * constructor de la calse Dibujo 
     * @param firstState nombre del estado inicial
     * @param iniFin el estado inicial es aceptado
     */
    
    public Dibbujo(String firstState,boolean iniFin) {
        actP=actN=Ini = firstState;
        fiP=fiN=iniFin;
        comeP=comeN=false;
        next=' ';
        ani =anim.estatic;
        init();
        
    }
    
    /**
     * funcion paint que dibuja en el canvas
     * @param g grafics del dibbujo
     */
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        switch(ani){
            case estatic:
                drawState(100,true,g);
                break;
            case moveto:
                if(ye<220){
                    drawState(100-ye,true,g);
                    drawState(320-ye,false,g); 
                    g.drawLine(160-ye, 100, 220-ye, 100);
                    g.drawString(next+"", 200-ye, 100);
                    ye+=5;
                }else{
                    ye=0;
                    ani=anim.estatic;
                    actP=actN;
                    fiP=fiN;
                    comeP=comeN;
                    
                }
                break;
            case white:
                g.clearRect(0, 0, 200, 200);
                ani=anim.estatic;
                break;
            case backto:
                if(ye<220){
                    drawState(-120+ye,false,g);
                    drawState(100+ye,true,g); 
                    g.drawLine(-60+ye, 100,ye, 100);
                    g.drawString(next+"", -20+ye, 100);
                    ye+=5;
                }else{
                    ye=0;
                    ani=anim.estatic;
                    actP=actN;
                    fiP=fiN;
                    comeP=comeN;
                    
                }
                break;
            default:
                throw new AssertionError(ani.name());
}
        
    }

    private void init() {
        setBackground(Color.WHITE);
    }
    private void drawState(int x,boolean P,Graphics g){
        String data;
        boolean fina;
        boolean come;
        if(P){
            data=actP;
            fina=fiP;
            come=comeP;
        }else{
            data=actN;
            fina=fiN;
            come=comeN;       
        }
        if(fina){
            drawCircle(x, 100, 50, g);
        }
        drawCircle(x, 100, 60, g);
        if(data.equals(Ini)){
            drawFle(x,40,g,2);
        }
        drawName(x, 100, data, g);
        if(come){
            drawFle(x-60,100,g,4);
        }
    }
    
    private void drawCircle(int x,int y, int r,Graphics g){
        int xc=x-r;
        int yc=y-r;
        g.drawOval(xc, yc, r*2, r*2);
    }
    
    private void drawName(int x,int y, String text,Graphics g){
        int tam = text.length();
        g.drawString(text,(x-5*(tam-1)-6), (y+6));
        
    }

    private void drawFle(int x, int y, Graphics g,int direc) {
        switch(direc){
            case 2:
                g.drawLine(x, y-40, x, y);
                g.drawLine(x-10, y-10, x, y);
                g.drawLine(x+10, y-10, x, y);
                break;
            case 8:
                g.drawLine(x, y+40, x, y);
                g.drawLine(x-10, y+10, x, y);
                g.drawLine(x+10, y+10, x, y);
                break;
            case 4:
                g.drawLine(x-40, y, x, y);
                g.drawLine(x-10, y+10, x, y);
                g.drawLine(x-10, y-10, x, y);
                break;
            case 6:
                g.drawLine(x+40, y, x, y);
                g.drawLine(x+10, y+10, x, y);
                g.drawLine(x+10, y-10, x, y);
                break;
        }
        
    }
    
    
    /**
     * funcion para actualizar los datos de la case y cambiar el estado de la animacion
     * @param nextState nombre del estado al que se debe desplazar
     * @param Fin el estado al que se debe mover es aceptado?
     * @param tp el desplazamiento sera instantaneo o tiene direccion
     * @param used caracter por el cual se realiza el movimiento
     */
    public void setData(String nextState,boolean Fin,int tp, char used){
        if(nextState == null)nextState = "∅";
        if(nextState.equals(actP) && tp == 0)return;
        
        switch (tp) {
            case 0:
                ani=anim.white;
                actP=nextState;
                fiP=Fin;
                comeP=false;
                break;
            case 1:
                ani=anim.moveto;
                actN=nextState;
                fiN=Fin;
                next=used;
                comeN=true;
                break;
            case -1:
                ani=anim.backto;
                actN=nextState;
                fiN=Fin;
                next=used;
                comeP=true;
                comeN=false;
                break;
        }
    }
    
    /**
     * la animacion esta en un estado estatico
     * @return 
     */
 
    public boolean isStatic(){
        return ani==anim.estatic;
    }
    
    /**
     * consigue el nombre del estado previo
     * @return 
     */

    public String getActP() {
        return actP;
    }

    /**
     * returna si el estado previo es aceptado
     * @return 
     */
    
    public boolean isFiP() {
        return fiP;
    }
}
