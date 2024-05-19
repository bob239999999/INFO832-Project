package timer;

public class PeriodicTimer implements Timer {

    private int period;
    private int next;
    private RandomTimer moreOrLess = null;

    public PeriodicTimer(int at) {
        this.period = at;
        this.next = at;
    }

    /**
     * Constructs a new PeriodicTimer that adjusts the time by a random factor.
     *
     * @param at the initial period
     * @param moreOrLess the timer that randomizes the next call time
     * @deprecated since "1.2", forRemoval = true. Use {@link MergedTimer} instead.
     */
    @Deprecated(since = "1.2", forRemoval = true)
    public PeriodicTimer(int at, RandomTimer moreOrLess) {
        this.period = at;
        this.moreOrLess = moreOrLess;
        this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
    }

    public PeriodicTimer(int period, int at) {
        this.period = period;
        this.next = at;
    }

    /**
     * Constructs a new PeriodicTimer with specified period, initial offset, and randomizer.
     *
     * @param period the period between events
     * @param at the initial time offset
     * @param moreOrLess the timer that randomizes the next call time
     * @deprecated since "1.2", forRemoval = true. Use {@link MergedTimer} instead.
     */
    @Deprecated(since = "1.2", forRemoval = true)
    public PeriodicTimer(int period, int at, RandomTimer moreOrLess) {
        this.period = period;
        this.moreOrLess = moreOrLess;
        this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
    }

    public int getPeriod() {
        return this.period;
    }


    @Override
    public Integer next() {
		int nextTime = this.next;

		if(this.moreOrLess != null) {
			this.next = this.period + Math.abs((int)(this.moreOrLess.next() - this.moreOrLess.getMean()));
		} else {
			this.next = this.period;
		}

		return nextTime;
	}

    @Override
    public boolean hasNext() {
        return true;
    }

}
