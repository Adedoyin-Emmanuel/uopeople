<?php

/**
 * get-demo.php
 * Intent: show how GET makes parameters visible and shareable.
 * Example URL: get-demo.php?q=hello
 */
$q = isset($_GET["q"]) ? trim($_GET["q"]) : "";
echo "<h2>Search demo</h2>";
if ($q === "") {
    echo "<p>No query supplied. Try ?q=php</p>";
} else {
    // Escape output to avoid XSS
    echo "<p>Search term: " .
        htmlspecialchars($q, ENT_QUOTES, "UTF-8") .
        "</p>";
}
