#!/bin/bash
set -x

#REQUIRED
apk_origin_path=app/build/outputs/apk/debug/app-debug.apk
apk_name=MarvelCPP.apk
apk_folder=build/apk/


apk_final_path=$apk_folder$apk_name

#BUILD APK
chmod +x gradlew
./gradlew clean
./gradlew assembleDebug

#COPY FILE TO RIGHT FOLDER
mkdir -p $apk_folder
cp $apk_origin_path $apk_final_path