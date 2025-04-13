# Build-To-Order (BTO) Management System

## Features

- **User Roles**:
  - **Applicant**:  
    - View eligible projects (subject to age, marital status, and visibility).  
    - Apply for different types of flats 
    - Track application status and withdraw applications when needed.  
    - Manage project inquiries (create, edit, and delete).
  - **Officer**:
    - Inherits all Applicant functionalities.
    - Register to handle a project.
    - View registration status.
    - Assist with project bookings.
    - Generate booking receipts.
    - Maintain individual daily logs.
  - **Manager**:
    - Create, edit, and delete project listings.
    - Toggle project visibility.
    - Process applicant applications and withdrawal requests.
    - Approve or reject officer registration requests.
    - Generate comprehensive applicant and booking reports.
    - Maintain a daily log for project management.

- **Key Functionalities**:
  - Role-based access and menu-driven CLI.
  - Application workflows with status transitions: Pending → (Successful / Rejected) → Booked / Withdrawn.
  - Officer registration handled via `RegistrationList`, and updates upon Manager approval.
  - Fuzzy search functionality for filtering project listings (embedded in the `ViewBy` utility class), allowing convenient usage.
  - Stronger password functionality, kicking one off the system if too many login attempts are done. 
  - Daily logging for both Managers and Officers for enhanced record tracking.
  - Data persistence via text/CSV files (for initial data load).

## How to Build and Run
1. **Compile the Project**:  
   Ensure you are using Java 8 or later (Java 17 is recommended). From the project root, compile all the Java files. For example:
   ```
   javac *.java
   ```
If you have nested packages or directories, adjust the compile commands accordingly.

2. Prepare Data Files:
Place your initial CSV data files (e.g., ApplicantList.csv, ManagerList.csv, OfficerList.csv, ProjectList.csv) inside a folder named data in the project root. The Initialization class will read these files at startup.

3. Run the Application:
  Run the main entry point using:
  ```
  java Main
  ```
4. Interacting with the System:
Use the CLI menus provided in the application to log in as an Applicant, Officer, or Manager and test the various functionalities.

The project can also be run by pulling from Github to any IDE and interactions through the console. 


