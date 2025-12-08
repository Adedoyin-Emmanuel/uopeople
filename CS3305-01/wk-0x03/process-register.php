<?php

/**
 * process_register.php
 * Intent: validate and securely store a new user. Use PDO + prepared statements
 * to prevent SQL injection, and password_hash to avoid storing plain text.
 */
$errors = [];

// Helper funciton: sanitize user-visible output to avoid XSS
function e($str)
{
    return htmlspecialchars($str, ENT_QUOTES, "UTF-8");
}

// Capture and trim inputs
$username = isset($_POST["username"]) ? trim($_POST["username"]) : "";
$email = isset($_POST["email"]) ? trim($_POST["email"]) : "";
$password = isset($_POST["password"]) ? $_POST["password"] : "";
$confirm = isset($_POST["confirm_password"]) ? $_POST["confirm_password"] : "";

// Validate required fields
if ($username === "") {
    $errors[] = "Username is required.";
}
if ($email === "") {
    $errors[] = "Email is required.";
}
if ($password === "") {
    $errors[] = "Password is required.";
}
if ($confirm === "") {
    $errors[] = "Please confirm your password.";
}

// Validate email format
if ($email !== "" && !filter_var($email, FILTER_VALIDATE_EMAIL)) {
    $errors[] = "Please enter a valid email address.";
}

// Password policy: at least 8 chars, one digit, one special char
$pwPolicy = '/^(?=.*[0-9])(?=.*[\W_]).{8,}$/';
if ($password !== "" && !preg_match($pwPolicy, $password)) {
    $errors[] =
        "Password must be at least 8 characters and include a number and a special character.";
}

// Confirm password match
if ($password !== "" && $confirm !== "" && $password !== $confirm) {
    $errors[] = "Password and confirm password do not match.";
}

// If errors exist, show them; otherwise safe to proceed
if (!empty($errors)) {
    echo "<h3>Errors</h3><ul>";
    foreach ($errors as $err) {
        echo "<li>" . e($err) . "</li>";
    }
    echo "</ul><p>Please fix the above and try again.</p>";
    exit();
}

// At this point inputs are validated. Hash password for safe storage.
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// Example DB insert using PDO and prepared statements.
// Intent: avoid SQL injection by binding values; use descriptive names.
try {
    // Replace with your real DB settings when testing locally
    $pdo = new PDO(
        "mysql:host=localhost;dbname=community",
        "dbuser",
        "dbpass",
        [
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
        ],
    );

    $stmt = $pdo->prepare(
        "INSERT INTO users (username, email, password_hash) VALUES (:username, :email, :phash)",
    );
    $stmt->execute([
        ":username" => $username,
        ":email" => $email,
        ":phash" => $hashedPassword,
    ]);

    echo "<p>Registration successful. Welcome, " . e($username) . ".</p>";
} catch (PDOException $ex) {
    // Intent: do not leak DB details. Log server-side; show generic message to user.
    error_log("DB error: " . $ex->getMessage());
    echo "<p>There was a problem saving your account. Try again later.</p>";
}
