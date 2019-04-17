#!/bin/bash

DATE=$(date +"%Y-%m-%d_%H%M")

fswebcam -d /dev/video0 -r 1920x1080 -S 20 --no-banner /home/pi/webcam/cam1.jpg
fswebcam -d /dev/video1 -r 1920x1080 -S 20 --no-banner /home/pi/webcam/cam2.jpg
fswebcam -d /dev/video2 -r 1920x1080 -S 20 --no-banner /home/pi/webcam/cam3.jpg
python firebase_upload.py