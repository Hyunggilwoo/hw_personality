/*
* Course: CSE 142 – Introduction to Object-Oriented Programming
* File Name: Personality.java
* Due Date: November 15, 2021
* Instructor: Professor Stuart Reges
*/
import java.io.*;
import java.util.*;

/**
* This program prints a the statistics of the class average personality test involving
* the Keirsey test. When the test results in a text file is scanned, the user can 
* expect the statistics result of the user's test case.
* Personality test is first on Extrovert/Introvert 10 questions,
* Thinking/Feeling 20 questions, Judging/Perceiving 20 questions
* @author Hyunggil Woo
* @version 2021/11/19
*/
public class Personality {

    private static final int NUMBER_OF_CHARACTERS = 4;

    public static void main(String[] theArgs) throws FileNotFoundException {
		Scanner input = new Scanner(new File("bigdata.txt"));
		PrintStream output = new PrintStream(new File("bigoutput2.txt"));
		while (input.hasNextLine()) {
			String name = input.nextLine();
			String line = input.nextLine();
			// two options for constructing an array:
			// 1) a method constructs it and passes it as a parameter
			// 2) the called method constructs it and returns it
            int[] countB = compute('B', line);
			int[] countA = compute('A', line);
            int[] percentOfB = percentageOfB(countB, countA);
            String result = personalityType(name, percentOfB);
            output.println(result);
		}
    }
		
        /**
        * Computes the letters into a designated array from 10 groups of 7 questions.
        * Answer = char array of [4]
        * First questions for each set of Answer = Answer.length = 0
        * Second & third questions for each set of Answer = Answer.length = 1,
        * Fourth & Fifth questions for each set of Answer = Answer.length = 2,
        * Sixth & Seventh questions for each set of Answer = Answer.length = 3
        *
        * @param letter to be examined.
        * @param line of String to be examined.
        * @return the new counted array.
        */ 
		public static int[] compute(char letter, String line) {
			int[] answer = new int[NUMBER_OF_CHARACTERS];
			for (int i = 0; i < line.length(); i++) {
				if (Character.toLowerCase(line.charAt(i)) ==
					Character.toLowerCase(letter)) {
                    if (i % 7 == 0) {
                        answer[0]++;
                    } else if (i % 7 == 1 || i % 7 == 2) {
                        answer[1]++;
                    } else if (i % 7 == 3 || i % 7 == 4) {
                        answer[2]++;
                    } else {
                        answer[3]++;
                    }
				}
			}
			return answer; // { return an array with 4 indicies
		}
        
        /**
        * Outputs the percentage of answers of B / total questions.
        *
        * @param B Array of questions answered as B
        * @param A Array of questions answered in A
        * @return the Array with questions answered in correct way.
        */
        public static int[] percentageOfB (int[] B, int[] A) {
            int[] percentage = new int[NUMBER_OF_CHARACTERS];
            double percent = 0;
            for (int i = 0; i < B.length; i++) {
                percent = (B[i] * 100) / (B[i] + A[i]);
                percentage[i] = (int) Math.round(percent);
            }
            return percentage;
        }
        
		/**
		* Introduces which personality test is used and how it
		* is used.
		*
		*/
		public static void intro() {
			System.out.println("The class used the Keirsey test to measure for " +
			"independent dimensions of personality:");
			System.out.println();
		}
		
		/*
		* Calculates the percentage of the answers A and B from a file
		* that classifies 4 different characters. The following question set has
		* 10 groups of 7 questions with a repeating pattern.
        * B answers correspond to: Introvert(I), intuition(N), feeling(F), perceiving (P)
		* A answers correspond to: Extravert(E), Sensation (S), Thinking (T), Judging (J)
		*
		*
		*/
        public static String personalityType(String name, int[] percentOfB) {
            String personality = "";
            personality += answerOfAOrB(0, "I", "E", percentOfB, personality);
            personality += answerOfAOrB(1, "N", "S", percentOfB, personality);
            personality += answerOfAOrB(2, "F", "T", percentOfB, personality);
            personality += answerOfAOrB(3, "P", "J", percentOfB, personality);
                        System.out.println(personality); 
//             pOrJ(percentOfB);
            return name + ": " + Arrays.toString(percentOfB) +
            " = " + personality;
        }
        
        /**
        * Answers which character for each answer.
        *
        */
        public static String answerOfAOrB (int index, String B, String A, 
                                            int[] percentOfB, String result) {
            String personality = result;
            if (percentOfB[index] > 50) {
                personality = B;
            } else if (percentOfB[index] == 50) {
                personality = "X";
            }   else {
                personality = A;
            }
            return personality;     
        }
        /**
        * Answers if the person is extrovert or introvert.
        * 
        * @param percentOfB Test result.
        * @return answer of either E or I.
        */
        public static String iOrE (int[] percentOfB, String result) {
            String personality = result;
            int index = indexOfAnswer(0, percentOfB);
            if (percentOfB[index] > 50) {
                personality = "I";
            } else if (percentOfB[index] == 50) {
                personality = "X";
            }   else {
                personality = "E";
            }
            return personality;     
        }

        /**
        * Returns if user has more intuition(N), sensation(S), or is neither. 
        * 
        * @param percentOfB Test result.
        * @return answer of either E or I.
        */        
        public static String nOrS (int[] percentOfB, String result) {
            String personality = result;
            int index = indexOfAnswer(1, percentOfB);
            if (percentOfB[index] > 50) {
                personality = "N";
            } else if (percentOfB[index] == 50) {
                personality = "X";
            }   else {
                personality = "S";
            }
            return personality;        
        }
        
        /**
        * Returns the correct index to answer the letters.
        */
        public static int indexOfAnswer(int index, int[] percentOfB) {
            return index % percentOfB.length;
        }
        
        /**
        * Returns if user has more Thinking(T), Feeling(F), or is neither. 
        * 
        * @param percentOfB Test result.
        * @return answer of either T or F.
        */        
        public static String tOrF (int[] percentOfB, String result) {
            String personality = result;
            int index = indexOfAnswer(2, percentOfB);
            if (percentOfB[index] > 50) {
                personality += "F";
            } else if (percentOfB[index] == 50) {
                personality += "X";
            }   else {
                personality += "T";
            }
            return personality;        
        }
        
        /**
        * Returns if user has more Perceiving(P), Judging(J), or is neither. 
        * 
        * @param percentOfB Test result.
        * @return answer of either T or F.
        */        
        public static String pOrJ (int[] percentOfB) {
            String personality = "";
            if (percentOfB[NUMBER_OF_CHARACTERS - 2] > 50) {
                personality += "P";
            } else if (percentOfB[0] == 50) {
                personality += "X";
            }   else {
                personality += "J";
            }
            return personality;        
        }        
        

}