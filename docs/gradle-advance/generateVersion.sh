./gradlew cleanJar

echo "====================>   gradlew model  <===================="
./gradlew :model:clean :model:deploy

echo "====================>   gradlew common  <===================="
./gradlew :common:clean :common:deploy

echo "====================>   gradlew busi  <===================="
./gradlew :busi:clean :busi:deploy

echo "====================>   gradlew release  <===================="
./gradlew :release:makeJar :release:deploy

echo "====================>   gradlew pos  <===================="
./gradlew :pos:clean :pos:makeJar :pos:deploy

