package com.company;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String inputFile = "data/input.txt";
        String outputFile = "data/output.txt";
        String studentData = "";
        BufferedReader reader = null;
        String[] data = null;
        boolean sex = false;
        try {
            reader = new BufferedReader(new FileReader(outputFile));
            studentData = reader.readLine();
            data = studentData.split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data[3].equals("1")) {
            sex = true;
        }

        Student testStudent1 = new Student(Integer.parseInt(data[0]),data[1],sex,Integer.parseInt(data[3]));

        testStudent1.doHomework(inputFile,outputFile,"Со");

    }
}
