package pl.coderslab.beans;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class RandomMachine {
    public static void main(String[] args) {
        int [][] tab = RandomMachine.getTab(9);
        for(int [] i : tab){
            System.out.println(Arrays.toString(i));
        }
    }

    public static int[][] getTab(int length){
        int [][] tab = new int [length][];
        for(int i = 0; i < tab.length; i++){
            tab[i] = new int [(int)(Math.random() * 3) + 1];//{1, 2, 3}
            Integer [] helpTab = randomIndexes(length, i);
            for(int j = 0; j < tab[i].length; j++){
                tab[i][j] = helpTab[j];
            }
        }

        return tab;
    }

    private static Integer[] randomIndexes(int length, int otherThanThis){
        List<Integer> list = IntStream
                .range(0, length)
                .filter(el -> el != otherThanThis)
                .boxed()
                .collect(toList());

        Collections.shuffle(list);
        return list.toArray(new Integer [list.size()]);
    }
}
