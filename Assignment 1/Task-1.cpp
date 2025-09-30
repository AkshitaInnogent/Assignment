// --- Iterative Factorial ---

// This function calculates the factorial using a loop.
// All variables here are local to the function and are allocated on the STACK.
// The STACK usage is minimal and does not grow with the input number.

long long iterativeFactorial(int number) {

    // 'number', 'result', and 'i' are all local variables stored on the STACK.
    // When this function is called, a new "stack frame" is created to hold these variables.
// When the function finishes, this stack frame is automatically removed.

    long long result = 1;
    for (int i = 1; i <= number; ++i) {
        result *= i;
}

    return result;
}



// --- Recursive Factorial ---


// This function calls itself to calculate the factorial.
// Each function call creates a new stack frame on the STACK.

long long recursiveFactorial(int number) {


    // 'number' is a local variable stored on the STACK.
    // Each time this function is called, a new instance of 'number' is pushed onto the stack.
    
    // The HEAP is NOT used by this program, as we are not dynamically allocating memory.

    if (number <= 1) {
        // This is the base case. The recursion stops here.
        return 1;
    }
    // This line causes a new recursive call, creating a new stack frame.
    return number * recursiveFactorial(number - 1);
}
