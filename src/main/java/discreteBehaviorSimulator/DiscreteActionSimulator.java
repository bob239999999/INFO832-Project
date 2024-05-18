
package main.discreteBehaviorSimulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteActionInterface;


/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */
public class DiscreteActionSimulator implements Runnable {


    private Thread t;
    private boolean running = false;

    private Clock globalTime;

    private ArrayList<DiscreteActionInterface> actionsList = new ArrayList<>();

    private int nbLoop;
    private int step;

    private Logger logger;
    private FileHandler logFile;
    private ConsoleHandler logConsole;

    public DiscreteActionSimulator(){

        // Start logger
        this.logger = Logger.getLogger("DAS");
        this.logger.setLevel(Level.ALL);
        this.logger.setUseParentHandlers(true);
        try{
            this.logFile = new FileHandler(this.getClass().getName() + ".log");
            //this.logFile.setFormatter(new SimpleFormatter());
            this.logFile.setFormatter(new LogFormatter());
            this.logConsole = new ConsoleHandler();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.logger.addHandler(logFile);
        this.logger.addHandler(logConsole);


        this.globalTime = Clock.getInstance();

        this.t = new Thread(this);
        this.t.setName("Discrete Action Simulator");
    }

    /**
     * @param nbLoop defines the number of loop for the simulation, the simulation is infinite if nbLoop is negative or 0.
     */
    public void setNbLoop(int nbLoop){
        if(nbLoop>0){
            this.nbLoop = nbLoop;
            this.step = 1;
        }else{ // running infinitely
            this.nbLoop = 0;
            this.step = -1;
        }
    }

    public void addAction(DiscreteActionInterface c){

        if(c.hasNext()) {
            // add to list of actions, next is call to the action exist at the first time
            this.actionsList.add(c.next());

            // sort the list for ordered execution
            Collections.sort(this.actionsList);
        }
    }
	

    /**
     * @return the laps time before the next action
     */
    private int nextLapsTime() {
        DiscreteActionInterface currentAction = this.actionsList.get(0);
        return currentAction.getCurrentLapsTime();
    }
    /**
     * @return laps time of the running action
     */
    private int runAction() {
	    // Utilisation d'un modèle de chaîne de caractères pour les messages de journalisation
	    final String LOG_TEMPLATE_WITH_TIME = "[DAS] run action %s on %s:%d at %d after %d time units\n";
	    final String LOG_TEMPLATE_WITHOUT_TIME = "[DAS] run action %s on %s:%d after %d time units\n";
	
	    // Run the first action
	    int sleepTime = 0;
	    
	    DiscreteActionInterface currentAction = this.actionsList.get(0);
	    Object o = currentAction.getObject();
	    Method m = currentAction.getMethod();
	    sleepTime = currentAction.getCurrentLapsTime();
	    try {
	        // Thread.sleep(sleepTime); // Real time can be very slow
	        Thread.yield();
	        // Thread.sleep(1000); // Wait message bus sends
	        if (this.globalTime != null) {
	            this.globalTime.increase(sleepTime);
	        }
	        m.invoke(o);
	        
	        // Utilisation de String.format pour formater le message de journalisation, remplaçant ainsi la concaténation de chaînes
	        String logMessage;
	        if (this.globalTime != null) {
	            logMessage = String.format(LOG_TEMPLATE_WITH_TIME, m.getName(), o.getClass().getName(), o.hashCode(), this.globalTime.getTime(), sleepTime);
	        } else {
	            logMessage = String.format(LOG_TEMPLATE_WITHOUT_TIME, m.getName(), o.getClass().getName(), o.hashCode(), sleepTime);
	        }
	        this.logger.log(Level.FINE, logMessage); // Remplacement de System.out.println par une méthode de journalisation
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return sleepTime;
	}

    private void updateTimes(int runningTimeOf1stCapsul) {
    // Utilisation d'un modèle de chaîne de caractères pour les messages de journalisation
    final String LOG_TEMPLATE_WITH_TIME = "[DAS] reset action %s on %s:%d at %d to %d time units\n";
    final String LOG_TEMPLATE_WITHOUT_TIME = "[DAS] reset action %s on %s:%d to %d time units\n";

    // update time laps of all actions
    for (int i = 1; i < this.actionsList.size(); i++) {
        int d = this.actionsList.get(i).getLapsTime();
        this.actionsList.get(i).setLapsTemps(d - runningTimeOf1stCapsul);
        this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
    }

    // get new time lapse of first action
    if(this.globalTime == null) {
        this.actionsList.get(0).updateTimeLaps();
    }else {
        this.actionsList.get(0).updateTimeLaps(this.globalTime.getTime());
    }

    // remove the action if no more lapse time is defined
    if(this.actionsList.get(0).getLastLapsTime() == null) {
        this.actionsList.remove(0);
    }else {
        // resort the list
        Collections.sort(this.actionsList);
    }

    DiscreteActionInterface a = this.actionsList.remove(0);
    if (a.hasNext()) {
        a = a.next();
        this.actionsList.addElement(a);

        String logMessage;
        if (this.globalTime != null) {
            logMessage = String.format(LOG_TEMPLATE_WITH_TIME, a.getMethod().getName(), a.getObject().getClass().getName(), a.getObject().hashCode(), this.globalTime.getTime(), a.getCurrentLapsTime());
        } else {
            logMessage = String.format(LOG_TEMPLATE_WITHOUT_TIME, a.getMethod().getName(), a.getObject().getClass().getName(), a.getObject().hashCode(), a.getCurrentLapsTime());
        }
        
        this.logger.log(Level.FINE, logMessage); // Remplacement de System.out.println par une méthode de journalisation
        Collections.sort(this.actionsList);
    }
}


    public void run() {
        int count = this.nbLoop;
        boolean finished = false;

        System.out.println("LANCEMENT DU THREAD " + t.getName() + " \n");

        while(running && !finished){

            if(!this.actionsList.isEmpty()){
                System.out.println(this);
                this.globalTime.setNextJump(this.nextLapsTime());

                this.globalTime.lockWriteAccess();
                int runningTime = this.runAction();
                this.updateTimes(runningTime);
                this.globalTime.unlockWriteAccess();
                try {
                    Thread.sleep(100);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                //TODO add global time synchronizer for action with list of date and avoid drift
            }else{
                System.out.println("NOTHING TO DO\n");
                this.stop();
            }

            count -= this.step;
            if(count<=0) {
                finished = true;
            }
        }
        this.running = false;
        if(this.step>0) {
            System.out.println("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
        }else {
            System.out.println("DAS: " + (count) + " actions done!");
        }
    }

    public void start(){
        this.running = true;
        this.t.start();
    }

    public void stop(){
        System.out.println("STOP THREAD " + t.getName() + "obj " + this);
        this.running = false;
    }

    public String toString(){
        StringBuffer toS = new StringBuffer("------------------\nTestAuto :" + this.actionsList.size());
        for(DiscreteActionInterface c : this.actionsList){
            toS.append(c.toString() + "\n");
        }
        toS.append("---------------------\n");
        return toS.toString();
    }

    public boolean getRunning() {
        return this.running;
    }

}
