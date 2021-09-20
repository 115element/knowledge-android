set GRADLE_OPTS="-Dfile.encoding=utf-8"
call gradlew.bat :pos:clean :pos:deploy
rem dir D:\DevelopmentSoftWare\workSpace\posfx\pos\build\libs\
java -cp D:\DevelopmentSoftWare\workSpace\posfx\pos\build\libs\pos-lawson.jar hyi.pos.release.ZipPack