<!DOCTYPE html>
<html>
<head>
    <title>Car Dealership System</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col items-center py-10">

<div class="w-full max-w-md bg-white p-8 rounded-lg shadow-md">
    <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">Enter New Vehicle Details</h2>

    <form method="post" class="space-y-4">
        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Type:</label>
            <select name="type" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="car">Car</option>
                <option value="truck">Truck</option>
                <option value="motorcycle">Motorcycle</option>
            </select>
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Brand:</label>
            <input type="text" name="brand" required class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Model:</label>
            <input type="text" name="model" required class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Year:</label>
            <input type="number" name="year" required class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Price ($):</label>
            <input type="number" name="price" required class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Unique Attribute (Doors/Capacity/Handlebars):</label>
            <input type="text" name="unique_attr" required class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <input type="submit" name="submit" value="Add Vehicle" class="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded hover:bg-blue-700 cursor-pointer transition duration-200">
    </form>
</div>

<div class="w-full max-w-md mt-8">

<br/>

<?php
// Base Class
class Vehicle
{
    // Encapsulated properties
    protected $brand;
    protected $model;
    protected $year;
    protected $price;

    // Static property to count total vehicles
    public static $counter = 0;

    public function __construct($brand, $model, $year, $price)
    {
        $this->brand = $brand;
        $this->model = $model;
        $this->year = $year;
        $this->price = $price;
        // Increment the static counter each time an object is created
        self::$counter++;
    }

    public function displayInfo()
    {
        return "<strong>Brand:</strong> {$this->brand} | <strong>Model:</strong> {$this->model} | <strong>Year:</strong> {$this->year} | <strong>Price:</strong> \${$this->price}";
    }

    // Method to compare price with another vehicle
    public function comparePrice($otherVehicle)
    {
        if ($this->price > $otherVehicle->price) {
            return "The {$this->model} is more expensive than the {$otherVehicle->model}.";
        } elseif ($this->price < $otherVehicle->price) {
            return "The {$this->model} is cheaper than the {$otherVehicle->model}.";
        } else {
            return "Both vehicles have the same price.";
        }
    }
}

// Subclass: Car
class Car extends Vehicle
{
    private $doors;

    public function __construct($brand, $model, $year, $price, $doors)
    {
        // Call parent constructor to handle common data
        parent::__construct($brand, $model, $year, $price);
        $this->doors = $doors;
    }

    // Override displayInfo
    public function displayInfo()
    {
        // Reuse parent display logic
        return parent::displayInfo() .
            " | <strong>Doors:</strong> {$this->doors} <br>";
    }
}

// Subclass: Truck
class Truck extends Vehicle
{
    private $capacity;

    public function __construct($brand, $model, $year, $price, $capacity)
    {
        parent::__construct($brand, $model, $year, $price);
        $this->capacity = $capacity;
    }

    // Override displayInfo
    public function displayInfo()
    {
        return parent::displayInfo() .
            " | <strong>Cargo Capacity:</strong> {$this->capacity} lbs <br>";
    }
}

// Subclass: Motorcycle
class Motorcycle extends Vehicle
{
    private $handlebar;

    public function __construct($brand, $model, $year, $price, $handlebar)
    {
        parent::__construct($brand, $model, $year, $price);
        $this->handlebar = $handlebar;
    }

    // Override displayInfo
    public function displayInfo()
    {
        return parent::displayInfo() .
            " | <strong>Handlebar Type:</strong> {$this->handlebar} <br>";
    }
}

// Processing Form Input
if (isset($_POST["submit"])) {
    $type = $_POST["type"];
    $brand = $_POST["brand"];
    $model = $_POST["model"];
    $year = $_POST["year"];
    $price = $_POST["price"];
    $unique = $_POST["unique_attr"];

    // Instantiate based on type
    if ($type == "car") {
        $vehicle = new Car($brand, $model, $year, $price, $unique);
    } elseif ($type == "truck") {
        $vehicle = new Truck($brand, $model, $year, $price, $unique);
    } else {
        $vehicle = new Motorcycle($brand, $model, $year, $price, $unique);
    }

    echo "<h3>Vehicle Added Successfully</h3>";
    echo $vehicle->displayInfo();

    // We create a dummy comparison vehicle to demonstrate the comparison method
    $comparisonCar = new Car("Generic", "Standard Model", 2024, 20000, 4);
    echo "<br><em>Comparison Test:</em> " .
        $vehicle->comparePrice($comparisonCar);

    echo "<br><br><strong>Total Vehicles Created in Session:</strong> " .
        Vehicle::$counter;
}
?>

</div>
</body>
</html>
