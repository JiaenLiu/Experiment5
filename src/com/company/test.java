package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] a) {
        Scanner sc = new Scanner("��ѧ87�֣�����76�֣�Ӣ��96��");
        Pattern p = Pattern.compile("\\d+");
        List<String> Strings = sc.findAll(p).map(MatchResult::group).collect(Collectors.toList());
        List<Integer> ints = new ArrayList<Integer>();
        int allScore = 0;
        for (String s : Strings) {
            ints.add(Integer.parseInt(s));
        }
        for (Integer i : ints) {
            allScore += i;
        }
        String output = "�ܳɼ�Ϊ: " + allScore + " " + "ƽ���ɼ�Ϊ: " + allScore/3;
        System.out.println(output);

    }
}
