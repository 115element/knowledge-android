#/bin/sh  $1(facepos/sunmi/wintec/utk)
rm -rf app/src/main/jniLibs
cp -r customVersion/$1/main/ app/src/main/
cp customVersion/$1/build.gradle app/

