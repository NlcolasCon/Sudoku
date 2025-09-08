# Sudoku Game

A Java implementation of the classic **Sudoku** puzzle with support for both **4x4** and **9x9** boards.  
The program loads a Sudoku puzzle from a file, validates it, and allows the user to play interactively through the console.

---

## Project Explanation
Sudoku is a logic-based number placement puzzle. The goal is to fill an `N x N` grid (where `N = 4` or `N = 9`) so that:
1. Each row contains every number exactly once.
2. Each column contains every number exactly once.
3. Each subgrid (`√N x √N`) contains every number exactly once.

This implementation supports:
- Reading puzzles from a text file (`input.txt`) containing given values, user-saved progress, or empty cells.
- Displaying the Sudoku grid in a **formatted text layout**.
- Interactive play with commands to insert, clear, or save-and-exit.
- Validity checks to prevent illegal moves.

The project demonstrates:
- Object-Oriented Programming (OOP) in Java
- File I/O and input validation
- 2D array manipulation
- Class-based modular design (`Board`, `Sudoku`, `UserChoice`)

---

## Repository Structure
Sudoku-Game/
┣ src/
┃ ┣ Board.java
┃ ┣ Sudoku.java
┃ ┗ UserChoice.java
┣ lib/
┃ ┗ stdlib.jar
┣ LICENSE
┣ README.md

---

## Features
- Load Sudoku puzzles from file
- Interactive text-based gameplay
- Input validation with error messages
- Supports **4x4** and **9x9** grids
- Save game progress to a file
- Clear, modular OOP design

---

## Technologies
- Language: Java
- Paradigm: Object-Oriented Programming
- Libraries: stdlib (for I/O utilities)
- Tools: Any Java-compatible IDE (Eclipse, IntelliJ IDEA, VS Code)

---

## Author
- Developed by Nicolas Constantinou
- 2025

## Usage

### Compile/Run:
```bash
javac -cp .:lib/stdlib.jar src/*.java -d bin
java -cp bin:lib/stdlib.jar Sudoku <N> <input-file>
