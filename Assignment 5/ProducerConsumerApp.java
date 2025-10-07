import java.util.LinkedList;
import java.util.Scanner;

class ProducerConsumer {
    private LinkedList<Integer> buffer = new LinkedList<>();
    private int capacity;
    private int totalItems;

    public ProducerConsumer(int capacity, int totalItems) {
        this.capacity = capacity;
        this.totalItems = totalItems;
    }

    public void produce() throws InterruptedException {
        for (int value = 0; value < totalItems; value++) {
            synchronized (this) {
                while (buffer.size() == capacity) {
                    wait(); // wait if buffer is full
                }
                buffer.add(value);
                System.out.println("Produced: " + value + " | Buffer: " + buffer);
                notifyAll(); // notify consumer
            }
            Thread.sleep(500); // simulate production delay
        }
    }

    public void consume() throws InterruptedException {
        int consumedCount = 0;
        while (consumedCount < totalItems) {
            synchronized (this) {
                while (buffer.isEmpty()) {
                    wait(); // wait if buffer is empty
                }
                int val = buffer.removeFirst();
                consumedCount++;
                System.out.println("Consumed: " + val + " | Buffer: " + buffer);
                notifyAll(); // notify producer
            }
            Thread.sleep(500); // simulate consumption delay
        }
    }
}

public class ProducerConsumerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int capacity = 0, totalItems = 0;

        // User input for buffer capacity
        while (capacity <= 0) {
            System.out.print("Enter buffer capacity (>0): ");
            capacity = sc.nextInt();
        }

        // User input for total items
        while (totalItems <= 0) {
            System.out.print("Enter total number of items to produce/consume (>0): ");
            totalItems = sc.nextInt();
        }

        ProducerConsumer pc = new ProducerConsumer(capacity, totalItems);

        Thread producerThread = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Producer");

        Thread consumerThread = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer");

        producerThread.start();
        consumerThread.start();

        // Ensure main waits for both threads to finish
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All items produced and consumed successfully!");
    }
}
