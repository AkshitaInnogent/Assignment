import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        System.out.println("Main: Starting workers...");

        new Thread(new Worker("Worker-1", 2000, latch)).start();
        new Thread(new Worker("Worker-2", 3000, latch)).start();
        new Thread(new Worker("Worker-3", 1500, latch)).start();

        try {
            latch.await();
            System.out.println("Main: All workers done. Continue...");
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}

class Worker implements Runnable {
    private String name;
    private int time;
    private CountDownLatch latch;

    Worker(String name, int time, CountDownLatch latch) {
        this.name = name; this.time = time; this.latch = latch;
    }

    public void run() {
        System.out.println(name + " started.");
        try { Thread.sleep(time); } catch (InterruptedException e) {}
        System.out.println(name + " finished.");
        latch.countDown();
    }
}
