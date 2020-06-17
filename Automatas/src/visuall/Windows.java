/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author fanat
 */
public class Windows extends JFrame implements Runnable{
    static boolean hey;
    protected Dibbujo vent;
    protected int time;
    protected AFD aff;

    public Windows(String tittle, AFD aff){
        super("tittle");
        hey=true;
        this.aff=aff;
        initFrame();
        initCanvas();
        time=100;
    }
    
    private void initFrame(){
        setSize(600, 250);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initCanvas(){
        vent = new Dibbujo(aff.getQ().get(aff.getQ0()),aff.getF().contains(aff.getQ0()));
        vent.setBounds(10,10,200, 200);
        getContentPane().add(vent);
    }
    
    @Override
    public void dispose() {
        hey=false;
        setVisible(false);
        super.dispose();
    }
    
    protected void ChangeEstado(String namenext,char used,boolean Fin, boolean tp ){
        vent.setData(namenext, Fin, tp, used);       
        if(tp){
            time=1000;
        }else{
            time=100;
        }
        ReDraw();
    }

    @Override
    public void run() {
        while(!vent.isStatic()){
            try
            {
                Thread.sleep(200);
            }catch(InterruptedException d){}
            vent.repaint();
        }
    }
    
    private void ReDraw(){
        new Thread(() -> {
            while(!vent.isStatic()){
                try
                {
                    Thread.sleep(time);
                }catch(InterruptedException d){}
                vent.repaint();
            }
        }).start();
    }
    
    public void Simulat(){
        while(hey&&isVisible()){
            try
            {
                Thread.sleep(500);
            }catch(InterruptedException d){}
        }
    }
}

