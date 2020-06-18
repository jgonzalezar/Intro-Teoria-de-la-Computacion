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
import java.awt.Dimension;

import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author fanat
 */
public class Windows2 extends Windows{
    ProcesamientoCadenaAFD move;
    int Camino;
    String[] caminos;
    ArrayList<Label> blueLabel;
    Label cadds;
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
            ProcesamientoCadenaAFD resp = aff.prosCaden("aab");
                Windows jf= new Windows2("Tutorial",aff,resp);
            
                jf.Simulat();
                System.out.println("heeey");
        }catch(HeadlessException | FileNotFoundException | NullPointerException e){
                e.printStackTrace();
        }
    }

    public Windows2(String tittle, AFD aff,ProcesamientoCadenaAFD resp){
        super("tittle",aff);
        move = resp;
        Camino=0;
        initButtons();
        initScrool();
    }

    private void initButtons() {
        Button next= new Button("previo");
        next.setEnabled(false);
        next.setBounds(220, 10, 100, 40);
        
        getContentPane().add(next);
        
        Button trans= new Button("siguiente");
        trans.setBounds(330, 10, 100, 40);
        trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!next.isEnabled())next.setEnabled(true);
                    
                    String name = move.getListaEstadoSimboloDeProcesamiento().get(Camino+1);
                    char camb=move.getCadena().charAt(Camino);
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),1);
                    if(Camino==0){
                        blueLabel.get(Camino).setForeground(Color.BLACK);
                        blueLabel.get(1).setForeground(Color.RED);
                    }else if(Camino==move.getCadena().length()-1){
                        blueLabel.get(1).setForeground(Color.BLACK);
                        blueLabel.get(2).setForeground(Color.RED);
                        trans.setEnabled(false);
                    }else{
                        blueLabel.get(0).setText(caminos[Camino]);
                        blueLabel.get(1).setText(caminos[Camino+1]);
                        blueLabel.get(2).setText(caminos[Camino+2]);
                    }
                    Camino++;
                }
                
                
            }
        });
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vent.isStatic()){
                    if(!trans.isEnabled())trans.setEnabled(true);
                    String name = move.getListaEstadoSimboloDeProcesamiento().get(Camino-1);
                    char camb=move.getCadena().charAt(Camino-1);
                    String go = aff.getDelta().cambio(camb, vent.getActP());
                    ChangeEstado(name,camb,aff.getF().contains(aff.getQ().indexOf(name)),-1);
                    if(Camino==1){
                        blueLabel.get(Camino).setForeground(Color.BLACK);
                        blueLabel.get(0).setForeground(Color.RED);
                        next.setEnabled(false);
                    }else if(Camino==move.getCadena().length()){
                        blueLabel.get(2).setForeground(Color.BLACK);
                        blueLabel.get(1).setForeground(Color.RED);
                        trans.setEnabled(false);
                    }else{
                        blueLabel.get(2).setText(caminos[Camino]);
                        blueLabel.get(1).setText(caminos[Camino-1]);
                        blueLabel.get(0).setText(caminos[Camino-2]);
                    }
                    Camino--;
                }
                
                
            }
        });
        getContentPane().add(trans);
    }

    private void initScrool() {
        String camino = move.pasos();
        caminos = camino.split("->");
        
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
        blueLabel.add(new Label(caminos[1]));
        blueLabel.get(1).setForeground(Color.BLACK);
        blueLabel.get(1).setBounds(330, 150, 100, 30);
        getContentPane().add(blueLabel.get(1));
        blueLabel.add(new Label(caminos[2]));
        blueLabel.get(2).setForeground(Color.BLACK);
        blueLabel.get(2).setBounds(430, 150, 100, 30);
        getContentPane().add(blueLabel.get(2));
        //label = new JLabel("Aguirre, der Zorn Gottes");
        //label.setFont(new Font("Serif", Font.PLAIN, 12));

    }
    
}

