// The Right-Angle Triangle Checker


//        ALGORITHM STEPS

/*
Step 1:Start.
Step 2:Read angle1 and angle2.
Step 3:If angle1 ≤ 0 OR  angle2 ≤ 0 OR (angle1 + angle2) ≥ 180, print "Invalid input" and stop.
Step 4:Compute angle3 = 180.0 – (angle1 + angle2).
Step 5:If angle1 = 90.0 OR  angle2 = 90.0 OR angle3 = 90.0, the triangle is right-angled; otherwise it is not.
Step 6:Display angle1, angle2, angle3 and the result.
Step 7:End.
*/


//       C PROGRAM STEPS

#include <stdio.h>
#include <math.h>

int main(void) {
    double angle1, angle2, angle3;

    // Step 2: read the two known angles from the user
    printf("Enter the first angle of the triangle (in degrees): ");
    scanf("%lf", &angle1);
    printf("Enter the second angle of the triangle (in degrees): ");
    scanf("%lf", &angle2);

    // Step 3: reject any angle pair that cannot form a real triangle
    // (angle must be positive and their sum must be less than 180 degrees)
    if (angle1 <= 0 || angle2 <= 0 || (angle1 + angle2) >= 180) {
        printf("Invalid input. These two angles cannot form a valid triangle.\n");
        return 1; // Exit the program with an error code
    }

    // Step 4: the third angle is always 180 minus the other two angles
    // (angle-sum property of a triangle — not a free variable)
    angle3 = 180.0 - (angle1 + angle2);

    // Step 5: check all three angles — a triangle has at most one right angle, so this covers every possible case.
    // NOTE: A small tolerance (1e-9) is used instead of exact equality
    // because floating-point values may contain some tiny rounding errors(eg., 90.000000001 or 89.999999999).
    if (fabs(angle1 - 90.0) < 1e-9 || fabs(angle2 - 90.0) < 1e-9 || fabs(angle3 - 90.0) < 1e-9) {
        printf("This IS a right-angled triangle.\n");
    } else {
        printf("This is NOT a right-angled triangle.\n");
    }

    // Step 6: display all the three angles so it can be verified by the user.
    printf("The three angles are: %.2f, %.2f, %.2f\n", angle1, angle2, angle3);

    // Step 7: end the program.
    return 0;
}