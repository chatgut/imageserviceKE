# Image Microservice
This microservice handles images where you can upload images to a database and retrieve them from the database.

> [!IMPORTANT]
>  Runs on port: ***8001*** <br>
>  Needs a Mysql connection on port: ***3306***


> [!NOTE]
> You can run it on both amd64 and arm64 processors.

> [!WARNING]
> The docker-images with tags that end with ```.sig``` <br> ex. ```sha256-97caa7e56bbecb81339387d5c45c60d4ae262a42c36b62f6cb64d8e42c3fecb8.sig``` <br> Cannot be used as docker images as they are only used to verify that other images are signed.

## Verifying docker-image:
The docker-images from this repository are signed to ensure the integrity of the images. <br>
You can verify that the docker-image is signed from this repository by:
- Download the latest version of https://github.com/sigstore/cosign/releases/ and the correct file for your computer,<br> ex. cosign-windows-amd64.exe.
- Place cosign.pub in the directory where you saved the cosign... .exe file.
- Run the cosign.pub file with the image using cmd/powershell/bash/etc:
  - Open terminal where the cosign files are located.
  - Run this command ``` .\cosign-windows-amd64.exe verify --key cosign.pub ghcr.io/chatgut/imageserviceke:{tag or digest of image you want to verify} ``` ex. latest <br>

If done correctly it should look like this: ![image](https://github.com/chatgut/imageserviceke/assets/143023413/3922ded6-f886-4586-ac8e-02995bc0b005)

## API Endpoints
**Post image to database, returns URL for the saved image. <br>
Needs requestparam for the image ```image``` as a multipartfile in the body, 15MB is max upload size for file.**
```
POST localhost:8001/images?image
```
![image](https://github.com/chatgut/imageserviceke/assets/143023413/38678e1d-acc1-4dcc-8acb-1522614615f1)

**Get image from database, returns ResponseEntity with the pictures byte-array.**
```
GET localhost:8001/images/{id}
```
**Get cropped thumbnail for image, 180px * 200px**
``` 
GET localhost:8001/images/{id}/thumbnail
```
**Delete image from database**
```
DELETE localhost:8001/images/{id}
```
