<h1>Java Spring project with MySQL: File Management System</h1>

<br>
This project is a Java Spring application that uses a MySQL database to create a simple file management system. The system is designed to have two types of users: an administrator with full permissions (upload, download and delete files) and a user with limited permissions (download files only).<br>

<h3>Technologies used</h3>

<p>Java Spring Boot: Rapid development framework for Java applications.</p>
<p>MySQL: Relational database for storing information about users and files.</p>
<p>Spring Security: For user authentication and authorization.</p>
<p>Thymeleaf: For creating dynamic HTML pages.</p>
<p></p>Bootstrap: For a responsive and user-friendly user interface.</p>
<br>

<h3>Features</h3>

<h4>Admin</h4>

<p>File Upload: The administrator can upload files to the system.(upload .zip files only)</p>
<p>File Download: Can download any file available in the system.</p>
<p></p>Delete Files: Has the ability to delete existing files.</p>

<h4>Standard User</h4>

<p>File Download: Can download available files, but does not have permission to upload or delete files.</p>

<br>
<h3>How to Run the Project</h3>

<p>Configure the database properties in the application.properties file.</p>
<p>Run the Spring Boot application using your IDE or via the command line (./mvnw spring-boot:run).</p>
<p>Access the application at http://localhost:8081 </p>
