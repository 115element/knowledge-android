./gradlew clean
./gradlew build sourcesJar && \
./gradlew deploy buildFatPosJar && \
cp -f ./updateVersion/androidlibs/* ../mobile/app/libs/
cp -f ./updateVersion/androidlibs/pos-busi-sources.jar ./updateVersion/server/libs/
cp -f ./updateVersion/androidlibs/pos-model-sources.jar ./updateVersion/server/libs/
cp -f ./updateVersion/androidlibs/pos-common-sources.jar ./updateVersion/server/libs/
cp -f ./updateVersion/server/libs/* ../inline/library/libs/
echo "copy time : `date '+%Y-%m-%d %H:%M:%S'`" > ./updateVersion/version
