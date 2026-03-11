//
//  ContentView.swift
//  swift-todo-app
//
//  Created by Mac on 11/03/2026.
//

import SwiftUI

// MARK: - ToDo Struct
struct ToDo: Identifiable {
    let id = UUID()
    var toDoId: Int
    var toDoTitle: String
    var toDoStatus: String // "Completed" or "Pending"
}

// MARK: - Filter Function
/// Filters the array to return only items with an even toDoId and "Completed" status.
/// This represents the completed tests from TestSet 2 (even days).
func filterEvenCompletedToDos(_ todos: [ToDo]) -> [ToDo] {
    return todos.filter { $0.toDoId % 2 == 0 && $0.toDoStatus == "Completed" }
}

// MARK: - Sample Data
let sampleToDos: [ToDo] = [
    ToDo(toDoId: 1, toDoTitle: "Run TestSet 1 - Day 1: Unit Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 2, toDoTitle: "Run TestSet 2 - Day 2: Integration Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 3, toDoTitle: "Run TestSet 1 - Day 3: Regression Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 4, toDoTitle: "Run TestSet 2 - Day 4: Performance Tests", toDoStatus: "Pending"),
    ToDo(toDoId: 5, toDoTitle: "Run TestSet 1 - Day 5: Security Tests", toDoStatus: "Pending"),
    ToDo(toDoId: 6, toDoTitle: "Run TestSet 2 - Day 6: Load Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 7, toDoTitle: "Run TestSet 1 - Day 7: Smoke Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 8, toDoTitle: "Run TestSet 2 - Day 8: API Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 9, toDoTitle: "Run TestSet 1 - Day 9: UI Tests", toDoStatus: "Pending"),
    ToDo(toDoId: 10, toDoTitle: "Run TestSet 2 - Day 10: Stress Tests", toDoStatus: "Pending"),
    ToDo(toDoId: 11, toDoTitle: "Run TestSet 1 - Day 11: Compatibility Tests", toDoStatus: "Completed"),
    ToDo(toDoId: 12, toDoTitle: "Run TestSet 2 - Day 12: End-to-End Tests", toDoStatus: "Completed"),
]

// MARK: - Views
struct ContentView: View {
    @State private var showFilteredOnly = false

    private var displayedToDos: [ToDo] {
        showFilteredOnly ? filterEvenCompletedToDos(sampleToDos) : sampleToDos
    }

    var body: some View {
        NavigationStack {
            VStack {
                // Toggle filter
                Toggle("Show TestSet 2 Completed Only", isOn: $showFilteredOnly)
                    .padding(.horizontal)
                    .padding(.top, 8)

                if showFilteredOnly {
                    Text("Showing \(displayedToDos.count) completed even-day tests")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .padding(.bottom, 4)
                }

                // To-Do List
                List(displayedToDos) { todo in
                    HStack {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(todo.toDoTitle)
                                .font(.body)
                            Text("Day \(todo.toDoId)")
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        Spacer()
                        Text(todo.toDoStatus)
                            .font(.caption)
                            .fontWeight(.semibold)
                            .padding(.horizontal, 8)
                            .padding(.vertical, 4)
                            .background(
                                todo.toDoStatus == "Completed"
                                    ? Color.green.opacity(0.2)
                                    : Color.orange.opacity(0.2)
                            )
                            .foregroundColor(
                                todo.toDoStatus == "Completed" ? .green : .orange
                            )
                            .cornerRadius(6)
                    }
                    .padding(.vertical, 4)
                }
                .listStyle(.plain)
            }
            .navigationTitle("Test Tracker")
        }
    }
}

#Preview {
    ContentView()
}
