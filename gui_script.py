import io
import subprocess
import datetime
import time
import Tkinter as tk
from PIL import Image, ImageTk
# from google.cloud import storage

# config values
touch_screen_size = "800x480"
main_menu_font = 'Helvetica 17 bold'
sub_menu_header_font = 'Helvetica 14 bold'
num_poses = 4
num_cameras = 4
num_images = num_poses*num_cameras

## called for new capture window

def new_window_capture(main_window):
    capture_window = tk.Toplevel(main_window)
    capture_window.title('Capture Window')
    capture_window.geometry(touch_screen_size)
    capture_window.configure(background='white')

    capture_buttons = []
    pose_buttons = []

    for x in range(num_images):
        capture_buttons.append(tk.Button(capture_window, text='Capture Image '+str(x+1), height=4,width=25,command=(lambda x=x: capture_image(x+1,main_window))))

    for x in range(num_poses):
        pose_buttons.append(tk.Button(capture_window, text='Capture Pose '+str(x+1), font=sub_menu_header_font, height=4, width=16, command=(lambda x=x: capture_pose(x+1,main_window))))
        pose_buttons[x].grid(column=x, row=0)

    for x in range(num_images):
        capture_buttons[x].grid(column=x/num_cameras, row=x%num_cameras+1)

    capture_back = tk.Button(capture_window, text='Close Window', height=3,width=16,command=capture_window.destroy)
    capture_back.grid(column=0,row=num_cameras+1)

## called for new check window

def new_window_check(main_window):
    check_window = tk.Toplevel(main_window)
    check_window.title('Check Window')
    check_window.geometry(touch_screen_size)
    check_window.configure(background='white')

    check_buttons = []
    for x in range(num_images):
        check_buttons.append(tk.Button(check_window, text='Check Image '+str(x+1), height=6,width=28,command=(lambda x=x: check_image(x+1, main_window))))

    for x in range(num_images):
        check_buttons[x].grid(column=x/num_cameras,row=x%num_cameras)

    check_back = tk.Button(check_window, text='Close Window', height=4,width=18,command=check_window.destroy)
    check_back.grid(column=0,row=num_cameras)

## called for new pose capture window

def capture_pose(pose_num, main_window):
    first_photo = (pose_num-1)*num_cameras+1
    last_photo = (pose_num-1)*num_cameras+num_cameras
    pose_success_window = tk.Toplevel(main_window)
    pose_success_window.title('Capture Window')
    pose_success_window.geometry(touch_screen_size)
    pose_success_window.configure(background='white')
    try:
        for x in range(first_photo,last_photo+1):
            camera_select = (x-1)%num_cameras
            filename = create_filepath(x)
            print "Capturing image %d with camera %d" % (x,camera_select)
            subprocess.call(['fswebcam -d /dev/video0 -r 1920x1080 -S 5 -q --no-banner'+filename])
        pose_message = "Pose capture succeeded! Touch to return back."
    except:
        pose_message = "Pose capture failed... touch to return back and try again."
        print "Pose %d capture failed." % pose_num
    pose_success_destroy = tk.Button(pose_success_window, text=pose_message, font = main_menu_font, height=18, width=60,command=pose_success_window.destroy).pack()

## called for new individual image capture window

def capture_image(img_num, main_window):

    camera_select = (img_num-1)%num_cameras
    filename = create_filepath(img_num)
    capture_success_window = tk.Toplevel(main_window)
    capture_success_window.title('Capture Window')
    capture_success_window.geometry(touch_screen_size)
    capture_success_window.configure(background='white')

    # image capture command
    try:
        print "Capturing image %d with camera %d" % (img_num, camera_select)
        subprocess.call(['fswebcam -d /dev/video0 -r 1920x1080 -S 5 -q --no-banner'+filename])
        capture_message = "Image capture succeeded! Touch to return back."
    except:
        capture_message = "Image capture failed... touch to return back and try again."
        print "Image %d capture failed." % img_num

    capture_success_destroy = tk.Button(capture_success_window, text=capture_message, font = main_menu_font, height=18, width=60,command=capture_success_window.destroy).pack()

## called for new check image window

def check_image(img_num, main_window):
    real_num = (img_num-1)%num_cameras+1
    path = create_filepath(real_num)

    # create image window
    image_window = tk.Toplevel(main_window)
    image_window.title('Image'+str(img_num))
    image_window.geometry(touch_screen_size)
    image_window.configure(background='white')

    try:
        # open and resize image
        print "Loading image %d (actually %d for now)" % (img_num, real_num)
        loaded_img = ImageTk.PhotoImage(Image.open(path).resize((600,480),Image.ANTIALIAS))
        # place image on canvas (left side of screen)
        img_canvas = tk.Canvas(image_window,width=800,height=480)
        img_canvas.grid(row=0,column=0)
        img_canvas.create_image(0,0,image=loaded_img,anchor="nw")
        img_canvas.image = loaded_img
    except:
        print "Image %d failed to load" % img_num

    # create back button
    back_button = tk.Button(image_window, text='Close Window', height=4,width=20,command=image_window.destroy)
    back_button.place(x=600,y=200)
    image_window.mainloop()

## called when uploading images is clicked

def upload_images(main_window):
    upload_window = tk.Toplevel(main_window)
    upload_window.title('Upload Window')
    upload_window.geometry(touch_screen_size)
    upload_window.configure(background='white')
    try:
        print "Uploading all images to Firebase"
        firebase_upload()
        upload_message = "Upload succeeded! Touch to return back."
    except:
        upload_message = "Upload failed... touch to return back."
        print "Upload failed."
    upload_destroy = tk.Button(upload_window, text=upload_message, font = main_menu_font, height=18, width=60,command=upload_window.destroy).pack()

## firebase upload code, uploads cam1.jpg to cam16.jpg in webcam directory

def firebase_upload():

    PROJECT_ID = 'BME590Project'
    CLOUD_STORAGE_BUCKET = 'bme590project.appspot.com'

    for x in range(num_images):
        filename = create_filepath(x+1)
        client = storage.Client(project=PROJECT_ID)
        bucket = client.bucket(CLOUD_STORAGE_BUCKET)
        blob = bucket.blob(filename)
        with open(filename, "rb") as fp:
           try:
               blob.upload_from_file(fp)
           except:
               print "firebase upload failed"

def create_filepath(img_num):
    return "/home/pi/webcam/cam" + str(img_num) + ".jpg"

def main():

    print "Started new session on %s" % (datetime.datetime.now())

    # create main window
    main_window = tk.Tk()
    main_window.title('Image Capture Helper')
    main_window.geometry(touch_screen_size)
    main_window.configure(background='white')

    button_capture = tk.Button(main_window, text='Capture Images', font=main_menu_font, height=5, width=60, command =(lambda: new_window_capture(main_window))).pack()
    button_check = tk.Button(main_window, text='Check Images', font=main_menu_font, height=5, width=60, command=(lambda: new_window_check(main_window))).pack()
    button_upload = tk.Button(main_window, text='Upload Images',font=main_menu_font, height=5, width=60, command=(lambda: upload_images(main_window))).pack()

    main_window.mainloop()

if __name__ == '__main__':
    main()
