// DOM elements
const taskInput = document.getElementById("taskInput");
const addTaskBtn = document.getElementById("addTaskBtn");
const taskList = document.getElementById("taskList");

// Task counter for unique IDs
let taskIdCounter = 1;

// Event listeners
addTaskBtn.addEventListener("click", addTask);
taskInput.addEventListener("keypress", function (e) {
  if (e.key === "Enter") {
    addTask();
  }
});

// Event delegation for task list
taskList.addEventListener("click", handleTaskActions);

// Add new task
function addTask() {
  const taskText = taskInput.value.trim();

  if (taskText === "") {
    alert("Please enter a task!");
    return;
  }

  const taskId = `task-${taskIdCounter++}`;
  const taskItem = createTaskElement(taskId, taskText, false);

  taskList.appendChild(taskItem);
  taskInput.value = "";
  taskInput.focus();
}

// Create task element
function createTaskElement(taskId, taskText, isCompleted) {
  const li = document.createElement("li");
  li.className = `task-item ${isCompleted ? "completed" : ""}`;
  li.dataset.taskId = taskId;

  li.innerHTML = `
        <input type="checkbox" class="task-checkbox" ${
          isCompleted ? "checked" : ""
        }>
        <span class="task-text">${escapeHtml(taskText)}</span>
        <div class="task-actions">
            <button class="edit-btn">Edit</button>
            <button class="remove-btn">Remove</button>
        </div>
    `;

  return li;
}

// Handle task actions using event delegation
function handleTaskActions(e) {
  const target = e.target;
  const taskItem = target.closest(".task-item");

  if (!taskItem) return;

  const taskId = taskItem.dataset.taskId;

  // Handle checkbox (complete/incomplete)
  if (target.classList.contains("task-checkbox")) {
    toggleTaskComplete(taskItem, target.checked);
  }

  // Handle edit button
  if (target.classList.contains("edit-btn")) {
    editTask(taskItem);
  }

  // Handle remove button
  if (target.classList.contains("remove-btn")) {
    removeTask(taskItem);
  }
}

// Toggle task completion
function toggleTaskComplete(taskItem, isCompleted) {
  if (isCompleted) {
    taskItem.classList.add("completed");
  } else {
    taskItem.classList.remove("completed");
  }
}

// Edit task
function editTask(taskItem) {
  const taskText = taskItem.querySelector(".task-text");
  const originalText = taskText.textContent;

  // Use native prompt dialog
  const newText = prompt("Edit task:", originalText);

  // Check if user clicked Cancel (prompt returns null) or entered empty text
  if (newText === null) {
    return; // User cancelled
  }

  const trimmedText = newText.trim();
  if (trimmedText === "") {
    alert("Task cannot be empty!");
    return;
  }

  // Update task text
  taskText.textContent = trimmedText;
}

// Remove task
function removeTask(taskItem) {
  if (confirm("Are you sure you want to remove this task?")) {
    taskItem.remove();
  }
}

// Utility function to escape HTML
function escapeHtml(text) {
  const div = document.createElement("div");
  div.textContent = text;
  return div.innerHTML;
}

// Add some sample tasks for demonstration
document.addEventListener("DOMContentLoaded", function () {
  // Add a couple of sample tasks
  const sampleTasks = [
    { text: "Finish CS2205 programming assignment", completed: true },
    { text: "Buy vegetables", completed: false },
  ];

  sampleTasks.forEach((task) => {
    const taskId = `task-${taskIdCounter++}`;
    const taskItem = createTaskElement(taskId, task.text, task.completed);
    taskList.appendChild(taskItem);
  });
});
