# Hospital Management System

## System Overview
The Hospital Management System is a Java-based console application designed to manage patient records. It allows users to perform CRUD (Create, Read, Update, Delete) operations on patient data, search for specific patients, and persist data using file serialization.

## Class Responsibilities
- `model.BaseEntity`: An abstract base class providing common properties like ID, Name, Create Date, and Update Date.
- `model.Patient`: Represents a patient entity, extending `BaseEntity` with attributes like sex, address, phone, dob, diagnosis, assigned doctor, and admission status.
- `manager.IManager`: Interface defining basic CRUD and file operations.
- `manager.BaseManager`: Abstract implementation of `IManager` using Generics, handling common logic like adding, deleting, and file IO.
- `manager.PatientManager`: Specializes `BaseManager` for `Patient`, implementing update and search logic.
- `controller.PatientHandle`: Controls the workflow for managing patients, interacting with the user and the `PatientManager`.
- `controller.HospitalHandler`: The main controller that routes user menu choices to appropriate handlers.
- `view.InputValidator`: Handles robust user input validation (regex, loops) to ensure data integrity.
- `view.OutputViewer`: Centralized display for messages, menus, and errors.
- `main.Main`: The entry point of the application, displaying the menu and starting the system.

## Relationships
- `Patient` extends `BaseEntity`.
- `BaseManager` implements `IManager` and uses a List of `BaseEntity`.
- `PatientManager` extends `BaseManager<Patient>`.
- `PatientHandle` uses `PatientManager` and `InputValidator` / `OutputViewer`.
- `HospitalHandler` aggregates `PatientHandle`.

## File Format
The system uses **Java Object Serialization** to store data. Patient records are saved into `patient.dat` in binary format. When reading from the file, the system safely deserializes the list and appends only valid, non-duplicate records to the existing collection.

## Compilation and Execution Instructions
1. Requires Java Development Kit (JDK) 8 or higher.
2. If using Maven:
   ```bash
   mvn clean package
   java -cp target/classes main.Main
   ```
3. Alternatively, compile directly:
   ```bash
   javac -d bin src/main/java/**/*.java
   java -cp bin main.Main
   ```
