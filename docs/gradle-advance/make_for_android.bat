call gradlew.bat clean
call gradlew.bat build sourcesJar
call gradlew.bat deploy buildFatPosJar
copy /y .\updateVersion\androidlibs\* ..\unifiedpos-andriod\app\libs\
copy /y .\updateVersion\androidlibs\pos-busi-sources.jar .\updateVersion\server\libs\
copy /y .\updateVersion\androidlibs\pos-model-sources.jar .\updateVersion\server\libs\
copy /y .\updateVersion\androidlibs\pos-common-sources.jar .\updateVersion\server\libs\
copy /y .\updateVersion\server\libs\* ..\unifiedpos-inline\library\libs\
