public class Factorial {
    public static void main(String[] args) {
        System.out.println(iterativeFactorial(20));
        System.out.println(recursiveFactorial(20));
    }

    // --- Iterative Factorial ---
    // This function calculates the factorial using a loop.
    // All variables (n, res, i) are local to the function and stored on the STACK.
    // The STACK usage is minimal and does not grow with the input size beyond the fixed-size stack frame.
    public static long iterativeFactorial(long n) {
        // 'res' and 'i' are local variables allocated on the STACK.
        // When this function is called, a single stack frame is created to hold 'n', 'res', and 'i'.
        // No memory is allocated on the HEAP, as no dynamic memory allocation (e.g., 'new') is used.
        long res = 1;
        if (n <= 1)
            return 1;
        for (long i = 2; i <= n; i++) {
            res = res * i;
        }
        // When the function returns, the stack frame is automatically removed, freeing the memory.
        return res;
    }

    // --- Recursive Factorial ---
    // This function calls itself to calculate the factorial.
    // Each recursive call creates a new stack frame on the STACK.
    public static long recursiveFactorial(long n) {
        // 'n' and 'res' are local variables stored on the STACK.
        // Each recursive call creates a new stack frame containing its own instance of 'n' and 'res'.
        // The HEAP is NOT used, as no dynamic memory allocation occurs.
        long res = 1;
        if (n <= 1)
            // Base case: stops recursion, no further stack frames are created.
            return 1;
        else
            // This recursive call creates a new stack frame for recursiveFactorial(n-1).
            // The stack grows with each recursive call, proportional to the input 'n'.
            // Stack frames are automatically removed as recursion unwinds when the base case is reached.
            return n * recursiveFactorial(n - 1);
    }
}