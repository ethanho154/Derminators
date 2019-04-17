import io
import os
import datetime
import time
import RPi.GPIO as GPIO
from google.cloud import storage

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(26,GPIO.IN,pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(19,GPIO.OUT)


while True:
    input_state = GPIO.input(26)
    if input_state == True:
        print("Button was pushed")
        GPIO.output(19,GPIO.HIGH)
        time.sleep(0.5)
        GPIO.output(19,GPIO.LOW)
    time.sleep(0.2)