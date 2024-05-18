package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

import timer.DateTimer;
import timer.Timer;

/**
 * @author flver
 *
 */
public class DiscreteActionOnOffDependent implements DiscreteActionInterface {

    protected DiscreteActionInterface onAction;
    protected DiscreteActionInterface offAction;
    protected DiscreteActionInterface currentAction;

    private Integer lastOffDelay=0;

    /**
     * Construct an On Off dependence, first action (method) called is On, then method nextMethod() is called to select the next action.
     * The default behavior of nextMethod() is to switch between On and Off actions.  It can be change by overloading.
     *
     * @param o L'objet sur lequel la méthode opère.
     * @param on Nom de la méthode pour l'action 'on'.
     * @param timerOn Timer pour l'action 'on'.
     * @param off Nom de la méthode pour l'action 'off'.
     * @param timerOff Timer pour l'action 'off'.
     */


    public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
        this.onAction = new DiscreteAction(o, on, timerOn);
        this.offAction = new DiscreteAction(o, off, timerOff);

        this.currentAction = this.offAction;

    }



    public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){

        this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
        this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));



        if(datesOn.first() < datesOff.first()){
            this.currentAction = this.onAction;
        }else{
            this.currentAction = this.offAction;
        }
    }

    public void nextAction(){
        if (this.currentAction == this.onAction){
            this.currentAction = this.offAction;
            this.currentAction = this.currentAction.next();
            this.lastOffDelay = this.currentAction.getCurrentLapsTime();
        }else{
            this.currentAction = this.onAction;
            this.currentAction = this.currentAction.next();
            this.currentAction.spendTime(this.lastOffDelay);
        }
    }

    public	void spendTime(int t) {
        this.currentAction.spendTime(t);
    }

    public Method getMethod() {
        return this.currentAction.getMethod();
    }

    public Integer getCurrentLapsTime() {
        return this.currentAction.getCurrentLapsTime();
    }

    public Object getObject() {
        return this.currentAction.getObject();
    }

    public int compareTo(DiscreteActionInterface c) {
        return this.currentAction.compareTo(c);
    }

    public DiscreteActionInterface next() {
        this.nextAction();
        return this;
    }

    public boolean hasNext() {
        return this.onAction.hasNext() || this.offAction.hasNext();
    }



}
