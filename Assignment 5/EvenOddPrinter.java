import java.util.Scanner;

class NumberPrinter {
    private int current = 0;
    private final int n;

    public NumberPrinter(int n) {
        this.n = n;
    }

    public synchronized void printEven() {
        while (current <= n) {
            try {
                if (current % 2 != 0) {
                    wait();
                } else {
                    if (current <= n) {
                        System.out.println("Even Thread: " + current);
                        current++;
                        notify();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void printOdd() {
        while (current <= n) {
            try {
                if (current % 2 == 0) {
                    wait();
                } else {
                    if (current <= n) {
                        System.out.println("Odd Thread: " + current);
                        current++;
                        notify();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class EvenOddPrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number n: ");
        int n = scanner.nextInt();
        scanner.close();

        NumberPrinter printer = new NumberPrinter(n);

        Thread evenThread = new Thread(printer::printEven);
        Thread oddThread = new Thread(printer::printOdd);

        evenThread.start();
        oddThread.start();

        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Printing complete.");
    }
}