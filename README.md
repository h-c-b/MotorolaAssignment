# Motorola Technical Assignment

### Run the app
To run the solution execute `.\mvnw spring-boot:run` from the project's root folder.
The app runs on the default port 8080.
The files are being written to the demo/FilesFolder in this project, but the location
can be updated by editing the `fileStoreRoot` property in the `application.properties` file.

To call the upload endpoint:
    send a `POST` request to `/api/files` with body type form-data, with the key: `file`
    and value: the file that is to be uploaded. 

To call the download endpoint:
    send a `GET` request to `/api/files/{filename}`, replace `{filename}` in the path with
    an actual name, eg /api/files/myDoc.pdf. It should return the file to download.

To call the getFiles endpoint:
    send a `GET` request to `/api/files`.

### Run the tests
To run the tests simply run `.\mvnw test` from the project's root folder.
    