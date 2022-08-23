public class Timer {
    private int delay;

    public Timer(int delay){
        this.delay = delay;
    }

    public void extendTimer(){
        delay *= 1.5;
    }

    public int getDelay(){
        return delay;
    }
}
