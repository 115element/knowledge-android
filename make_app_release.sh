# Usage: sh make_app_release.sh {Version}
./gradlew clean :app:assembleRelease
mkdir -p release/Ver$1
cp -f app/build/outputs/apk/release/app-release.apk release/Ver$1/sotpos_$1.$2.apk
cp -f app/build/outputs/apk/pos/release/app-pos-release.apk release/Ver$1/sotpos_$1.$2.apk
cp -f app/build/outputs/apk/self/release/app-self-release.apk release/Ver$1/selfpos_$1.$2.apk
