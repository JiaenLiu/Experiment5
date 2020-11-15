package com.company;

import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student extends Member implements Homework{

    private List<Course> courseToLearn = new ArrayList<Course>();
    private int scoreUpperbound = 28;
    private int currentScore;

    public Student(Integer id, String n, boolean sex, int s) {
        super(id,n,sex);
        scoreUpperbound = s;
    }

    public void clearAll() {
        courseToLearn.clear();
    }

    public void unEnrollACourse(Course course) {
        if (courseToLearn.contains(course)) {
            courseToLearn.remove(course);
            System.out.println("Successfully unenroll course " + "\n" + course.toString());
            return;
        }
        System.out.println("You do not enroll this course" + "\n" + course.toString());
    }

    private void findCurrentScore() {
        for (Course c : courseToLearn) {
            currentScore = currentScore + c.getScore();
        }
    }

    @Override
    public String toString() {
        String output = "Student: " + this.getName() + "\n";
        for (Course c :courseToLearn) {
            output = output + c.toString();
        }
        return output;
    }

    public void addCourseToLearn(Course c) {
        this.findCurrentScore();
        if (currentScore + c.getScore() > scoreUpperbound && c.isFull()) {
            System.out.println("You can not choose more course due to your upperbound of scores or the course is full.");
            return;
        }
        else {
            System.out.println("Successfully Added!!!!!!!");
            this.courseToLearn.add(c);
            c.addOneStudent();
            c.addStudent(this);
        }
    }

    public int getScoreUpperbound() {
        return scoreUpperbound;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    /**
     *
     * @param filename
     * @return
     */

    private StringBuffer fileReader(String filename) {
        StringBuffer toRet = new StringBuffer();
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
            reader = new BufferedReader(isr);
            toRet.append(reader.readLine());
        } catch (IOException e) {
            System.out.println("Problem loading file: " + filename);
            e.printStackTrace();
        }
        return toRet;
    }

    /**
     *
     * @param toOutput
     * @param wordCount
     * @param wordToFind
     */
    private void outputFile(StringBuffer toOutput, String outputFilename, int wordCount, String wordToFind) {
        PrintWriter out;
        try {
            out = new PrintWriter(outputFilename, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            String wordCountInfo = "\n" + wordToFind + "ÓÐ: " + wordCount + "¸ö¡£";
            toOutput.append(wordCountInfo);
            out.println(toOutput);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    private int countWord(String wordToFind, String input) {
        int toRet = 0;
        String[] words = wordToFind.split("");

        for (String word : words) {
            if (word.equals(wordToFind)) {
                toRet++;
            }
        }
        return toRet;
    }

    public static List<String> getList(String str, int length,
                                       int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            String childStr = substring(str, i * length,
                    (i + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    public static String substring(String str, int x, int y) {
        if (x > str.length())
            return null;
        if (y > str.length()) {
            return str.substring(x, str.length());
        } else {
            return str.substring(x, y);
        }
    }

    /**
     *  @param inputFilename
     * @param outputFilename
     * @param wordToFind
     */
    public void doHomework(String inputFilename, String outputFilename, String wordToFind) {
        StringBuffer input = fileReader(inputFilename);
        String word = input.toString();
        int size = word.length()/8;
        if (size%8 != 0) {
            size++;
        }
        List<String> words = getList(word,7,size);
        int index = 0;
        int count = countWord(wordToFind,word);
        StringBuffer output = new StringBuffer();

        for (String s : words){

            if (index%2 == 0) {
                output.append(s + "£¬");
                index++;
            }
            else {
                output.append(s+ "¡£" + "\n");
                index++;
            }
        }

        outputFile(output,outputFilename,count,wordToFind);
    }

}
