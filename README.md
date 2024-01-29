# Super*Duper*Drive Cloud Storage

1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  


## Requirements
1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End

1. Managing user access with Spring Security
 - Restrict unauthorized users from accessing pages other than the login and signup pages.
 - Spring Boot has built-in support for handling calls to the `/login` and `/logout` endpoints.
 - Custom `AuthenticationProvider` which authorizes user logins by matching their credentials against those stored in the database.  


2. Handling front-end calls with controllers
 - Controllers for the application that bind application data and functionality to the front-end via Spring MVC's application model to identify the templates served for different requests and populating the view model with data needed by the template. 
 - Controllers to determine what error messages the application displays to the user and handle them accordingly.

3. Making calls to the database with MyBatis mappers
 - POJOs (Plain Old Java Objects) with fields that match the names and data types in the schema.
 - MyBatis mapper interfaces for each of the model types that represent specific SQL queries and statements required by the functionality of the application.

### The Front-End

1. Login page
 - Users can use this page to login to the application. 
 - Show login errors, like invalid username/password.


2. Sign Up page
 - Potential users can use this page to sign up for a new account. 
 - Validate that the username supplied does not already exist in the application, and show such signup errors on the page when they arise.
 - Securely store password (encrypted via 256 hash)

3. Home page
The home page is the center of the application and hosts the three required pieces of functionality.
 i. Files
  - The user can upload files and see any files they previously uploaded.
  - The user can view/download or delete previously-uploaded files.
  - Any errors related to file actions should be displayed. (i.e. duplicate files, invalid payload)

 ii. Notes
  - The user can create notes and see a list of the notes they have previously created.
  - The user can edit or delete previously-created notes.

 iii. Credentials
 - The user can store credentials for specific websites and see a list of the credentials they've previously stored. Can even store passwords (stored securely)
 - The user can view/edit or delete individual credentials. Passwords are un-encrypted and displayed.


### Testing

1. Tests for user signup, login, and unauthorized access restrictions.
 - Test that verifies that an unauthorized user can only access the login and signup pages.
 - Test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible. 


2. Tests for note creation, viewing, editing, and deletion.
 - Test that creates a note, and verifies it is displayed.
 - Test that edits an existing note and verifies that the changes are displayed.
 - Test that deletes a note and verifies that the note is no longer displayed.


3. Tests for credential creation, viewing, editing, and deletion.
 - Test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
 - Test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
 - Test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.