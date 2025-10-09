import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockDemo {
    static ReentrantLock resource1 = new ReentrantLock();
    static ReentrantLock resource2 = new ReentrantLock();

    public static void main(String[] args) {
        Runnable threadA = () -> tryLockResources("Thread A", resource1, resource2);
        Runnable threadB = () -> tryLockResources("Thread B", resource2, resource1);

        new Thread(threadA).start();
        new Thread(threadB).start();
    }

    static void tryLockResources(String threadName, ReentrantLock first, ReentrantLock second) {
        try {
            if (first.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    System.out.println(threadName + ": locked first resource");
                    Thread.sleep(500);
                    if (second.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println(threadName + ": locked second resource");
                        } finally { second.unlock(); }
                    } else {
                        System.out.println(threadName + ": could not lock second resource, retrying...");
                    }
                } finally { first.unlock(); }
            } else {
                System.out.println(threadName + ": could not lock first resource, retrying...");
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
