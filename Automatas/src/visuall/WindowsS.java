/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * la clase Windows1 extiende de la clase windows y esta diseñada para mostrar una version de la ventana que permite moverse a travez del automata de forma libre pero no puede guardar nada
 * @author fanat
 */
public class WindowsS extends JFrame{
    JComboBox<String> estad;
    JComboBox<String> alpha;
    //ArrayList<
    
    /**
     * constructor para crear la ventana
     * @param tittle titulo de la ventana
     * @param aff automata
     */

    public WindowsS(String tittle, AFD aff){
        super("tittle");
       /* initButtons();
        initEstados();
        initAlphabe();*/
        
    }
/*
    private void initButtons() {
        Button next= new Button("Ir a:");
        next.setBounds(220, 10, 75, 30);
        next.addActionListener((ActionEvent e) -> {
            if(vent.isStatic()){
                ChangeEstado(estad.getSelectedItem().toString(),'a',aff.getF().contains(estad.getSelectedIndex()),0);
            }
        });
        getContentPane().add(next);
        
        Button trans= new Button("Procesar:");
        trans.setBounds(220, 50, 75, 30);
        trans.addActionListener((ActionEvent e) -> {
            if(vent.isStatic()){
                char camb=alpha.getSelectedItem().toString().charAt(0);
                String go = aff.getDelta().cambio(camb, vent.getActP());
                ChangeEstado(go,camb,aff.getF().contains(aff.getQ().indexOf(go)),1);
                
            }
        });
        getContentPane().add(trans);
    }
    
    
    private void initEstados() {
        ArrayList<String> Qa= aff.getQ();
        String[] estados = new String[Qa.size()];
        for (int i = 0; i < Qa.size(); i++) {
            estados[i]=Qa.get(i);
        }

        estad = new JComboBox<>(estados);
        //estad.setSelectedIndex(aff.getQ0());
        /*estad.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //display.setText(e.getItem().toString());
            }
        });*//*
        estad.setBounds(330,10,110,30);
        getContentPane().add(estad);
        
    }
    private void initAlphabe() {
        char[] alp= aff.getSigma().getSimbolos();
        String[] afl = new String[alp.length];
        for (int i = 0; i < alp.length; i++) {
            afl[i]=alp[i]+"";
        }
        
        alpha = new JComboBox<>(afl);
        alpha.setBounds(330,50,110,30);
        getContentPane().add(alpha);
        
    }*/
    
}

