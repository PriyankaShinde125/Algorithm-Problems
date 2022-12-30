package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgorithmProblemMain {
    public static final int BINARY_SEARCH = 1;
    public static final int INSERTION_SORT = 2;
    public static final int EXIT = 0;


    public static void main(String[] args) throws CustomException {
        AlgorithmProblemMain main = new AlgorithmProblemMain();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your choice : " +
                    "\n1 : Search word using binary search" +
                    "\n2 : Insertion Sort" +
                    "\n0 : Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case BINARY_SEARCH:
                    main.binarySearch();
                    break;
                case INSERTION_SORT:
                    main.insertionSort();
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("invalid input");
            }
        }
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
