/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFNL;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import ProcesamientoCadenas.ProcesamientoCadenaAFNLambda;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/**
 * la clase Windows2 genera una ventana del estilo windows con la variacion de que permite visualizar el camino tomado por la cadena procesada
 * @author fanat
 */
public class Windows2AFNL extends WindowsAFNL{
    ProcesamientoCadenaAFNLambda move;
    int Camino;
    String[] caminos;
    ArrayList<Label> blueLabel;
    Label cadds;
    boolean lec;
    /**
     * constructor del segundo tipo de ventana 
     * @param tittle nombre de la ventana
     * @param aff automata
     * @param resp procesamiento de la cadena
     */
    public Windows2AFNL(String tittle, AFNL aff,ProcesamientoCadenaAFNLambda resp){
        super(tittle,aff);
        move = resp;
        Camino=0;
        initScrool();
        initButtons();
    }

    private void initButtons() {
        Button next= new Button("previo");
        next.setEnabled(false);
        next.setBounds(610, 10, 100, 40);
        
        getContentPane().add(next);
        
        Button trans= new Button("siguiente");
        trans.setBounds(710, 10, 100, 40);
        trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!next.isEnabled())next.setEnabled(true);
                    
                    if(Camino==0){
                        blueLabel.get(Camino).setForeground(Color.BLACK);
                        blueLabel.get(1).setForeground(Color.RED);
                    }else if(Camino==caminos.length-2){
                        blueLabel.get(1).setForeground(Color.BLACK);
                        blueLabel.get(2).setForeground(Color.RED);
                        trans.setEnabled(false);
                    }else{
                        blueLabel.get(0).setText(caminos[Camino]);
                        blueLabel.get(1).setText(caminos[Camino+1]);
                        blueLabel.get(2).setText(caminos[Camino+2]);
                    }
                    Camino++;
                    
                    String name = caminos[Camino], nameAnt = caminos[Camino-1];
                    char camb;
                    if(nameAnt.split(",")[1].substring(1).indexOf(name.split(",")[1].substring(1))==0){
                        camb = 'λ';
                    }else{
                        camb = nameAnt.split(",")[1].charAt(1);
                    }    
                    name = name.split(",")[0].substring(1);
                    if(name.equals("null")){
                        ChangeEstado(name,camb,false,1);
                    }
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),1);
                }
                
                
            }
        });
        if(move.getCadena().length()==0)trans.setEnabled(false);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!trans.isEnabled())trans.setEnabled(true);
                    
                    if(Camino==1){
                        blueLabel.get(Camino).setForeground(Color.BLACK);
                        blueLabel.get(0).setForeground(Color.RED);
                        next.setEnabled(false);
                    }else if(Camino==move.getCadena().length()){
                        blueLabel.get(2).setForeground(Color.BLACK);
                        blueLabel.get(1).setForeground(Color.RED);
                    }else{
                        blueLabel.get(2).setText(caminos[Camino]);
                        blueLabel.get(1).setText(caminos[Camino-1]);
                        blueLabel.get(0).setText(caminos[Camino-2]);
                    }
                    Camino--;
                    
                    String name = caminos[Camino], nameAnt = caminos[Camino+1];
                    char camb;
                    if(nameAnt.split(",")[1].substring(1).indexOf(name.split(",")[1].substring(1))==0){
                        camb = 'λ';
                    }else{
                        camb = name.split(",")[0].charAt(1);
                    }    
                    name = name.split(",")[0].substring(1);
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),-1);
                    if(Camino==move.getCadena().length())trans.setEnabled(false);
                }
                
                
            }
        });
        getContentPane().add(trans);
        
        boolean ac,re,ab;
        Button aceptados = new Button("Aceptados");
        aceptados.setBounds(880, 10, 100, 30);
        ac=move.getListaProcesamientosAceptacion().isEmpty();
        if(ac){
            aceptados.setEnabled(false);
        }else{
            getContentPane().add(aceptados);
        }
        
        Button rechazados = new Button("Rechazados");
        rechazados.setBounds(880, 40, 100, 30);
        re=move.getListaProcesamientosRechazados().isEmpty();
        
        
        Button abortados = new Button("Abortados");
        abortados.setBounds(880, 70, 100, 30);
        getContentPane().add(abortados);
        ab=move.getListaProcesamientosAbortados().isEmpty();
        if(ab){
            abortados.setEnabled(false);
        }else{
            getContentPane().add(abortados);
        }
        
        JComboBox combo = new JComboBox<>();
        //JComboBox combo;
        combo.setBounds(880, 100, 100, 30);
        
        getContentPane().add(combo);
        lec = true;
        aceptados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lec=false;
                combo.removeAllItems();
                lec=true;
                aceptados.setEnabled(false);
                if(!rechazados.isEnabled())rechazados.setEnabled(true);
                if(!abortados.isEnabled())abortados.setEnabled(true);
                for (int i = 0; i < move.getListaProcesamientosAceptacion().size(); i++) {
                    combo.addItem(i+1);
                }
                combo.setSelectedIndex(0);
            }
        });
        if(!ac){
            for (int i = 0; i < move.getListaProcesamientosAceptacion().size(); i++) {
                combo.addItem(i+1);
            }
        }else if(!re){
            for (int i = 0; i < move.getListaProcesamientosRechazados().size(); i++) {
                combo.addItem(i+1);
            }
            rechazados.setEnabled(false);
        }else if(!ab){
            for (int i = 0; i < move.getListaProcesamientosAbortados().size(); i++) {
                combo.addItem(i+1);
            }
            abortados.setEnabled(false);
            rechazados.setEnabled(false);
        }
        //aceptados.setEnabled(false);
        rechazados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lec=false;
                combo.removeAllItems();
                lec=true;
                rechazados.setEnabled(false);
                if(!aceptados.isEnabled()&&!ac)aceptados.setEnabled(true);
                if(!abortados.isEnabled()&&!ab)abortados.setEnabled(true);
                for (int i = 0; i < move.getListaProcesamientosRechazados().size(); i++) {
                    combo.addItem(i+1);
                }
                combo.setSelectedIndex(0);
            }
        });
        abortados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lec=false;
                combo.removeAllItems();
                lec=true;
                abortados.setEnabled(false);
                if(!aceptados.isEnabled()&&!ac)aceptados.setEnabled(true);
                if(!rechazados.isEnabled()&&!re)rechazados.setEnabled(true);
                for (int i = 0; i < move.getListaProcesamientosAbortados().size(); i++) {
                    combo.addItem(i+1);
                }
                combo.setSelectedIndex(0);
            }
        });
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Camino=0;
                if(!lec)return;
                String camino;
                ChangeEstado(aff.GetQ()[aff.getQ0()], '0', aff.getF().contains(aff.getQ0()), 0);
                if(!aceptados.isEnabled() && getContentPane().isAncestorOf(aceptados)){
                    camino = move.getListaProcesamientosAceptacion().get(combo.getSelectedIndex());
                }else if(!rechazados.isEnabled() && getContentPane().isAncestorOf(rechazados)){
                    camino = move.getListaProcesamientosRechazados().get(combo.getSelectedIndex());
                }else{
                    camino = move.getListaProcesamientosAbortados().get(combo.getSelectedIndex());
                    int ab = camino.lastIndexOf("]");
                    camino = camino.substring(0, ab+1) + " -> [null, ]";
                }
                
                int len = camino.lastIndexOf("]");
                caminos = camino.trim().substring(0,len+1).split(" -> ");
                next.setEnabled(false);
                blueLabel.get(0).setText(caminos[0]);
                blueLabel.get(0).setForeground(Color.red);
                blueLabel.get(0).setBounds(230, 150, 100, 30);
                getContentPane().add(blueLabel.get(0));
                if(caminos.length>1){
                    blueLabel.get(1).setText(caminos[1]);
                    blueLabel.get(1).setForeground(Color.black);
                    getContentPane().add(blueLabel.get(1));
                    trans.setEnabled(true);
                }else{
                    blueLabel.get(1).setText(" ");
                    blueLabel.get(1).setForeground(Color.BLACK);
                    trans.setEnabled(false);
                }
                if(caminos.length>2){
                    blueLabel.get(2).setText(caminos[2]);
                    blueLabel.get(2).setForeground(Color.black);
                    getContentPane().add(blueLabel.get(2));
                }else{
                     blueLabel.get(2).setText(" ");
                    blueLabel.get(2).setForeground(Color.BLACK);
                }
            }
        });
        combo.setSelectedIndex(0);
    }

    private void initScrool() {
        String camino = move.imprimirCamino();
        int len = camino.lastIndexOf("]");
        caminos = camino.trim().substring(0,len+1).split(" -> ");
        String cad = move.getCadena();
        
        cadds=new Label(move.getCadena());
        cadds.setForeground(Color.BLACK);
        cadds.setBounds(330,100,100,30);
        cadds.setEnabled(true);
        getContentPane().add(cadds);
        blueLabel = new ArrayList<>();
        blueLabel.add(new Label(caminos[0]));
        blueLabel.get(0).setForeground(Color.red);
        blueLabel.get(0).setBounds(230, 150, 100, 30);
        getContentPane().add(blueLabel.get(0));
        if(caminos.length>1){
            blueLabel.add(new Label(caminos[1]));
            blueLabel.get(1).setForeground(Color.BLACK);
            blueLabel.get(1).setBounds(330, 150, 100, 30);
            getContentPane().add(blueLabel.get(1));
        }else{
            blueLabel.add(new Label(" "));
            blueLabel.get(1).setForeground(Color.BLACK);
            blueLabel.get(1).setBounds(330, 150, 100, 30);
            getContentPane().add(blueLabel.get(1));
        }
        
        if(caminos.length>2){
            blueLabel.add(new Label(caminos[2]));
            blueLabel.get(2).setForeground(Color.BLACK);
            blueLabel.get(2).setBounds(430, 150, 100, 30);
            getContentPane().add(blueLabel.get(2));
        }else{
            blueLabel.add(new Label(" "));
            blueLabel.get(2).setForeground(Color.BLACK);
            blueLabel.get(2).setBounds(330, 150, 100, 30);
            getContentPane().add(blueLabel.get(2));
        }
        //label = new JLabel("Aguirre, der Zorn Gottes");
        //label.setFont(new Font("Serif", Font.PLAIN, 12));

    }
    
    public static void main(String[] args) {
        try{
            
            JFileChooser fileChooser = new JFileChooser(new File("."));
            fileChooser.setDialogTitle("Seleccione el automata que desea importar");

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                throw new NullPointerException();
            }
            AFNL aff = new AFNL(fileChooser.getSelectedFile().getAbsolutePath());
            
            ProcesamientoCadenaAFNLambda proc = aff.procesarCadenad("aaaab");
            
            Windows2AFNL nes = new Windows2AFNL("AFNL",aff,proc);
            nes.Simulat();
            
        }catch(Exception|Error e){
            
        }
    }
    
}

