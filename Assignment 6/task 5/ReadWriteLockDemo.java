import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    static int data = 0;
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Runnable reader = () -> {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " read: " + data);
                Thread.sleep(200); // slightly shorter sleep
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } finally { 
                lock.readLock().unlock(); 
            }
        };

        Runnable writer = () -> {
            lock.writeLock().lock();
            try {
                data++;
                System.out.println(Thread.currentThread().getName() + " wrote: " + data);
                Thread.sleep(200);
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } finally { 
                lock.writeLock().unlock(); 
            }
        };

        // Create all threads
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            threads.add(new Thread(reader, "Reader-" + i));
        }
        for (int i = 1; i <= 10; i++) {
            threads.add(new Thread(writer, "Writer-" + i));
        }

        // Shuffle threads to mix readers and writers
        Collections.shuffle(threads);

        // Start all threads
        for (Thread t : threads) {
            t.start();
        }
    }
}
