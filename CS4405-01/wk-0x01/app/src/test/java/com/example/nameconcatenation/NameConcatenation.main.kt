#!/usr/bin/env kotlin

fun main() {
    // Array 1: First Names
    val firstNames = arrayOf(
        "James", "Joseph", "Art", "Len", "Don", "Sima", "Mitsue",
        "Leo", "Sage", "Krish", "Minna", "Abe", "Kyle", "Graciela",
        "Cammi", "Matt", "Mel", "Glady", "Yukee"
    )

    // Array 2: Last Names
    val lastNames = arrayOf(
        "Bhatt", "Darakjy", "Veere", "Paprocki", "Foller", "Morasca", "Toll",
        "Dilli", "Wiezer", "Marrier", "Amigo", "Maclead", "Caldarera", "Roota",
        "Albares", "Poquette", "Garufi", "Rim", "Whobrey"
    )

    // Array 3: Full Names (Concatenation)
    val fullNames = Array(firstNames.size) { i -> "${firstNames[i]} ${lastNames[i]}" }

    // Printing the arrays
    println("--- First Names Array ---")
    println(firstNames.joinToString(", "))

    println("\n--- Last Names Array ---")
    println(lastNames.joinToString(", "))

    println("\n--- Full Names Array ---")
    fullNames.forEach { println(it) }
}