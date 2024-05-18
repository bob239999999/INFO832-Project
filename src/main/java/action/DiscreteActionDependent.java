package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * Manages a sequence of dependent actions, starting with a base action and cycling through additional actions.
 */
public class DiscreteActionDependent implements DiscreteActionInterface {

    private final DiscreteAction baseAction;
    private final TreeSet<DiscreteAction> dependentActions;
    private Iterator<DiscreteAction> actionIterator;
    private DiscreteAction currentAction;

    /**
     * Constructs a series of dependent actions. The first action called is identified by baseMethodName.
     *
     * @param o Object on which the base method operates
     * @param baseMethodName Name of the base method
     * @param timerBase Timer associated with the base action
     */
    public DiscreteActionDependent(Object o, String baseMethodName, Timer timerBase) {
        this.baseAction = new DiscreteAction(o, baseMethodName, timerBase);
        this.dependentActions = new TreeSet<>();
        this.actionIterator = this.dependentActions.iterator();
        this.currentAction = this.baseAction;
    }

    public void addDependence(Object o, String dependentMethodName, Timer timerDependence) {
        this.dependentActions.add(new DiscreteAction(o, dependentMethodName, timerDependence));
        this.actionIterator = this.dependentActions.iterator();  // Reinitialize the iterator
    }

    private void reinitializeIterator() {
        this.actionIterator = this.dependentActions.iterator();
    }

    public void nextMethod() {
        if (this.currentAction.equals(this.baseAction) && !this.dependentActions.isEmpty()) {
            this.currentAction = this.actionIterator.next();
        } else if (this.currentAction.equals(this.dependentActions.last())) {
            this.currentAction = this.baseAction;
            reinitializeIterator();
        } else if (this.actionIterator.hasNext()) {
            this.currentAction = this.actionIterator.next();
        }
    }

    public void spendTime(int t) {
        this.dependentActions.forEach(action -> action.spendTime(t));
    }

    public void updateTimeLaps() {
        this.nextMethod();  // Advances to the next method, effectively updating time laps
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

    public Boolean isEmpty() {
        return !this.hasNext();
    }

    public DiscreteActionInterface next() {
        this.nextMethod();
        return this;
    }

    public boolean hasNext() {
        return this.baseAction.hasNext() || !this.dependentActions.isEmpty();
    }

}
