set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_202
set GRADLE_OPTS=-Dfile.encoding=UTF-8
call gradlew assembleRelease installRelease
if %ERRORLEVEL% EQU 0 adb shell am start -n hyi.mobilepos/.MainActivity