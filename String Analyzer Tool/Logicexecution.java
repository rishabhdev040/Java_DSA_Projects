
import java.util.*;
import java.util.Scanner;

public class Logicexecution {

    public static void main(String[] args) {
      Scanner scanner = new Scanner (System.in);

      // input main string from user to analyse 

      System.out.println("Enter the string to analyze");
      String input = scanner.nextLine();
      String lower = input.toLowerCase();

      //Basic Stats

      int vowels =0, consonants = 0, digits = 0, special=0;
      Map<Character, Integer> freqMap = new HashMap<>();
      
      for (char c :lower.toCharArray()){
        if ( Character .isLetter(c)){
            if ("aeiou".indexOf(c)>= 0)vowels++;
            else consonants++;

        }else if (Character.isDigit(c)){
            digits++;

        }
        else if (c!= ' '){
            special++;

        }

        if ( c!=' '){
            freqMap.put(c,freqMap.getOrDefault(c,0)+1);
        }
      }


      //To calculate the numbers for words and sentences
      String[] words = input.trim().split("\\s+");
      int wordCount = words.length;
      int sentenceCount = input.split("[.!?]").length;

      


      // Palindrome Check
      String reversed = new StringBuilder(lower.replaceAll("\\s+", "")).reverse().toString();
      boolean isPalindrome = lower.replaceAll("\\s+", "").equals(reversed);



       System.out.println("\n--- String Analysis Report ---");
        System.out.println("Total characters (including space): " + input.length());
        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        System.out.println("Digits: " + digits);
        System.out.println("Special characters: " + special);
        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        
        System.out.println("Is Palindrome? " + (isPalindrome ? "Yes" : "No"));
       





    }
}