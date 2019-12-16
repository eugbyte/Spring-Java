# SpringJava
Content Management System in Java Spring

Group Project - fellow contributors: Khine, Peter, Saif, Lara, Zhao Min, Samuel

# Instructions:
1. Unzip the file. In the file, there are one folder (smsFinal) and one database file(team5.sql)

2. Import database into MySQL: 
- Server/ Data Import/ Import from Disk
- Import from Self-Contained File/ Browse to sms_3.sql
- Under Default Schema to be Imported to, click New/ Enter the name of schema to create, for example "team5", click OK
- Under Import Progress, click Start Import
3. Import folder "sms5" to SpringToolSuit(STS): 
- File/Import/General/Existing Projects Into Workplace/ Next
- Root Directory/ Browse ... / Chooose the folder sms5/ click Finish 

4. Make a change in src/main/resources/ application.properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/team5?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=root
spring.datasource.password=XXXXXXX (your data server password)

5. Make a change in MailController.java
mailSender.send("XXXXXX", "Enrolment Request submitted", "Please check system to approve/reject enrollment request.");
(replace XXXXXX by your email address)

6. Go to sms/main/java, under main, right click at SmsApplication.java/ Run As/ Spring Boot App

7. Open your web browser (Chrome is recommmended), enter the url: localhost:8080 to start 

Login Credentials: 
For Admin: 
- Username:admin
- Password:admin

For Student: 
- Username:student 
- Password:student

For Faculty Member: 
- Username:faculty 
- Password:faculty






