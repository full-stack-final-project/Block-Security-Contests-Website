# Block-Security-Contests-Website

We use git to do version contorl. We have a github repo for our project: https://github.com/full-stack-final-project/Block-Security-Contests-Website/tree/main/contest-website/database

It is private right now and we will publish it after this course. Prof. Lu, if you want to see our repo during project1-project3, please let us know you github email, and we will send you a invitation. 

## Group member

Yihua Hu; 
Junhao Qiu

## Instructions to run our project

Install Eclipse, Tomcat and config these two as Exercise 1.

Import folder contest-website folder into Eclipse as a project. Change the user name and password for your databse in userDAO.java. Our database name is `contestdb`.

Find database/WebContent and right click index.jsp to run on server. 

## Contributions


### Part 1
We both discussed, designed and drew the ER diagram. 

Yihua Hu: SQL codes for creating 4 tables. Developed: `init()` in `userDAO.java`, `rootPage()` routing function in `ControlServlet.java`. ALL class/.java `user.java`, `contestant.java`, `judge.java`, `sponsor.java`. The last three class are  inherited from class user. ALSO implemented a python script `generate_data.py` to generate `fake_data.txt` which we use to initialize the database. This python script and fake_data.txt are also in the zip file under folder `Block-Security-Contests-Website`. 

Junhao Qiu: SQL codes for creating 4 tables. Developed: `insert()`, `checkWalletAddress()`(check if the passing wallet_address exists in database) in `userDAO.java`. `login()` and `signup()` functions in `ControlServlet.java`. And also two jsp file for front end: `mylogin.jsp`, `signuppage.jsp`.

### Part 2
We both discussed, designed and implemented in about 28 hours per person for part 2. List the main responsibilities that each team member took on below:

#### UserDAO.java

Yihua Hu: 
Update tables by adding new fields and add procedures and event for updating contests status;
getOpenContests(): Obtain all open contests
rankContestants(): Rank all contestants based on their reward balance. [This part is required on Part 3.]
sponsor_contests(): Obtain all contests sponsored by a given sponsor.
listBigSponsors(): List all sponsors who created the most number of contests. [This part is required on Part 3.]
getjudgeByID(): Return a judge object based on the user name.
updateGrade(): Update a contestant's grade after the judge grades.
getContestsJudge(): Obtain the contests list for judges to review that the list of contests that he/she is assigned to.
getContestsParticipated(): Obtain all the contests that a certain contestant participates. [This part is required on Part 3.]
assignSubmissionsToJudges(): When a contest is closed, sponsors can use this function to evenly assign all submissions to all judges.
distributeContestRewards(): After all submissions for a contest are graded, sponsors can use this function to distribute rewards to judges and contestants based on rules, and the contest status will be updated to be past.
init(): Update this function by updating some insertion rows and adding procedures and event to check and update contests status.

Junhao Qiu:
listJudgesName(): List all judges name for sponsors selection.
getContestbyID(): Return a contest object based on the wallet address.
insertSubmission(): Insert contestant's sumission for a contest into table `participate`.
getSponsorbyID(): Return a sponsor object based on the wallet address.
getJudgeIDByLoginID(): Return a judge's wallet address based on the user name.
getSponsorIDByLoginID(): Return a sponsor's wallet address based on the user name.
getContestantByLoginID(): Return a contestant object based on the user name.
getjudgeByLoginID(): Return a judge object based on the user name.
getContestantsJudge(): Obtain the contestant object for judge grading in a certain contest.
getContestantByID(): Return a contestant object based on the wallet address.
getSubmission(): Obtain the submission content for judges' review.
createContest(): Insert a new row into table contest when a sponsor creates a new contest.
insertJudgeby(): Insert new records into table judgeby when a sponsor creates a new contest.
checkUserID(): Check if the input user id exists in the selected role table.

#### ControlServlet.java
The functions in this file implement the routing functionalities. 70% of the functions are implemented by Junhao and 30% are implemented by Yihua.

#### JSP files.
50% of the JSP files are done by Junhao and the rest 50% are done by Yihua.


