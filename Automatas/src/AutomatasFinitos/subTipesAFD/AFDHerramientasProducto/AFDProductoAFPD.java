/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD.AFDHerramientasProducto;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFPD;
import AutomatasFinitos.Alfabeto;
import Herramientas.Transition;
import Herramientas.TransitionAFPD;
import Herramientas.Transitions;
import Herramientas.trioPila;
import java.util.ArrayList;

/**
 *
 * @author brandon
 */
public class AFDProductoAFPD extends AFPD {

    public AFDProductoAFPD(AFD afd, AFPD afpd) {

        Alfabeto afdSigma = afd.getSigma();
        Alfabeto afpdSigma = afpd.getSigma();
        if (!(afdSigma.toString().equals(afpdSigma.toString()))) {
            throw new Error("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
        }
        Sigma = afdSigma;
        //Genera Q
        ArrayList<String> afdQ = afd.getQ();
        ArrayList<String> afpdQ = afpd.getQ();
        Q = new ArrayList<>();
        for (int i = 0; i < afpdQ.size(); i++) {
            for (int j = 0; j < afdQ.size(); j++) {
                Q.add("(" + afpdQ.get(i) + "," + afdQ.get(j) + ")");
            }
        }
        q0 = Q.indexOf("(" + afpdQ.get(afd.getQ0()) + "," + afdQ.get(afpd.getQ0()) + ")");
        //Genera F
        ArrayList<Integer> afdF = afd.getF();
        ArrayList<Integer> afpdF = afpd.getF();
        F = new ArrayList<>();
        for (int i = 0; i < afpdQ.size(); i++) {
            for (int j = 0; j < afdQ.size(); j++) {
                if (afdF.contains(i) && afpdF.contains(j)) {
                    F.add(i * afdQ.size() + j);
                }
            }
        }
        //----Gamma----
        Gamma = afpd.getGamma();
        //Genera Delta
        Transitions afdDelta = afd.getDelta();
        Transitions afpdDelta = afpd.getDelta();
        Delta = new TransitionAFPD();
        for (String afpdStates : afpdQ) {
            trioPila trio = afpdDelta.cambios('$', afpdStates, '$');
            for (String afdStates1 : afdQ) {
                if (trio != null) {
                    for (String afdStates2 : afdQ) {
                        Delta.add('$', "(" + afpdStates + "," + afdStates1 + ")", trio.getPila(), "(" + trio.getDuo().getEstado() + "," + afdStates2 + ")", trio.getDuo().getPila());
                    }
                }

                for (int k = 0; k < Sigma.length(); k++) {
                    char a = Sigma.get(k);
                    String preDelta = "(" + afpdStates + "," + afdStates1 + ")";
                    trioPila trioAFPD;
                    trioAFPD = afpdDelta.cambios(a, preDelta, a);
                    if (trioAFPD != null) {
                        String postDelta = "(" + trioAFPD.getDuo().getEstado() + "," + afdDelta.cambio(a, afdStates1) + ")";
                        Delta.add(a, preDelta, a, postDelta, trioAFPD.getDuo().getPila());
                    }
                }
            }
        }
    }

}
