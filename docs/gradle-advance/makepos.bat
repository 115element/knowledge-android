echo clean
call gradlew.bat clean
echo build
call gradlew.bat build
echo makeJar
call gradlew.bat makeJar
echo deploy
call gradlew.bat deploy
echo buildFatPosJar
call gradlew.bat buildFatPosJar
echo buildFatInlineJar
call gradlew.bat buildFatInlineJar