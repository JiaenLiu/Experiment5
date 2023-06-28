# Experiment5
12334321 test
## 实验目的

掌握字符串String及其方法的使用  
掌握文件的读取/写入方法  
掌握异常处理结构  

## 实验要求

### 基本要求

在某课上,学生要提交实验结果，该结果存储在一个文本文件A中。  
文件A包括两部分内容：  
一是学生的基本信息；  
二是学生处理后的作业信息，该作业的业务逻辑内容是：利用已学的字符串处理知识编程完成《长恨歌》古诗的整理对齐工作，写出功能方法，实现如下功能：  

1.	每7个汉字加入一个标点符号，奇数时加“，”，偶数时加“。”  
2.	允许提供输入参数，统计古诗中某个字或词出现的次数  
3.	输入的文本来源于文本文件B读取，把处理好的结果写入到文本文件A  
4.	考虑操作中可能出现的异常，在程序中设计异常处理程序  

### 输入格式

汉皇重色思倾国御宇多年求不得杨家有女初长成养在深闺人未识天生丽质难自弃一朝选在君王侧回眸一笑百媚生六宫粉黛无颜色春寒赐浴华清池温泉水滑洗凝脂侍儿扶起娇无力始是新承恩泽时云鬓花颜金步摇芙蓉帐暖度春宵春宵苦短日高起从此君王不早朝承欢侍宴无闲暇春从春游夜专夜后宫佳丽三千人三千宠爱在一身金屋妆成娇侍夜玉楼宴罢醉和春姊妹弟兄皆列士可怜光采生门户遂令天下父母心不重生男重生女骊宫高处入青云仙乐风飘处处闻缓歌慢舞凝丝竹尽日君王看不足渔阳鼙鼓动地来惊破霓裳羽衣曲九重城阙烟尘生千乘万骑西南行

### 输出格式

汉皇重色思倾国，御宇多年求不得。  
杨家有女初长成，养在深闺人未识。  
天生丽质难自弃，一朝选在君王侧。  
回眸一笑百媚生，六宫粉黛无颜色。  
春寒赐浴华清池，温泉水滑洗凝脂。  
侍儿扶起娇无力，始是新承恩泽时。  
云鬓花颜金步摇，芙蓉帐暖度春宵。  
春宵苦短日高起，从此君王不早朝。  

### 附带要求

1.	设计学生类（可利用之前的）；  
2.	采用交互式方式实例化某学生；  
3.	设计程序完成上述的业务逻辑处理，并且把“古诗处理后的输出”结果存储到学生基本信息所在的文本文件A中。  

## 实验架构

在第三次实验设计的基础上新增一个接口，名为Homework，在其中声明方法doHomework，参数类型都是字符串，分别为inputFilename,outputFilename & wordToFind。  
并且在Student类实现接口声明的方法。其中统计指定字符就是将字符串做一个个分割开，再用for循环进行统计。

## 实现过程&核心代码

### 实现文件格式化

采用字符串的切割，使用String类中的substring方法，每7个字符进行一次切割，并将切割后的字符串放在一个List中。  
之后在输出List内内容时，采用StringBuffer进行处理，List中index为偶数时，添加一个“，”，当index为奇数时，添加“。”和“\n”。  

```java
// 获取任意长度的子字符串
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
```

```java
    /**
     * To get the formalize the input file and find the number of the word in the input file. 
     * 
     * 
     * @param inputFilename
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
                output.append(s + "，");
                index++;
            }
            else {
                output.append(s+ "。" + "\n");
                index++;
            }
        }

        outputFile(output,outputFilename,count,wordToFind);
    }
```

### 文件的读取与输出

这里仅介绍实现doHomework方法所而外添加的文件的读取与输入模块，对于Main文件中的文件读取不在做介绍。

#### 读取


使用StringBuffer作为缓冲区，并且调用FileInputStream作为阅读器，指定编码格式为“UTF-8”。  
在读取过程中采用try-catch结构来进行异常的捕获。捕获的异常为IOException。

```java
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
```

#### 输出

在创建Writer时采用try-catch结构用于处理异常。之后使用try-catch来获取输出过程中的异常。

```java
  private void outputFile(StringBuffer toOutput, String outputFilename, int wordCount, String wordToFind) {
        PrintWriter out;
        try {
            out = new PrintWriter(outputFilename, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            String sex = "female";
            String wordCountInfo = "\n" + wordToFind + "有: " + wordCount + "个。" + "\n";
            if (getSex() == true) {
                sex = "male";
            }
            String studentInfo = "Name: " + getName() + " ID: " + getIdNumber() + " Sex: " + sex + " Score: " + getScoreUpperbound();
            toOutput.append(wordCountInfo);
            toOutput.append(studentInfo);
            out.println(toOutput);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
```

### 实验结果

汉皇重色思倾国，御宇多年求不得。  
杨家有女初长成，养在深闺人未识。  
天生丽质难自弃，一朝选在君王侧。  
回眸一笑百媚生，六宫粉黛无颜色。  
春寒赐浴华清池，温泉水滑洗凝脂。  
侍儿扶起娇无力，始是新承恩泽时。  
云鬓花颜金步摇，芙蓉帐暖度春宵。  
春宵苦短日高起，从此君王不早朝。  
承欢侍宴无闲暇，春从春游夜专夜。  
后宫佳丽三千人，三千宠爱在一身。  
金屋妆成娇侍夜，玉楼宴罢醉和春。  
姊妹弟兄皆列土，可怜光彩生门户。  
遂令天下父母心，不重生男重生女。  
骊宫高处入青云，仙乐风飘处处闻。  
缓歌慢舞凝丝竹，尽日君王看不足。  
渔阳鼙鼓动地来，惊破霓裳羽衣曲。  
九重城阙烟尘生，千乘万骑西南行。  
翠华摇摇行复止，西出都门百余里。  
六军不发无奈何，宛转蛾眉马前死。  
花钿委地无人收，翠翘金雀玉搔头。  
君王掩面救不得，回看血泪相和流。  
黄埃散漫风萧索，云栈萦纡登剑阁。  
峨嵋山下少人行，旌旗无光日色薄。  
蜀江水碧蜀山青，圣主朝朝暮暮情。  
行宫见月伤心色，夜雨闻铃肠断声。  
天旋地转回龙驭，到此踌躇不能去。  
马嵬坡下泥土中，不见玉颜空死处。  
君臣相顾尽沾衣，东望都门信马归。  
归来池苑皆依旧，太液芙蓉未央柳。  
芙蓉如面柳如眉，对此如何不泪垂。  
春风桃李花开夜，秋雨梧桐叶落时。  
西宫南苑多秋草，落叶满阶红不扫。  
梨园弟子白发新，椒房阿监青娥老。  
夕殿萤飞思悄然，孤灯挑尽未成眠。  
迟迟钟鼓初长夜，耿耿星河欲曙天。  
鸳鸯瓦冷霜华重，翡翠衾寒谁与共。  
悠悠生死别经年，魂魄不曾来入梦。  
临邛道士鸿都客，能以精诚致魂魄。  
为感君王辗转思，遂教方士殷勤觅。  
排空驭气奔如电，升天入地求之遍。  
上穷碧落下黄泉，两处茫茫皆不见。  
忽闻海上有仙山，山在虚无缥渺间。  
楼阁玲珑五云起，其中绰约多仙子。  
中有一人字太真，雪肤花貌参差是。  
金阙西厢叩玉扃，转教小玉报双成。  
闻道汉家天子使，九华帐里梦魂惊。  
揽衣推枕起徘徊，珠箔银屏迤逦开。  
云鬓半偏新睡觉，花冠不整下堂来。  
风吹仙袂飘飖举，犹似霓裳羽衣舞。  
玉容寂寞泪阑干，梨花一枝春带雨。  
含情凝睇谢君王，一别音容两渺茫。  
昭阳殿里恩爱绝，蓬莱宫中日月长。  
回头下望人寰处，不见长安见尘雾。  

杨有: 1个。  
Name: Liu ID: 77778888 Sex: male Score: 3  

### 实验感想

本次实验原理较为简单，只需要掌握字符串的一些基本方法和java异常处理结构的规则既可以轻松的完成。  
本次实验也加深了我对字符串的一些理解，尤其是关于字符串的切割这一方面。同时，也使得我对于java异常处理有了更深刻的认识。
