# Block-Security-Contests-Website

We use git to do version contorl. We have a github repo for our project: https://github.com/full-stack-final-project/Block-Security-Contests-Website/tree/main/contest-website/database

It is private right now and we will publish it after this course. Prof. Lu, if you want to see our repo during project1-project3, please let us know you github email, and we will send you a invitation. 

## Group member

Yihua Hu; 
Junhao Qiu

## Instructions to run our project

Install Eclipse, Tomcat and config these two as Exercise 1.

Import folder contest-website folder into Eclipse as a project.

Find database/WebContent and right click index.jsp to run on server. 

## Contributions

We both discussed, designed and drew the ER diagram. 

Yihua Hu: SQL codes for creating 4 tables. Developed: `init()` in `userDAO.java`, `rootPage()` routing function in `ControlServlet.java`. ALL class/.java `user.java`, `contestant.java`, `judge.java`, `sponsor.java`. The last three class are  inherited from class user. ALSO implemented a python script `generate_data.py` to generate `fake_data.txt` which we use to initialize the database. This python script and fake_data.txt are also in the zip file under folder `Block-Security-Contests-Website`. 

Junhao Qiu: SQL codes for creating 4 tables. Developed: `insert()`, `checkWalletAddress()`(check if the passing wallet_address exists in database) in `userDAO.java`. `login()` and `signup()` functions in `ControlServlet.java`. And also two jsp file for front end: `mylogin.jsp`, `signuppage.jsp`.

