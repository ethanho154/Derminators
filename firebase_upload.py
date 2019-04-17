import io
import os
import datetime
import time
import RPi.GPIO as GPIO
from google.cloud import storage

## set pinouts for IOs

LED = 19
BUTTON = 26

## config for IOs

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(BUTTON,GPIO.IN,pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(LED,GPIO.OUT)

while True:

    input_state = GPIO.input(BUTTON)
    if input_state:
        os.system("fswebcam -d /dev/video0 -r LED20x1080 -S 5 -q --no-banner /home/pi/webcam/cam1.jpg")
        os.system("fswebcam -d /dev/video1 -r LED20x1080 -S 5 -q --no-banner /home/pi/webcam/cam2.jpg")
        os.system("fswebcam -d /dev/video2 -r LED20x1080 -S 5 -q --no-banner /home/pi/webcam/cam3.jpg")
        os.system("fswebcam -d /dev/video3 -r LED20x1080 -S 5 -q --no-banner /home/pi/webcam/cam4.jpg")

        # Google Cloud Project ID. This can be found on the 'Overview' page at
        # https://console.developers.google.com
        PROJECT_ID = 'BME590Project'
        CLOUD_STORAGE_BUCKET = 'bme590project.appspot.com'

        filename = "webcam/cam1.jpg"

        # Create unique filename to avoid name collisions in Google Cloud Storage
        date = datetime.datetime.utcnow().strftime("%Y-%m-%d-%H%M%S")
        basename, extension = filename.rsplit('.', 1)
        unique_filename = "{0}-{1}.{2}".format(basename, date, extension)

        # Instantiate a client on behalf of the project
        client = storage.Client(project=PROJECT_ID)
        # Instantiate a bucket
        bucket = client.bucket(CLOUD_STORAGE_BUCKET)
        # Instantiate a blob
        blob = bucket.blob(filename)

        # Upload the file
        with open(filename, "rb") as fp:
            try:
                blob.upload_from_file(fp)
            except:
                print "Upload failed"

        filename = "webcam/cam2.jpg"
        basename, extension = filename.rsplit('.', 1)
        unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
        blob = bucket.blob(filename)
        with open(filename, "rb") as fp:
            blob.upload_from_file(fp)

        filename = "webcam/cam3.jpg"
        basename, extension = filename.rsplit('.', 1)
        unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
        blob = bucket.blob(filename)
        with open(filename, "rb") as fp:
            blob.upload_from_file(fp)

        filename = "webcam/cam4.jpg"
        basename, extension = filename.rsplit('.', 1)
        unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
        blob = bucket.blob(filename)
        with open(filename, "rb") as fp:
            blob.upload_from_file(fp)

        print("Photos Uploaded!")

        GPIO.output(LED,GPIO.HIGH)
        time.sleep(0.3)
        GPIO.output(LED,GPIO.LOW)

    time.sleep(0.3)
