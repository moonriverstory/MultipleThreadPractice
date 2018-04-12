package volatile_;

public class VisibleTask implements Runnable{
    public volatile boolean running = true;
    int i = 0;

    @Override
    public void run() {
        while (running){
            i++;
        }
    }
}
