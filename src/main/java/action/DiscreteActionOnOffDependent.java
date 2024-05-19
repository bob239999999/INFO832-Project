package action;

import java.lang.reflect.Method;
import java.util.SortedSet;

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
     * Constructs a dependent action system with 'on' and 'off' states.
     *
     * @param o Object on which the method operates.
     * @param on Method name for the 'on' action.
     * @param timerOn Timer for the 'on' action.
     * @param off Method name for the 'off' action.
     * @param timerOff Timer for the 'off' action.
     */


    public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
        this.onAction = new DiscreteAction(o, on, timerOn);
        this.offAction = new DiscreteAction(o, off, timerOff);

        this.currentAction = this.offAction;

    }



    public DiscreteActionOnOffDependent(Object o, String on, SortedSet<Integer> datesOn, String off, SortedSet<Integer> datesOff){

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
