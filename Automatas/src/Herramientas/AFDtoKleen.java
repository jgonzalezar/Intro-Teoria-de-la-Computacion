/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author fanat
 */
public class AFDtoKleen {

    String expresion;

    public AFDtoKleen(AFD afd) {
        if(afd instanceof AFNL){
            afd= ((AFNL) afd).AFN_LambdaToAFD();
        }else if(afd instanceof AFN){
            afd=((AFN) afd).AFNtoAFD();
        }
        HashMap<String, HashMap<String, String>> table = new HashMap<>();
        ArrayList<String> Q = new ArrayList<>();
        afd.eliminarEstadosInaccesibles();
        ArrayList<String> lim = afd.getEstadosLimbo();

        int q0 = afd.getQ0();
        if (lim.contains(afd.getQ().get(q0))) {
            expresion = "∅";
            return ;
        }
        
        if (q0 != 0) {
            for (int i = q0; i < afd.getQ().size(); i++) {
                if (!lim.contains(afd.getQ().get(i))) {
                    Q.add(afd.getQ().get(i));
                }

            }
            for (int i = 0; i < q0; i++) {
                if (!lim.contains(afd.getQ().get(i))) {
                    Q.add(afd.getQ().get(i));
                }
            }
        } else {
            for (String string : afd.getQ()) {
                if (!lim.contains(string)) {
                    Q.add(string);
                }
            }

        }

        char[] alf = afd.getSigma().getSimbolos();
        for (String Q1 : Q) {
            table.put(Q1, new HashMap<>());
            if (afd.getF().contains(afd.getQ().indexOf(Q1))) {
                table.get(Q1).put("∅", "$");
            }
            for (char Sig : alf) {
                String go = afd.getDelta().cambio(Sig, Q.get(0));
                if (!lim.contains(go)) {
                    if (table.get(Q1).get(go) == null) {
                        table.get(Q1).put(go, Sig + "");
                    } else {
                        String hey = table.get(Q1).get(go);
                        table.get(Q1).replace(go, hey + "U" + Sig);
                    }
                    //table.get(Q1)
                }
            }
        }

        for (int i = Q.size() - 1; i > 0; i--) {   // "∅", "$"
            if (table.get(Q.get(i)) != null) {
                if (table.get(Q.get(i)).get(Q.get(i)) != null) {
                    String ss = table.get(Q.get(i)).get(Q.get(i));
                    ss = "(" + ss + ")*";
                    for (String string : Q) {
                        if (!string.equals(Q.get(i)) && table.get(Q.get(i)).get(string) != null) {
                            String dd = table.get(Q.get(i)).get(string);
                            table.get(Q.get(i)).replace(string, ss + dd);
                        }
                    }
                    if (!"∅".equals(Q.get(i)) && table.get(Q.get(i)).get("∅") != null) {
                            String dd = table.get(Q.get(i)).get("∅");
                            if("$".equals(dd)){
                                table.get(Q.get(i)).replace("∅", ss);
                            }else{
                                table.get(Q.get(i)).replace("∅", ss + dd);
                            }
                    }
                    table.get(Q.get(i)).remove(Q.get(i));
                }
                HashMap<String, String> asd = table.get(Q.get(i));
                for (String string : Q) {
                    if (table.get(string) != null) {
                        if (!string.equals(Q.get(i)) && table.get(string).get(Q.get(i)) != null) {
                            String dd = table.get(string).get(Q.get(i));
                            table.get(string).remove(Q.get(i));
                            for (String dos : Q) {
                                if (asd.get(dos) != null) {
                                    if(table.get(string).get(dos)==null){
                                        table.get(string).put(dos, dd+asd.get(dos));
                                    }else{
                                        String aux= table.get(string).get(dos);
                                        table.get(string).replace(dos,"("+ dd+asd.get(dos)+"U"+aux+")");                                        
                                    }
                                }
                            }
                            if (asd.get("∅") != null) {
                                    if(table.get(string).get("∅")==null){
                                        if("$".equals(asd.get("∅"))){
                                            table.get(Q.get(i)).put("∅", dd);
                                        }else{
                                           table.get(string).put("∅", dd+asd.get("∅")); 
                                        }                                                                                
                                    }else{
                                        String aux= table.get(string).get("∅");
                                        if("$".equals(asd.get("∅"))){
                                            table.get(string).replace("∅","("+ dd+"U"+aux+")");
                                        }else{
                                            table.get(string).replace("∅","("+ dd+asd.get("∅")+"U"+aux+")"); 
                                        }
                                                                
                                    }
                                }
                        }
                    }
                }
                table.remove(Q.get(i));
            }

        }
        // estados Q0 remplazo
        if (table.get(Q.get(0)) != null) {
            if (table.get(Q.get(0)).get(Q.get(0)) != null) {
                    String ss = table.get(Q.get(0)).get(Q.get(0));
                    ss = "(" + ss + ")*";
                    
                    if (!"∅".equals(Q.get(0)) && table.get(Q.get(0)).get("∅") != null) {
                            String dd = table.get(Q.get(0)).get("∅");
                            if("$".equals(dd)){
                                table.get(Q.get(0)).replace("∅", ss);
                            }else{
                                table.get(Q.get(0)).replace("∅", ss + dd);
                            }
                    }
                    table.get(Q.get(0)).remove(Q.get(0));
                }
            
        }
        expresion=table.get(Q.get(0)).get("∅");       

    }

    @Override
    public String toString() {
        return expresion;
    }

}
