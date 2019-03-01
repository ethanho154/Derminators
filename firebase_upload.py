import io
import datetime
from google.cloud import storage

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
blob = bucket.blob(unique_filename)

# Upload the file
with open(filename, "rb") as fp:
    blob.upload_from_file(fp)

filename = "webcam/cam2.jpg"
basename, extension = filename.rsplit('.', 1)
unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
blob = bucket.blob(unique_filename)
with open(filename, "rb") as fp:
    blob.upload_from_file(fp)
    
filename = "webcam/cam3.jpg"
basename, extension = filename.rsplit('.', 1)
unique_filename = "{0}-{1}.{2}".format(basename, date, extension)
blob = bucket.blob(unique_filename)
with open(filename, "rb") as fp:
    blob.upload_from_file(fp)

