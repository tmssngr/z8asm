set JAVA_HOME=D:\tools\graalvm-jdk-21.0.12+7.1
call "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\VC\Auxiliary\Build\vcvarsall.bat" x64
gradlew.bat nativeCompile
IF %ERRORLEVEL% NEQ 0 (
  pause
)
