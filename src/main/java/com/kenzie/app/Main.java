package com.kenzie.app;
// import necessary libraries
import DTOitems.Clues;
import DTOitems.CluesDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main  {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided --DONE
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!

     */

    //declare VARIABLES
    static final String TEST_URL = "https://jservice.kenzie.academy/api/clues";
    static String httpResponseStr;
static Random rollingNumber= new Random();


        public static void main (String[]args) throws JsonParseException {
            //Write main execution code here
            //getting from the api with test print here
            try {

                //System.out.println("TEST of GET HERE");
                //System.out.println(CustomHttpClient.sendGET(TEST_URL));

                httpResponseStr = CustomHttpClient.sendGET(TEST_URL);
                 CluesDTO cluesObj;
                 ObjectMapper objectMapper= new ObjectMapper();
                cluesObj= objectMapper.readValue(httpResponseStr, CluesDTO.class);

                //able to call out from the DTO here--- No delete
                System.out.println("Welcome to the Jeopardy");

                // int randomNumberCreator = RandomNum.roll();

                String userInputAnswer;
                boolean exitLoop= false;

                int totalScoreBoard=0;
                int counterQuestions = 0;


                while(!exitLoop) {
                    for (int i = 0; i <10; i++) {
                        counterQuestions++;
                        int randomNumberCreator = rollingNumber.nextInt(100);
                        String DTOanswer = cluesObj.getClues().get(randomNumberCreator).getAnswer();
                        //question counter
                        System.out.println("Question " + counterQuestions);

                        //game prompt print out
                        System.out.println("ID:" + cluesObj.getClues().get(randomNumberCreator).getId() +
                                "\nCategory: " + cluesObj.getClues().get(randomNumberCreator).getCategory().getTitle() +
                                "\nQuestion: " + cluesObj.getClues().get(randomNumberCreator).getQuestion());
                        //Print for ANSWER HERE
                        System.out.println(DTOanswer);
                        //USER'S INPUT
                        Scanner scanner2 = new Scanner(System.in);
                        userInputAnswer = scanner2.nextLine();

                        //Correct answer
                        if (userInputAnswer.equalsIgnoreCase(DTOanswer)) {
                            System.out.println("\nCorrect!! Point to Ravenclaw");
                            totalScoreBoard += 1;

                            System.out.println("Total score: " + totalScoreBoard + "\n");
                        } else {
                            //Wrong answer; not correct
                            System.out.println("Sorry, wrong answer. The answer is " + DTOanswer);
                            System.out.println("Total score: " + totalScoreBoard + "\n");
                        }
                    }
                    //NEXT GAME
                    System.out.println("Would you like to keep playing? Yes or No");
                    Scanner scanner= new Scanner(System.in);
                    String playAgainRes= scanner.nextLine();

                    if(playAgainRes.equalsIgnoreCase("No")){
                        exitLoop=true;
                    }
                }

                System.out.println("Thank you for playing. Your total score is: " + totalScoreBoard);


            } catch (Exception e) {
                System.out.println("An error has occurred within Main.");
            }


        }


    }


