CALL gradlew.bat clean

echo "====================>   gradlew model  <===================="
CALL gradlew.bat :model:clean :model:deploy

echo "====================>   gradlew common  <===================="
CALL gradlew.bat :common:clean :common:deploy

echo "====================>   gradlew busi  <===================="
CALL gradlew.bat :busi:clean :busi:deploy

echo "====================>   gradlew release  <===================="
CALL gradlew.bat :release:makeJar :release:deploy

echo "====================>   gradlew pos  <===================="
CALL gradlew.bat :pos:clean :pos:makeJar :pos:deploy
