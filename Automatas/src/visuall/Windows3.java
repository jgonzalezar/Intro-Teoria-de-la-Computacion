
package visuall;

import AutomatasFinitos.AFD;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import java.awt.Button;
import java.awt.Color;

import java.awt.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JComboBox;

/**
 * clase que extiende el jfrma ventana que permite simular el uso de una cadena
 * @author fanat
 */
public class Windows3 extends Windows{    
    
    ArrayList<Label> blueLabel;
    Stack<String> avance;
    Stack<String> seguro;
    Label cadds;
    JComboBox<String> alpha;
    
    /**
     * constructor para la clase
     * @param tittle nombre del automata
     * @param aff automata a usar
     */
    public Windows3(String tittle, AFD aff){
        super(tittle,aff);
    
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
        trans.addActionListener((ActionEvent e) -> {
            if (vent.isStatic()) {
                if(!next.isEnabled())next.setEnabled(true);
                String name1 = seguro.pop();
                char camb=cadds.getText().charAt(avance.size()+1);
                String back = blueLabel.get(1).getText();
                avance.add(back);
                blueLabel.get(0).setText(back);
                blueLabel.get(1).setText(name1);
                ChangeEstado(name1, camb, aff.getF().contains(aff.getQ().indexOf(name1)), 1);
                cadds.setText(cadds.getText()+camb);
                avance.add(vent.getActP());
                if(seguro.size()>0){
                    blueLabel.get(2).setText(seguro.peek());
                }else{
                    blueLabel.get(2).setText("");
                    trans.setEnabled(false);
                }
            }
        });
        
        next.addActionListener((ActionEvent e) -> {
            if (vent.isStatic()) {
                if(!trans.isEnabled())trans.setEnabled(true);
                String name1 = avance.pop();
                char camb=cadds.getText().charAt(avance.size()+1);
                String back = blueLabel.get(1).getText();
                seguro.add(back);
                blueLabel.get(2).setText(back);
                blueLabel.get(1).setText(name1);
                if(avance.size()>0){
                    blueLabel.get(0).setText(avance.peek());
                }else{
                    blueLabel.get(0).setText("");
                    next.setEnabled(false);
                }
                ChangeEstado(name1, camb, aff.getF().contains(aff.getQ().indexOf(name1)), -1);
            }
        });
        
        getContentPane().add(trans);
        procs.addActionListener((ActionEvent e) -> {
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
            ProcesamientoCadenaAFD move = new ProcesamientoCadenaAFD(cadds.getText().substring(1));
            blueLabel.get(1);
            avance.forEach((string) -> {
                move.add(string);
            });
            move.add(blueLabel.get(1).getText());
            seguro.forEach((string) -> {
                move.add(string);
            });
            
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

