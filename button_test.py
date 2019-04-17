import io
import RPi.GPIO as GPIO

## set pinouts for IOs

LED = 19
BUTTON = 26

wait_time = 0.2
led_flash_time = 0.5

## configuration for IOs pull down resistor for button

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(BUTTON,GPIO.IN,pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(LED,GPIO.OUT)

while True:
    input_state = GPIO.input(BUTTON)
    if input_state == True:
        print("Button was pushed")
        GPIO.output(LED,GPIO.HIGH)
        time.sleep(led_flash_time)
        GPIO.output(LED,GPIO.LOW)
    time.sleep(wait_time)
