// The Palindrome Checker


//      ALGORITHM STEPS

/*
Step 1: Start.
Step 2: Read number.
Step 3: Set n = |number| and original = n.
Step 4: Set reverse = 0.
Step 5: While n > 0: extract digit = n MOD 10; set reversed = reversed * 10 + digit; set n = n DIV 10.
Step 6: If reversed = original, the number is a palindrome; otherwise it is not.
Step 7: Display the result.
Step 8: End.
*/


//     C PROGRAM STEPS

#include <stdio.h>
#include <stdlib.h>   /* for Labs() — absolute value for long integers */


int main(void) {
    // Step 1: Start the program and declare the variables.
    // NOTE: assumes the input fits in a C "Long" (width is platform-dependent --
    // commonly 32-bit or 64-bit). Very large numbers can overflow silently.
    long number, n, original, reversedNum, digit;

    // Step 2: read the integer to check from the user.
    printf("Enter an integer: ");
    scanf("%ld", &number);

    // Step 3: strip the sign so only the digits are compared
    // (a minus sign is not a digit — see the stated assumption in the analysis).
    n = labs(number); // absolute value of the input number.
    original = n; // store the original value for later comparison.

    // Step 4: start with a digits reversed yet
    reversedNum = 0;

    // Step 5: peel digits off n one at a time and rebuild them in reverse order.
    // How it works: n % 10 extracts the last digit; n / 10 removes it from n;
    // reversedNum * 10 + digit shifts the accumulated reversed number left by
    // one place and appends the new digit. Repeat until no digits remain.
    while (n > 0) {
        digit = n % 10; // extract the last digit
        reversedNum = reversedNum * 10 + digit; // append it to the reversed number
        n /= 10; // remove the last digit from n
    }

    // Step 6-7: compare reversed digits to the original and display the result.
    if (reversedNum == original) {
        printf("%ld is a PALINDROME number.\n", number);
    } else {
        printf("%ld is NOT a palindrome number.\n", number);
    }

    // Step 8: end the program.
    return 0;
}