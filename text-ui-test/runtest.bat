@ECHO OFF

REM Change to project root
cd /d "%~dp0\.."

REM Delete output from previous run
if exist text-ui-test\ACTUAL.TXT del text-ui-test\ACTUAL.TXT

REM Use fresh data file for deterministic test output
if not exist data mkdir data
type nul > data\groot.txt

REM Run the program with I/O redirection
gradlew.bat -q runText < text-ui-test\input.txt > text-ui-test\ACTUAL.TXT 2>&1

REM Compare actual vs expected
cd text-ui-test
FC ACTUAL.TXT EXPECTED.TXT
if %ERRORLEVEL% EQU 0 (
    echo Test result: PASSED
    exit /b 0
) else (
    echo Test result: FAILED
    exit /b 1
)
