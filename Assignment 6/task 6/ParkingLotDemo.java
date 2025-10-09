import java.util.concurrent.Semaphore;

public class ParkingLotDemo {
    static Semaphore spots = new Semaphore(3);

    public static void main(String[] args) {
        Runnable car = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting...");
                spots.acquire();
                System.out.println(Thread.currentThread().getName() + " parked");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " left");
                spots.release();
            } catch (InterruptedException e) { e.printStackTrace(); }
        };

        for (int i = 1; i <= 6; i++)
            new Thread(car, "Car-" + i).start();
    }
}
