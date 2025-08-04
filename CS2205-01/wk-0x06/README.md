# Bookstore Inventory Management System

This project demonstrates the implementation of data interchange formats (XML and JSON) for a bookstore inventory management system. The project includes XML Schema definition, JSON data structures, and an HTML interface for displaying the inventory.

## Files Overview

### 1. XML Schema (bookstore_schema.xsd)

- **Purpose**: Defines the structure and validation rules for XML bookstore catalog data
- **Root Element**: `catalog`
- **Key Elements**:
  - `book` with attributes `id` (string) and `available` (boolean)
  - `title` element containing the book title and `publicationYear` (integer)
  - `author` element with `firstName` and `lastName` (both strings)

### 2. XML Catalog (bookstore_catalog.xml)

- **Purpose**: Sample XML data that conforms to the schema
- **Content**: Three book entries demonstrating the XML structure
- **Books Included**:
  - The Great Gatsby (F. Scott Fitzgerald, 1925)
  - To Kill a Mockingbird (Harper Lee, 1960)
  - 1984 (George Orwell, 1949)

### 3. JSON Inventory (bookstore_inventory.json)

- **Purpose**: JSON data structure representing the bookstore inventory
- **Structure**:
  - Array of books
  - Each book contains:
    - `title` (string)
    - `author` object with `firstName` and `lastName`
    - `publicationYear` (integer)
    - `details` object with `publisher`, `pageCount`, and `available`

### 4. HTML Interface (bookstore_inventory.html)

- **Purpose**: Modern, responsive web interface for displaying inventory
- **Features**:
  - Beautiful gradient design
  - Interactive statistics dashboard
  - Responsive table layout
  - Color-coded availability status
  - Loading states and error handling

## Data Structure Comparison

### XML Structure

```xml
<catalog>
  <book id="B001" available="true">
    <title>
      Book Title
      <publicationYear>1925</publicationYear>
    </title>
    <author>
      <firstName>Author</firstName>
      <lastName>Name</lastName>
    </author>
  </book>
</catalog>
```

### JSON Structure

```json
{
  "books": [
    {
      "title": "Book Title",
      "author": {
        "firstName": "Author",
        "lastName": "Name"
      },
      "publicationYear": 1925,
      "details": {
        "publisher": "Publisher Name",
        "pageCount": 218,
        "available": true
      }
    }
  ]
}
```

## Key Features

### XML Schema Benefits

- **Validation**: Ensures data integrity and structure
- **Type Safety**: Enforces data types (string, integer, boolean)
- **Documentation**: Self-documenting structure
- **Interoperability**: Standard format for data exchange

### JSON Benefits

- **Lightweight**: Smaller file size compared to XML
- **JavaScript Native**: Easy to parse and manipulate in web applications
- **Human Readable**: Clear, concise format
- **Flexible**: Easy to extend and modify

### HTML Interface Features

- **Modern Design**: Gradient backgrounds and card-based layout
- **Responsive**: Works on desktop and mobile devices
- **Interactive**: Hover effects and dynamic content loading
- **Statistics**: Real-time calculation of inventory metrics
- **Error Handling**: Graceful handling of data loading errors

## Usage

1. **View the HTML Interface**: Open `bookstore_inventory.html` in a web browser
2. **Validate XML**: Use the `bookstore_schema.xsd` to validate `bookstore_catalog.xml`
3. **Process JSON**: Use the JSON data in `bookstore_inventory.json` for API integration

## Technical Implementation

### XML Schema Design

- Uses W3C XML Schema namespace
- Defines complex types for reusability
- Enforces required attributes and elements
- Supports multiple book entries

### JSON Data Design

- Follows REST API conventions
- Nested object structure for related data
- Boolean values for availability status
- Consistent naming conventions

### HTML/CSS/JavaScript

- Modern CSS Grid and Flexbox layouts
- JavaScript ES6+ features
- Responsive design principles
- Progressive enhancement approach

## Browser Compatibility

- Chrome 60+
- Firefox 55+
- Safari 12+
- Edge 79+

## Future Enhancements

- Add search and filter functionality
- Implement sorting capabilities
- Add pagination for large datasets
- Integrate with backend APIs
- Add data export features
