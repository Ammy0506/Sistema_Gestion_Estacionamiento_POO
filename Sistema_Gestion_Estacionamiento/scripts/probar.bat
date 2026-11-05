@echo off
setlocal
cd /d "%~dp0\.."
if not exist target\classes mkdir target\classes
if not exist target\test-classes mkdir target\test-classes
(for /r src\main\java %%f in (*.java) do @echo %%f) > target\sources.txt
javac --release 17 -encoding UTF-8 -d target\classes @target\sources.txt
if errorlevel 1 exit /b 1
(for /r src\test\java %%f in (*.java) do @echo %%f) > target\test-sources.txt
javac --release 17 -encoding UTF-8 -cp target\classes -d target\test-classes @target\test-sources.txt
if errorlevel 1 exit /b 1
java -cp target\classes;target\test-classes estacionamiento.PruebasEstacionamiento
endlocal
