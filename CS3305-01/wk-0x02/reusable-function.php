
<?php
// ----------------------------------------------
// PART 1: REUSABLE FUNCTIONS
// ----------------------------------------------

function calculateTotal($price, $quantity)
{
    $subtotal = $price * $quantity;
    $tax = $subtotal * 0.1;
    return $subtotal + $tax;
}

function formatProductName($name)
{
    $name = trim($name);
    $name = ucwords(strtolower($name));
    if (strlen($name) > 50) {
        $name = substr($name, 0, 50);
    }
    return $name;
}

function calculateDiscount($price, $discountPercent)
{
    $discount = $price * ($discountPercent / 100);
    return $price - $discount;
}

// ----------------------------------------------
// PART 2: ARRAY HANDLING
// ----------------------------------------------

$products = [
    ["name" => "Laptop", "price" => 1200],
    ["name" => "Phone", "price" => 800],
    ["name" => "Laptop", "price" => 1200],
];

$products = array_unique($products, SORT_REGULAR);

usort($products, function ($a, $b) {
    return $a["price"] <=> $b["price"];
});

foreach ($products as $p) {
    echo $p["name"] . " - $" . $p["price"] . "\n";
}

$electronics = [
    ["name" => "Camera", "category" => "Electronics", "price" => 500],
    ["name" => "Shoes", "category" => "Fashion", "price" => 60],
    ["name" => "Headphones", "category" => "Electronics", "price" => 150],
];

foreach ($electronics as &$item) {
    if ($item["category"] === "Electronics") {
        $item["price"] = calculateDiscount($item["price"], 10);
    }
}

foreach ($electronics as $item) {
    echo $item["name"] .
        " - " .
        $item["category"] .
        " - $" .
        $item["price"] .
        "\n";
}

$supplierA = ["Laptop", "Tablet", "Camera"];
$supplierB = ["Camera", "Phone", "Speaker"];

$merged = array_unique(array_merge($supplierA, $supplierB));
print_r($merged);

// ----------------------------------------------
// PART 3: STRING PROCESSING
// ----------------------------------------------

$description = "HIGH_QUALITY_PRODUCT_DESCRIPTION";
$description = strtolower($description);
$description = str_replace("_", " ", $description);

echo $description . "\n";

$desc = "This is a high-quality leather wallet with RFID protection.";

echo "Characters: " . strlen($desc) . "\n";
echo "Words: " . str_word_count($desc) . "\n";

if (strpos(strtolower($desc), "leather") !== false) {
    echo "Keyword found\n";
} else {
    echo "Keyword not found\n";
}

$review = "Great product! Fast delivery and excellent service.";
$preview = substr($review, 0, 20) . "...";

echo $preview . "\n";

$pos = strpos(strtolower($review), "excellent");
echo "Position of 'excellent': " . $pos . "\n";

$fullReview = $review . " Thank you for your feedback!";

echo $fullReview . "\n";


?>
