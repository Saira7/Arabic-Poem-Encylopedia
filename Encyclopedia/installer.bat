@echo off
setlocal

REM Set the installation directory
set "INSTALL_DIR=C:\Program Files\ArabicPoetryEncyclopedia"
set "source_dir=%~dp0"

set "JAR_FILE=scd.jar"

echo.

echo Auto Installer Initializing...
echo Analyzing System Requirements...

REM Check if the installation directory exists, create if not
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"

REM Check Java version
java -version 2>&1 | findstr /I "version" | findstr /V /I "1.7" >nul
if %errorlevel% neq 0 (
    echo Error: Java 1.8 or above is required to run this application.
    echo Please install Java and run the installer again.
    pause
    goto :EOF
)

REM Display installation message
echo.
echo Copying Resource Files...
echo.

REM Copy all files and folders to the installation directory
xcopy "%source_dir%*" "%INSTALL_DIR%" /e /i /y


REM Display completion message
echo.  
echo Installation Completed Successfully
echo Please Run The Application Using: java -jar "%INSTALL_DIR%\arabicPoem.jar"

echo.  
echo Thank You

pause
endlocal