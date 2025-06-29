# Dummy Vulnerable HAL Implementation
This is an example of a dummy implementation of a grade management system as a HAL service that intentionally includes security vulnerabilities. It was created for a seminar paper in the "Software Security" lecture at the Munich University of Applied Sciences.
The purpose of the implementation is to compare C++ and Rust in low-level Android code.

The purpose of the implementation is to compare C++ and Rust in low-level Android code.
The focus is on memory safety.

The following memory security vulnerabilities have been implemented:
- Out-of-bounds read
- Out-of-bounds write
- Null dereference
- Use After Free

# Contents
- HAL:
  - HAL Service Implementation of GradeManager in C++ and Rust
  - Definition of the AIDL interface 
- App: A Jetpack Compose system app that communicates with the HAL via AIDL
