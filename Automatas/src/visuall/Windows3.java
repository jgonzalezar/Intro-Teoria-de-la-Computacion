/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import java.awt.Button;
import java.awt.Color;

import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/**
 *
 * @author fanat
 */
public class Windows3 extends Windows{    
    ArrayList<Label> blueLabel;
    Stack<String> avance;
    Stack<String> seguro;
    Label cadds;
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
            
                Windows jf= new Windows3("Tutorial",aff);
            
                jf.Simulat();
        }catch(HeadlessException | FileNotFoundException | NullPointerException e){
                e.printStackTrace();
        }
    }

    public Windows3(String tittle, AFD aff){
        super("tittle",aff);
        initAlmc();
        initButtons();
        initAlphabe();
        
    }

    private void initButtons() {
        Button procs= new Button("Procesar:");
        procs.setBounds(220, 10, 75, 30);
        
        getContentPane().add(procs);
        
        Button next= new Button("previo");
        next.setEnabled(false);
        next.setBounds(220, 50, 100, 40);
        
        getContentPane().add(next);
        
        Button trans= new Button("siguiente");
        trans.setBounds(330, 50, 100, 40);
        trans.setEnabled(false);
        trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!next.isEnabled())next.setEnabled(true);
                    String name = seguro.pop();
                    char camb=cadds.getText().charAt(avance.size()+1);
                    String back = blueLabel.get(1).getText();
                    avance.add(back);
                    blueLabel.get(0).setText(back);
                    blueLabel.get(1).setText(name);
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),1);
                    cadds.setText(cadds.getText()+camb);
                    avance.add(vent.getActP());
                    
                    if(seguro.size()>0){
                        blueLabel.get(2).setText(seguro.peek());
                    }else{
                        blueLabel.get(2).setText("");
                        trans.setEnabled(false);
                    }
                }
                
                
            }
        });
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!trans.isEnabled())trans.setEnabled(true);                    
                    String name = avance.pop();
                    char camb=cadds.getText().charAt(avance.size()+1);
                    String back = blueLabel.get(1).getText();
                    seguro.add(back);
                    blueLabel.get(2).setText(back);
                    blueLabel.get(1).setText(name);
                    if(avance.size()>0){
                        blueLabel.get(0).setText(avance.peek());
                    }else{
                        blueLabel.get(0).setText("");
                        next.setEnabled(false);
                    }                    
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),-1);
                    
                    
                }
                
                
            }
        });
        
        getContentPane().add(trans);
        procs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    seguro.clear();
                    blueLabel.get(2).setText("");
                    char camb=alpha.getSelectedItem().toString().charAt(0);
                    String Prev= blueLabel.get(1).getText();
                    avance.push(Prev);
                    blueLabel.get(0).setText(Prev);
                    String go = aff.getDelta().cambio(camb, Prev);
                    blueLabel.get(1).setText(go);
                    ChangeEstado(go,camb,aff.getF().contains(aff.getQ().indexOf(go)),1);
                    
                    cadds.setText(cadds.getText().substring(0, avance.size())+camb);
                    trans.setEnabled(false);
                    next.setEnabled(true);
                }
                
                
            }
        });
        
        Button copyChad = new Button("Copiar Cadena");
        copyChad.setBounds(220,130,100, 30);
        copyChad.addActionListener((ActionEvent e) -> {
            if(!vent.isStatic())return;
            String myString =cadds.getText();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        getContentPane().add(copyChad);
        
        Button copyCam = new Button("Copiar camino tomado");
        copyCam.setBounds(220,190,150, 30);
        copyCam.addActionListener((ActionEvent e) -> {
            if(!vent.isStatic())return;
            ProcesamientoCadenaAFD move = new ProcesamientoCadenaAFD(cadds.getText());
            blueLabel.get(1);
            for (String string : avance) {
                move.add(string);
            }
            move.add(blueLabel.get(1).getText());
            for (String string : seguro) {
                move.add(string);
            }
            
            move.setEsAceptada(vent.isFiP());
            
            String myString =move.pasos()+move.EsAceptada();
            System.out.println(myString);
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        getContentPane().add(copyCam);
    }
    
    private void initAlphabe() {
        char[] alp= aff.getSigma().getSimbolos();
        String[] afl = new String[alp.length];
        for (int i = 0; i < alp.length; i++) {
            afl[i]=alp[i]+"";
        }
        
        alpha = new JComboBox<>(afl);
        alpha.setBounds(330,10,110,30);
        getContentPane().add(alpha);
        
    }
    /*
    String myString;// =estad.getSelectedItem().toString();
                    StringSelection stringSelection = new StringSelection(myString);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
    */

    private void initAlmc() {
        blueLabel=new ArrayList<>();
        blueLabel = new ArrayList<>();
        blueLabel.add(new Label(" "));
        blueLabel.get(0).setForeground(Color.BLACK);
        blueLabel.get(0).setBounds(230, 160, 100, 30);
        getContentPane().add(blueLabel.get(0));
        blueLabel.add(new Label(vent.getActP()));
        blueLabel.get(1).setForeground(Color.red);
        blueLabel.get(1).setBounds(330, 160, 100, 30);
        getContentPane().add(blueLabel.get(1));        
        blueLabel.add(new Label(" "));
        blueLabel.get(2).setForeground(Color.BLACK);
        blueLabel.get(2).setBounds(430, 160, 100, 30);
        getContentPane().add(blueLabel.get(2));
       
       
        
        avance=new Stack<>();
        seguro=new Stack<>();
        //66 char
        cadds=new Label(" ");
        cadds.setBounds(220, 100, 380, 30);
        getContentPane().add(cadds);
        
    }
    
}
