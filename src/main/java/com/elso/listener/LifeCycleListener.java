/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.elso.listener;

/**
 *
 * @author pcci967
 */
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LifeCycleListener implements PhaseListener {

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
        System.out.println("START PHASE " + event.getPhaseId());
    }

    public void afterPhase(PhaseEvent event) {
        System.out.println("END PHASE " + event.getPhaseId());
    }

}
