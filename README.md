# FILM QUERY Homework Assignment

### Overview
This program allows a user to interact with a wide range of information available through SQL databases.
The user is first met with a menu prompting them to choose an option regarding how to search through the data base for film information.  The first option is the search films by ID.  If the user chooses this option (1), the user is prompted to input the film ID into the program. Once entered, the corresponding film is returned along with a slew of information about that particular film. If the user enters a value that is outside the range of the film ID, the user is politely redirected to try again , and reminded that the range of film ID is from 1 - 1000.
Option 2 allows the user to search for films by keyword.  Once the user enters the keyword, any film with a matching portion of the title or description is returned, along with a slew of information about the film as well.  Any film that matches the keyword is returned, and if there is no film that matches, the user is properly informed.
The menu will properly loop until the user decides to quit the application by choosing option 3.  This command will gracefully close the program.


### Applications Used

 GitHub/git  
 Eclipse
 Java   
 Terminal
 SQL

### Lessons Learned

1. As this was my first interaction with SQL, I got off to a rocky start. Once the framework was complete, the rest of the project came into view. The most difficult part for me was determining what was actually going to print out.  Manipulation of the toString was something I overlooked often throughout my efforts during this project.
2. I did not realize that the ordering within the string sql statement and the get arguments within the while loop in the methods was of consequence to the proper compilation of the code. in the findActorsByFilmId method, I had (actor.*) and (film.*) swapped in the ordering of the string sql. It was then printing off incorrect information. I identified that the information printing out was relative to the film table, and not the required actor information.  
3. I found it very useful to create my sql statements and run them from SQL in terminal before trying to input it to eclipse.  This way I was able to ensure the logic of my statement was sound. Then I input it into the test method, with a hard-coded value.  Here I did not have to worry about perfecting the intricacies of the code still.  Lastly, I transferred the code to the actual menu and worked out the intricacies of the final code.  This process reiterated the core concept of SQLs well as allowed me to systematically handle the next step and trouble shoot issues instead of trying to attack the whole problem at once.
