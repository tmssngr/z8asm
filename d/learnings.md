## Summary: Problems & Learnings from Java→D Translation

### **1. Module System Differences**

**Problem**: Java uses packages (`package com.example;` + class in file), D uses modules (`module com.example;` with class).
**Learning**: D modules must match file paths exactly. `src/z8asm/lexer.d` → `module z8asm.lexer;`.

---

### **2. Collection Types & Appender**

**Problem**: 
```d
Appender!char buffer;  // WRONG - char is not a dynamic array
```
**Learning**: D's `Appender` requires dynamic array types:
```d
Appender!(char[]) buffer;  // CORRECT
Appender!(Command[]) commands;  // CORRECT
```

---

### **3. Exception Handling Syntax**

**Problem**:
```d
} catch (...) {           // Java-style, WRONG in D
```
**Learning**: D requires explicit exception types:
```d
} catch (Exception e) {   // CORRECT
```
Also, D uses `object.Exception`, not `core.exception.Exception`.

---

### **4. String Operations**

**Problem**: Missing imports for string methods:
```d
text.toLower();           // Error: no property toLower
text.startsWith("x");     // Error: needs import
```
**Learning**: Must import explicitly:
```d
import std.string : toLower, startsWith, endsWith;
```

---

### **5. String Concatenation**

**Problem**: Can't directly concatenate strings with non-strings:
```d
"Value: " ~ 42          // Error: incompatible types
"Type: " ~ token        // Error: token is TokenType enum
```
**Learning**: Use `to!string()` for conversions:
```d
"Value: " ~ to!string(42)
"Type: " ~ to!string(token)
```

---

### **6. Null Handling (Critical)**

**Problem**: D structs cannot be null (value types):
```d
Location current; 
if (current is null) { /* WRONG - structs can't be null */ }
```
**Learning**:
- **Classes**: Can be null (reference types)
- **Structs**: Cannot be null; use pointers or nullable types
- **Solution**: Changed `Location current` → `Location* current` or avoided null checks

---

### **7. Size Type Mismatches**

**Problem**: `string.length` returns `ulong` in D, but methods expect `int`:
```d
bytes.length         // ulong
Command(content(bytes.length))  // expects int
```
**Learning**: Explicit casting required:
```d
cast(int)bytes.length
```

---

### **8. Type Hierarchy Issues**

**Problem**: In Labels class:
```d
Label[string] globalLabels;
GlobalLabel current;  // GlobalLabel extends Label
current = globalLabels["key"];  // Error: Label → GlobalLabel
```
**Learning**: If storing subclasses, declare map with subclass type:
```d
GlobalLabel[string] globalLabels;  // Now correct
```

---

### **9. Method Overrides**

**Problem**:
```d
string toString() { /* ... */ }  // Missing override
```
**Learning**: D requires explicit `override` keyword:
```d
override string toString() { /* ... */ }
```

---

### **10. Enum to String**

**Problem**: Can't cast enums directly:
```d
cast(string)Type.LABEL   // Error
```
**Learning**: Use `to!string()`:
```d
to!string(Type.LABEL)
```

---

### **11. Keyword Conflicts**

**Problem**: `out` is a reserved keyword in D:
```d
out.print("text");  // WRONG
```
**Learning**: Rename or use alternative:
```d
warningOut.print("text");  // Solution used
```

---

### **12. Import Granularity**

**Problem**: Java has implicit imports via wildcard, D requires explicit:
```d
// Java: import java.util.*; // Gets everything
// D: Need to specify
import std.string : format, replace, toLower, startsWith, endsWith;
```
**Learning**: D has fine-grained imports - must list all needed symbols.

---

### **13. Character vs String**

**Problem**: Appender returns `char[]` not `string`:
```d
Appender!(char[]) buffer;
return buffer.data;  // Error: char[] vs string
```
**Learning**: Cast explicitly:
```d
return cast(string)buffer.data;
```

---

### **14. String Methods**

**Problem**: D strings don't have `indexOf`:
```d
"abc".indexOf('a')  // Error
```
**Learning**: Use `find` from std.algorithm:
```d
"abc".find('a') !is null
```

---

### **15. Test Framework Differences**

**Problem**: Java JUnit vs D unittest:
```java
@Test public void testX() { /* ... */ }  // Java
```
**Learning**: D uses unittest blocks:
```d
unittest {
    // test code here
}
```

---

### **16. Static Method Calls**

**Problem**: Importing specific static methods:
```d
import z8asm.command : Command, autoJump;  // WRONG
```
**Learning**: Just import the module, D brings in all public members:
```d
import z8asm.command : Command;
// Use: Command.autoJump(...)
```

---

### **17. File Reading**

**Problem**: `std.nio.file` doesn't exist in this D version
**Learning**: Use `std.file : readText` directly

---

### **Biggest Lessons**

1. **D is explicit** - Nothing is implicit. Must import, cast, and specify everything.
2. **Type system is strict** - No automatic conversions between numeric types.
3. **Structs vs Classes** - Fundamental difference affects null handling.
4. **Compiles ≠ Works** - D catches many issues at compile time, but type mismatches can be subtle.
5. **Appender needs array types** - Most common early mistake.

**Result**: After addressing all these issues systematically, the entire codebase now compiles successfully and the tests are ready to run (pending C compiler for linking).
