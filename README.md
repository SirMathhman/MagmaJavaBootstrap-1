# MagmaJavaBootstrap

Holds the Bootstrap compiler for the Magma programming language. Chances are that this repository will quickly fall out
of date when the real Magma compiler is complete. As a result, this compiler is to be used as a backup compiler in case
the standard Magma compiler becomes broken.
---
## Installation
Using SBT run the following command:
```
sbt package
```
Items are automatically compiled, so there is no need to run ```sbt compile```.

---
## Usage
Run the JAR file, which acts as a rudimentary build system. All inputs {fileName} is relative to the /src directory, and
all outputs are written to the /out directory.

|Common Commands|Syntax            |Usage Notes                                                            |
|:-------------:|------------------|-----------------------------------------------------------------------|
|compile        |compile {fileName}|Compiles the given file and its dependencies.                          |
|run            |run {fileName}    |Executes the given file as a script.                                   |
|package        |package {fileName}|Packages the given and its dependencies. Package is platform-dependent.|

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
