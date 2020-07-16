/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.*;
import Herramientas.Transitions;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * la clase Dibbujo extiende de un canvas para representar un dibujo grafico de
 * un automata finito determinista
 *
 * @author fanat
 */
public class Dibbujo1 extends JComponent implements Scrollable {

    private final AFD afd;
    private String estadoP;
    private String estadoN;
    private anim ani;
    private int trans;
    private int ye;
    private int x;
    private int y;

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return (orientation==SwingConstants.VERTICAL) ? visibleRect.height/10 : visibleRect.width/10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return  (orientation==SwingConstants.VERTICAL) ? visibleRect.height/10 : visibleRect.width/10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    private enum anim {
        estatic, moveto, white, backto
    }

    /**
     * constructor de la calse Dibujo
     *
     * @param ff
     */
    public Dibbujo1(AFD ff) {
        super();
        afd = ff;
        ani = anim.estatic;
        int qs = afd.getQ().size();
        trans = 0;
        estadoP = estadoN = afd.getQ().get(afd.getQ0());
        x = qs * 100 + 150 * (qs - 1) + 20;
        y = ((qs - 1) * 100) * 2 + 100;
        if (x < 600) {
            x = 600;
        }
        if (y < 300) {
            y = 300;
        }

        init();
    }

    /**
     * funcion paint que dibuja en el canvas
     *
     * @param g grafics del dibbujo
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        switch (ani) {
            case estatic:
                drawAFD(g);
                break;
            case moveto:
                if (ye == 0) {
                    drawAFD(g);
                } else if (ye < 220) {
                    ye += 5;
                } else {
                    ye = 0;
                    ani = anim.estatic;
                    estadoP = estadoN;
                    trans = 0;
                }
                break;
            case white:
                drawAFD(g);
                break;
            case backto:
                if (ye == 0) {
                    drawAFD(g);
                }
                if (ye < 220) {
                    ye += 5;
                } else {
                    ye = 0;
                    ani = anim.estatic;
                    estadoP = estadoN;
                    trans = 0;
                }
                break;
            default:
                throw new AssertionError(ani.name());
        }

    }

    private void init() {
        setBackground(Color.WHITE);
        setBounds(0, 0, x, y);
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(this.getSize().width,this.getSize().height);
    }
    
    private void drawAFD(Graphics g) {
        ArrayList<String> qq = afd.getQ();
        Transitions de = afd.getDelta();
        Alfabeto alf = afd.getSigma();
        drawFle(20 + 250 * afd.getQ0(), y / 2, g, 6);
        for (int i = 0; i < qq.size(); i++) {
            String name = qq.get(i);

            HashMap<String, ArrayList<Character>> ssss = new HashMap();
            for (int j = 0; j < alf.length(); j++) {
                ArrayList<Integer> asd = new ArrayList<>();
                if (afd instanceof AFNL) {
                    asd = de.getMove(i, alf.get(j));
                } else if (afd instanceof AFN) {
                } else {
                    String go = de.cambio(alf.get(j), qq.get(i));
                    asd.add(qq.indexOf(go));
                }
                for (int k = 0; k < asd.size(); k++) {
                    if (ssss.get(qq.get(asd.get(k))) == null) {
                        ssss.put(qq.get(asd.get(k)), new ArrayList<>());
                    }
                    ssss.get(qq.get(asd.get(k))).add(alf.get(j));
                }
            }

            System.out.println(ssss.toString());
            for (Map.Entry<String, ArrayList<Character>> entry : ssss.entrySet()) {
                String key = entry.getKey();
                ArrayList<Character> value = entry.getValue();
                System.out.println(qq.get(i)+" "+key+" "+value.toString());
                String text = value.toString().substring(1, value.toString().length() - 1);
                int ee = qq.indexOf(key);
                int dif = Math.abs(ee - i);
                if (this.trans != 0) {
                    if (qq.get(i).equals(estadoP) && key.equals(estadoN) && this.trans == 1) {
                        g.setColor(Color.BLUE);
                    } else if (qq.get(i).equals(estadoN) && key.equals(estadoP) && this.trans == -1) {
                        g.setColor(Color.BLUE);
                    }
                }
                if (dif == 0) {

                } else {
                    int xa, ya, large, alto, ang1, ang2;
                    large = 250 * dif;
                    alto = 75 * dif;
                    ang1=0;
                    ang2=0;
                    if (ee > i) {
                        xa = i * 250 + 20 + 50;
                        
                        ya = y / 2 - 50 - alto;
                    } else {
                        xa = ee * 250 + 20 + 50;
                        ya = y / 2 + 50;
                    }
                    
                    g.drawArc(xa, ya, large, alto, ang1, ang2);
                    g.drawRect(xa, ya,large, alto);
                }

                g.setColor(Color.BLACK);
            }
            switch (trans) {
                case 0:
                    if (qq.get(i).equals(estadoP)) {
                        g.setColor(Color.CYAN);
                        g.fillOval(i * 250 + 20, y / 2 - 50, 100, 100);
                        g.setColor(Color.BLACK);
                    }
                    break;
                case -1:
                    if (qq.get(i).equals(estadoP)) {
                        g.setColor(Color.CYAN);
                        g.fillOval(i * 250 + 20, y / 2 - 50, 100, 100);
                        g.setColor(Color.BLACK);
                    } else if (qq.get(i).equals(estadoN)) {
                        g.setColor(Color.ORANGE);
                        g.fillOval(i * 250 + 20, y / 2 - 50, 100, 100);
                        g.setColor(Color.BLACK);
                    }
                    break;
                case 1:
                    if (qq.get(i).equals(estadoP)) {
                        g.setColor(Color.CYAN);
                        g.fillOval(i * 250 + 20, y / 2 - 50, 100, 100);
                        g.setColor(Color.BLACK);
                    } else if (qq.get(i).equals(estadoN)) {
                        g.setColor(Color.ORANGE);
                        g.fillOval(i * 250 + 20, y / 2 - 50, 100, 100);
                        g.setColor(Color.BLACK);
                    }
                    break;
                default:
                    break;
            }
            drawState(i * 250 + 20 + 50, name, afd.getF().contains(i), g);
        }

    }

    private void drawState(int x, String Name, boolean fid, Graphics g) {
        if (fid) {
            drawCircle(x, y / 2, 40, g);
        }
        drawCircle(x, y / 2, 50, g);
        drawName(x, y / 2, Name, g);
    }

    private void drawCircle(int x, int y, int r, Graphics g) {
        int xc = x - r;
        int yc = y - r;
        g.drawOval(xc, yc, r * 2, r * 2);
    }

    private void drawName(int x, int y, String text, Graphics g) {
        int tam = text.length();
        g.drawString(text, (x - 5 * (tam - 1) - 6), (y + 6));

    }

    private void drawFle(int x, int y, Graphics g, int direc) {
        switch (direc) {
            case 2:
                g.drawLine(x, y - 20, x, y);
                g.drawLine(x - 10, y - 10, x, y);
                g.drawLine(x + 10, y - 10, x, y);
                break;
            case 8:
                g.drawLine(x, y + 20, x, y);
                g.drawLine(x - 10, y + 10, x, y);
                g.drawLine(x + 10, y + 10, x, y);
                break;
            case 6:
                g.drawLine(x - 20, y, x, y);
                g.drawLine(x - 10, y + 10, x, y);
                g.drawLine(x - 10, y - 10, x, y);
                break;
            case 4:
                g.drawLine(x + 20, y, x, y);
                g.drawLine(x + 10, y + 10, x, y);
                g.drawLine(x + 10, y - 10, x, y);
                break;
        }

    }

    /**
     * funcion para actualizar los datos de la case y cambiar el estado de la
     * animacion
     *
     * @param nextState nombre del estado al que se debe desplazar
     * @param Fin el estado al que se debe mover es aceptado?
     * @param tp el desplazamiento sera instantaneo o tiene direccion
     * @param used caracter por el cual se realiza el movimiento
     */
    /*public void setData(String nextState,boolean Fin,int tp, char used){
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
    }*/
    /**
     * la animacion esta en un estado estatico
     *
     * @return
     */
    public boolean isStatic() {
        return ani == anim.estatic;
    }
}
