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
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * la clase Windows2 genera una ventana del estilo windows con la variacion de que permite visualizar el camino tomado por la cadena procesada
 * @author fanat
 */
public class Windows2 extends Windows{
    ProcesamientoCadenaAFD move;
    int Camino;
    String[] caminos;
    ArrayList<Label> blueLabel;
    Label cadds;
    
    /**
     * constructor del segundo tipo de ventana 
     * @param tittle nombre de la ventana
     * @param aff automata
     * @param resp procesamiento de la cadena
     */
    public Windows2(String tittle, AFD aff,ProcesamientoCadenaAFD resp){
        super(tittle,aff);
        move = resp;
        Camino=0;
        initScrool();
        initButtons();
     
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
                       // trans.setEnabled(false);
                    }else{
                        blueLabel.get(0).setText(caminos[Camino]);
                        blueLabel.get(1).setText(caminos[Camino+1]);
                        blueLabel.get(2).setText(caminos[Camino+2]);
                    }
                    Camino++;
                    if(Camino==move.getCadena().length())trans.setEnabled(false);
                }
                
                
            }
        });
        if(move.getCadena().length()==0)trans.setEnabled(false);
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
        if(cad.length()>0){
            blueLabel.add(new Label(caminos[1]));
            blueLabel.get(1).setForeground(Color.BLACK);
            blueLabel.get(1).setBounds(330, 150, 100, 30);
            getContentPane().add(blueLabel.get(1));
        }
        
        if(cad.length()>1){
            blueLabel.add(new Label(caminos[2]));
            blueLabel.get(2).setForeground(Color.BLACK);
            blueLabel.get(2).setBounds(430, 150, 100, 30);
            getContentPane().add(blueLabel.get(2));
        }
        //label = new JLabel("Aguirre, der Zorn Gottes");
        //label.setFont(new Font("Serif", Font.PLAIN, 12));

    }
    
}

