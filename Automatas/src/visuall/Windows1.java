/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import java.awt.Button;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/**
 *
 * @author fanat
 */
public class Windows1 extends Windows{
    JComboBox<String> estad;
    JComboBox<String> alpha;
    //ArrayList<
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Seleccione el automata que desea importar");
        
        try {
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                throw new NullPointerException();
            }
            String url;
            url = fileChooser.getSelectedFile().getAbsolutePath();
            AFD aff = new AFD(url);
            
                Windows jf= new Windows1("Tutorial",aff);
            
                jf.Simulat();
        }catch(HeadlessException | FileNotFoundException | NullPointerException e){
                e.printStackTrace();
        }
    }

    public Windows1(String tittle, AFD aff){
        super("tittle",aff);
        initButtons();
        initEstados();
        initAlphabe();
        
    }

    private void initButtons() {
        Button next= new Button("Ir a:");
        next.setBounds(220, 10, 75, 30);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    ChangeEstado(estad.getSelectedItem().toString(),'a',aff.getF().contains(estad.getSelectedIndex()),0);
                }
                
                
            }
        });
        getContentPane().add(next);
        
        Button trans= new Button("Procesar:");
        trans.setBounds(220, 50, 75, 30);
        trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    char camb=alpha.getSelectedItem().toString().charAt(0);
                    String go = aff.getDelta().cambio(camb, vent.getActP());
                    ChangeEstado(go,camb,aff.getF().contains(aff.getQ().indexOf(go)),1);
                    
                }
                
                
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
        });*/
        estad.setBounds(330,10,110,30);
        System.out.println("estadops");
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
        
    }
    
}

