# credit-suisse-assignment

## Pre Requisites

In order to run this program you will need to first have Java 1.8 installed on your machine.

## Running The Program

In order to run the code you will need to either clone the repository locally or you can download the zip file into whichever directory you prefer and UnZip it.

After this you simply need to cd into /credit-suisse-assignment/output and run the command "java -jar serverlogService-0.0.1.jar logfile.txt" which will build and run the program with the log file to be parsed being logfile.txt which is a sample file I created based on the example given in the assignment.

On program completion "AllEvents.txt" & "AlertEvents.txt" these two files are created, which have output data.

In order to run with a different logfile you just need to give a relative path to the logfile that you would like to be parsed.

## Notes:

I was unsure as to how the program should deal with displaying the database values back to you for evaluation, so I write output in mentioned text files.

I added some sample tests to the program but I didn't have the time to fully finish all of them.