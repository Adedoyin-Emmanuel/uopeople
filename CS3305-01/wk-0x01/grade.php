<?php
// Loop through 5 students
for ($student = 1; $student <= 5; $student++) {
    echo "Student #$student\n";

    // Input exam scores
    $score1 = (int) readline("Enter exam 1 score: ");
    $score2 = (int) readline("Enter exam 2 score: ");
    $score3 = (int) readline("Enter exam 3 score: ");

    // Calculate average
    $average = ($score1 + $score2 + $score3) / 3;

    // Calculate percentage out of 300
    $percentage = (($score1 + $score2 + $score3) / 300) * 100;

    // Input five subject scores to check failures
    $failCount = 0;
    for ($i = 1; $i <= 5; $i++) {
        $mark = (int) readline("Enter subject $i mark: ");
        if ($mark < 50) {
            $failCount++;
        }
    }

    // Academic probation if more than two fails
    if ($failCount > 2) {
        echo "Warning: Student is placed on academic probation.\n";
    }

    // Pass or fail based on average
    if ($average >= 50) {
        echo "Result: Pass\n";
    } else {
        echo "Result: Fail\n";
    }

    // Honor roll check
    if ($average > 90 && ($score1 > 95 || $score2 > 95 || $score3 > 95)) {
        echo "Congratulations! Qualifies for Honor Roll.\n";
    }

    // Display calculated values
    echo "Average = $average\n";
    echo "Percentage = $percentage%\n";
    echo "--------------------------\n";
}
?>
