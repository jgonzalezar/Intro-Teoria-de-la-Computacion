/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFD;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author fanat
 */
public class AFDtoKleen {

    String expresion;

    public AFDtoKleen(AFD afd) {
        HashMap<String, HashMap<String, String>> table = new HashMap<>();
        ArrayList<String> Q = new ArrayList<>();
        afd.eliminarEstadosInaccesibles();
        ArrayList<String> lim = afd.getEstadosLimbo();

        int q0 = afd.getQ0();
        if (lim.contains(afd.getQ().get(q0))) {
            expresion = "∅";
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

        for (int i = Q.size() - 1; i > 0; i--) {
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
                    if ( table.get(Q.get(i)).get("") != null) {
                        String dd = table.get(Q.get(i)).get("");
                        if("$".equals(dd)){
                            table.get(Q.get(i)).replace("", ss);
                        }else{
                            table.get(Q.get(i)).replace("", ss + dd);
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
                                    }
                                }
                            }
                        }
                    }
                }
                
                table.remove(Q.get(i));
            }

        }

    }

    @Override
    public String toString() {
        return expresion;
    }

}
