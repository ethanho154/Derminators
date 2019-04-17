import io
import os
import datetime
import time
from PIL import Image
import Tkinter as tk
from google.cloud import storage

## Create Main Window

main_window = tk.Tk()
main_window.title('Image Capture Helper')
main_window.geometry("800x480")

## Define Button Functions

def new_window_capture():
    capture_window = tk.Toplevel(main_window)
    capture_window.title('Capture Window')
    capture_window.geometry("800x480")

    capture_buttons = []
    pose_buttons = []
    
    for x in range(1,17):
        capture_buttons.append(tk.Button(capture_window, text='Capture Image '+str(x), height=3,width=15,command=(lambda x=x: capture_image(x))))
    
    for x in range(1,5):
        pose_buttons.append(tk.Button(capture_window, text='Capture Pose '+str(x), font='Helvetica 14 bold', height=4,width=15,command=(lambda x=x: capture_pose(x))))
        pose_buttons[x-1].grid(column=x-1,row=0)
    
    for x in range(16):
        capture_buttons[x].grid(column=x/4,row=x%4+1)
    
    capture_back = tk.Button(capture_window, text='Close Window', height=3,width=16,command=capture_window.destroy)
    capture_back.grid(column=0,row=5)

def new_window_check():
    check_window = tk.Toplevel(main_window)
    check_window.title('Check Window')
    check_window.geometry("800x480")
    
    check_buttons = []
    for x in range(1,17):
        check_buttons.append(tk.Button(check_window, text='Check Image '+str(x), height=4,width=18,command=(lambda x=x: check_image(x))))
        
    for x in range(16):
        check_buttons[x].grid(column=x/4,row=x%4)
    
    check_back = tk.Button(check_window, text='Close Window', height=4,width=18,command=check_window.destroy)
    check_back.grid(column=0,row=4)

def capture_pose(pose_num):
    print "captured images "+str((pose_num-1)*4)+" to "+str((pose_num-1)*4+3)

def capture_image(img_num):
    print "captured image "+str(img_num)
#    os.system("fswebcam -d /dev/video0 -r 1920x1080 -S 5 -q --no-banner /home/pi/webcam/cam1.jpg")

def check_image(img_num):
    try:
        print "check image "+str(img_num)
        real_num = (img_num-1)%4+1
        os.system('display -resize '+str(798)+'x'+str(430)+'! webcam/cam'+str(real_num)+'.jpg')
    except:
        print "Failed to check image"

def upload_images():
    try:
        print "Images Uploaded!"
    except:
        print "Error in uploading images"

button_capture = tk.Button(main_window, text='Capture Images', font='Helvetica 17 bold',height=4,width=40,command=new_window_capture)
button_check = tk.Button(main_window, text='Check Images', font='Helvetica 17 bold',height=4,width=40,command=new_window_check)
button_upload = tk.Button(main_window, text='Upload Images',font='Helvetica 17 bold',height=4,width=40,command=(lambda: upload_images()))

## Add Buttons to GUI

button_capture.pack()
button_check.pack()
button_upload.pack()

main_window.mainloop()

#if True:

    #os.system("fswebcam -d /dev/video0 -r 1920x1080 -S 5 -q --no-banner /home/pi/webcam/cam1.jpg")

    # Google Cloud Project ID. This can be found on the 'Overview' page at
    # https://console.developers.google.com
    #PROJECT_ID = 'BME590Project'
    #CLOUD_STORAGE_BUCKET = 'bme590project.appspot.com'

    #filename = "webcam/cam1.jpg"

    # Create unique filename to avoid name collisions in Google Cloud Storage
    #date = datetime.datetime.utcnow().strftime("%Y-%m-%d-%H%M%S")
    #basename, extension = filename.rsplit('.', 1)
    #unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
    # Instantiate a client on behalf of the project
    #client = storage.Client(project=PROJECT_ID)
    # Instantiate a bucket
    #bucket = client.bucket(CLOUD_STORAGE_BUCKET)
    # Instantiate a blob
    #blob = bucket.blob(filename)

    # Upload the file
    #with open(filename, "rb") as fp:
    #    try:
    #        blob.upload_from_file(fp)
    #    except:
    #        print "Upload failed"

    #print("Photos Uploaded!")

    #GPIO.output(19,GPIO.HIGH)
    #time.sleep(0.3)
    #GPIO.output(19,GPIO.LOW)

