import java.util.*;

import java.util.Scanner;

public class Logicexecution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] arr = new int[7];              
        int len = scanner.nextInt();       

        for (int i = 0; i < len; i++) {
            arr[i] = scanner.nextInt();     
        }

       
       

        scanner.close(); 
    }
}