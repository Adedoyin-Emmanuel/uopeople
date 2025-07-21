# To-Do List Application

A modern, interactive to-do list application built with HTML, CSS, and JavaScript that demonstrates event handling and event delegation.

## Features

### ✅ Add New Tasks

- Input field for entering new task text
- "Add Task" button to add tasks to the list
- Enter key support for quick task addition
- Input validation (empty tasks are not allowed)

### ✏️ Edit Existing Tasks

- "Edit" button for each task
- Native JavaScript prompt dialog for editing
- Pre-filled with current task text
- Cancel option to discard changes
- Input validation (empty tasks are not allowed)

### 🗑️ Remove Tasks

- "Remove" button for each task
- Confirmation dialog before deletion
- Immediate removal from the DOM

### ☑️ Mark Tasks as Complete/Incomplete

- Checkbox for each task
- Visual feedback with strike-through text for completed tasks
- Background color change for completed tasks
- Toggle functionality (can uncheck completed tasks)

## Technical Implementation

### Event Delegation

The application uses event delegation to efficiently handle all task-related interactions:

- Single event listener on the task list container
- Handles clicks on checkboxes, edit buttons, remove buttons, save buttons, and cancel buttons
- Reduces memory usage and improves performance

### DOM Manipulation

- Dynamic creation of task elements
- Native prompt dialog for task editing
- Visual state management for completed tasks
- Proper HTML escaping for security

### Responsive Design

- Clean, modern UI with consistent styling
- Mobile-friendly layout
- Hover effects for better user experience
- Color-coded buttons for different actions

## File Structure

```
wk-0x04/
├── index.html      # Main HTML structure
├── styles.css      # CSS styling and layout
├── script.js       # JavaScript functionality
└── README.md       # This documentation
```

## Usage

1. Open `index.html` in a web browser
2. The application loads with two sample tasks
3. Use the input field and "Add Task" button to add new tasks
4. Click checkboxes to mark tasks as complete/incomplete
5. Click "Edit" to modify task text
6. Click "Remove" to delete tasks

## Browser Compatibility

- Modern browsers with ES6+ support
- Chrome, Firefox, Safari, Edge
- Mobile browsers supported
