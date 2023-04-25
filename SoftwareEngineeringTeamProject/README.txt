------------ INTRODUCTION ------------
Hello! You are about to embark on a very painful process of getting this program to work!
Just kidding. This project is really easy to set up. The only complicated part is setting
up the MySQL database, but that's super easy to do.

For the sake of this project, there exists a file called "farkle_database.properties".
This file contains the username, password, and url for the MySQL database we will use.
This database stores only usernames and passwords of clients who create an account.

Now that that's out of the way, let's get started.

-------- STEP 1: MYSQL SETUP --------

1. First thing is first, download and install the XAMPP Control Panel. This will be the 
program we use to host a MySQL server. Be sure that when installing, you select the 
MySQL component or else this project will not work.

2. Once downloaded, start the server on the control panel and then open up a new Windows 
command prompt window and navigate to your XAMPP's MySQL's bin directory. By default, 
your location should be "c:\xampp\mysql\bin"

3. Once your directory is changed, log into the MySQL server as the root user with the 
following command, "mysql -h localhost -u root"

4. Select the mysql database as your workspace. Use the following command "use mysql;"

5. Now, create a new user called 'student' whose password is 'hello'. This user will
have all privileges in the server. Enter the following command, "grant all privileges 
on * to student@localhost identified by 'hello' with grant option;
commit;"

6. Then, create a database called "student_space". Enter the following command, 
"create database student_space"

7. You're almost done. Now, grant all privileges to privileges to the database to
'student'. Enter the following command, "grant all on student_space.* to student 
identified by 'hello';"

8. Now, exit as "root" by entering "exit;" and log in as 'student'. Your command should
look like this, "mysql -u student -h localhost -p". Then, when prompted for your password,
enter "hello" and submit.

9. Select the "student_space" database with the command "use student_space".

10. Finally, you can run the "user_table.sql" script. Be sure the script is in an easy
to reach directory, then enter the command "source (directory here)/user_table.sql". An
example of this would be "source c:/user_table.sql". This has now created the necessary table
for the project and we are good to run the server.

-------- STEP 2: PROJECT SETUP --------
For this part, you are going to want to open to project with your favorite compiler.
This project was built using Eclipse, so if you're having issues then maybe try that.

1. Start by importing the project into your work space. 

2. Configure your project build path to include both the "ocsf.jar" and 
"mysql-connector-java-5.1.40-bin.jar" files.


-------- STEP 3: RUNNING PROJECT --------
Once all steps above have been completed, you are now able to use the .bat files to run
the project. Make sure that the .bat files are kept in the same location when you downloaded
the project.

1. Start the server on the computer that is hosting the MySQL server by using the "farkle_server.bat"

2. Use command prompt and enter the command "ipconfig", look for the "IPv4 Address" entry
and write that IP somewhere for you to keep. This is the IP you will supply the clients with
in order to connect to the server.

3. Run the client by using the "farkle_client.bat" file. You will be prompted to enter a
server IP address. Enter the IPv4 address you saved earlier.

4. Now, you can either log in or create an account with any set of credentials. Enjoy!

-------- STEP 4: PLAYING FARKLE --------
In this implementation, only a few rules exist for the game, and they are as follows:

1. Users must roll their dice and can only set aside values of "1" and "5". Each "5"
corresponds to 50 points whereas each "1" is 100 points.

2. If there are no dice that have a value of "1" or "5" then the user farkles and loses all
potential points.