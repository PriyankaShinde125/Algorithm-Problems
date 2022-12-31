package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgorithmProblemMain {
    public static final int BINARY_SEARCH = 1;
    public static final int INSERTION_SORT = 2;
    public static final int BUBBLE_SORT = 3;
    public static final int ANAGRAM_STRINGS = 4;
    public static final int PRIME_NUMBERS = 5;
    public static final int PRIME_ANAGRAM = 6;
    public static final int PRIME_PALINDROME = 7;
    public static final int EXIT = 0;


    public static void main(String[] args) throws CustomException {
        AlgorithmProblemMain main = new AlgorithmProblemMain();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your choice : " +
                    "\n1 : Search word using binary search" +
                    "\n2 : Insertion Sort" +
                    "\n3 : Bubble Sort" +
                    "\n4 : Check strings are anagram or not" +
                    "\n5 : Find prime numbers between 0 - 1000" +
                    "\n6 : Find all prime anagram " +
                    "\n7 : Find all prime palindrome" +
                    "\n0 : Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case BINARY_SEARCH:
                    main.binarySearch();
                    break;
                case INSERTION_SORT:
                    main.insertionSort();
                    break;
                case BUBBLE_SORT:
                    main.bubbleSort();
                    break;
                case ANAGRAM_STRINGS:
                    System.out.println("Enter two strings");
                    String str1 = sc.next();
                    String str2 = sc.next();
                    System.out.println(main.isAnagram(str1, str2) ? "Strings are anagram " : "Strings are not anagrams");
                    break;
                case PRIME_NUMBERS:
                    Arrays.stream(main.findPrimes()).forEach(System.out::println);
                    break;
                case PRIME_ANAGRAM:
                    main.printAllAnagramPrime();
                    break;
                case PRIME_PALINDROME:
                    main.primePalindrome();
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("invalid input");
            }
        }
    }

    private void primePalindrome() {
        int[] primes = findPrimes();
        System.out.println("Palindrome prime");
        for (int i = 0; i < primes.length; i++) {
            if (isPalindrome(primes[i]))
                System.out.print(primes[i] + " ");
        }
        System.out.println("\n");
    }

    private boolean isPalindrome(int prime) {
        int temp = prime;
        int rem;
        int reverse = 0;
        while (prime > 0) {
            rem = prime % 10;
            reverse = reverse * 10 + rem;
            prime = prime / 10;
        }
        if(temp == reverse)
            return true;
        return false;
    }

    private void printAllAnagramPrime() {
        int[] primes = findPrimes();
        System.out.println("Anagram Primes");
        for (int i = 0; i < primes.length; i++) {
            for (int j = i + 1; j < primes.length; j++) {
                if (isAnagram(String.valueOf(primes[i]), String.valueOf(primes[j])))
                    System.out.println(primes[i] + " " + primes[j]);
            }
        }
    }

    private int[] findPrimes() {
        int[] primes = new int[168];
        int i = 0;
        int limit = 1000;
        int number = 3;
        primes[i] = 2;
        i++;
        while (number <= limit) {
            if (isPrime(number)) {
                primes[i] = number;
                i++;
            }
            number++;
        }
        return primes;
    }

    private boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    private boolean isAnagram(String str1, String str2) {
        if (str1.equalsIgnoreCase(str2))
            return true;

        if (str1.length() == 1 && str2.length() == 1 && !str1.equalsIgnoreCase(str2))
            return false;
        if (str1.length() != str2.length()) {
            return false;
        }
        char[] str1CharArray = str1.toCharArray();
        char[] str2CharArray = str2.toCharArray();
        Arrays.sort(str1CharArray);
        Arrays.sort(str2CharArray);
        for (int i = 0; i < str1CharArray.length; i++) {
            if (str1CharArray[i] != str2CharArray[i]) {
                return false;
            }
        }
        return true;
    }

    private void bubbleSort() {
        String[] inputList;
        int[] intList;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter space separated array elements");
        inputList = sc.nextLine().split("\\s");
        intList = new int[inputList.length];
        for (int i = 0; i < inputList.length; i++) {
            intList[i] = Integer.parseInt(inputList[i]);
        }
        for (int i = 0; i < intList.length; i++) {
            for (int j = 0; j < intList.length - i - 1; j++) {
                if (intList[j] > intList[j + 1]) {
                    int temp = intList[j];
                    intList[j] = intList[j + 1];
                    intList[j + 1] = temp;
                }
            }
        }
        Arrays.stream(intList).forEach(System.out::println);
    }

    private void insertionSort() {
        String[] wordList;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter space separated array elements");
        wordList = sc.nextLine().split("\\s");
        for (int i = 1; i < wordList.length; ++i) {
            String key = wordList[i];
            int j = i - 1;

            while (j >= 0 && wordList[j].compareTo(key) > 0) {
                wordList[j + 1] = wordList[j];
                j = j - 1;
            }
            wordList[j + 1] = key;
        }
        Arrays.stream(wordList).forEach(System.out::println);
    }

    void binarySearch() throws CustomException {
        String[] wordList = readDataFromFile();
        wordList = Arrays.stream(wordList).sorted().toArray(String[]::new);
        Arrays.stream(wordList).forEach(System.out::println);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key to search : ");
        String searchKey = sc.next();
        boolean isFound = searchRecursive(wordList, 0, wordList.length, searchKey);
        if (isFound)
            System.out.println("Key found");
        else
            System.out.println("not found");

    }

    private boolean searchRecursive(String[] wordList, int start, int length, String searchKey) {
        int mid = start + (length - start) / 2;
        if (start <= length) {
            if (searchKey.equalsIgnoreCase(wordList[mid]) || searchKey.equalsIgnoreCase(wordList[start]) || searchKey.equalsIgnoreCase(wordList[length - 1]))
                return true;
            if (searchKey.compareTo(wordList[mid]) < 0) {
                length = mid - 1;
                return searchRecursive(wordList, start, length, searchKey);
            }
            if (searchKey.compareTo(wordList[mid]) > 0) {
                start = mid + 1;
                return searchRecursive(wordList, start, length, searchKey);
            }
        }
        return false;
    }

    String[] readDataFromFile() throws CustomException {
        String[] wordList;
        try {
            Path path = Paths.get("src/main/resources/wordList.txt");
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\\s"));
            wordList = data.toLowerCase().split("\\s");
            lines.close();
        } catch (IOException e) {
            throw new CustomException(ExceptionType.IO_EXCEPTION);
        }
        return wordList;
    }
}
