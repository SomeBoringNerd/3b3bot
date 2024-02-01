./gradlew build

if [ $? -ne 0 ]; then
  exit 1
fi

mv ./build/libs/b3bot-1.0.0.jar ~/.minecraft/mods/