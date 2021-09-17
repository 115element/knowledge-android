set JAVA_HOME=C:\Java\jdk1.8.0_40
set GRADLE_OPTS=-Dfile.encoding=UTF-8
call gradlew assembleDebug installDebug
if %ERRORLEVEL% EQU 0 adb shell am start -n hyi.mobilepos/.MainActivity
