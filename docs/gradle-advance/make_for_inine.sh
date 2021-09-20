./gradlew clean
./gradlew build sourcesJar && \
./gradlew deploy buildFatPosJar && \
cp -f ./updateVersion/server/libs/* ../inline/library/libs/
cp -f ./updateVersion/server/libs/* ../inline/updateVersion/server/libs/
echo "copy time : `date '+%Y-%m-%d %H:%M:%S'`" > ../inline/updateVersion/version