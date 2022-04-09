package com.jsy.learn.designpatterns.strategy;

import java.util.Arrays;

public class Sorter<T> {

    public void sort(T[] arr, Comparator<T> comparator) {
        for(int i=0; i<arr.length - 1; i++) {
            int minPos = i;

            for(int j=i+1; j<arr.length; j++) {
                minPos = comparator.compare(arr[j],arr[minPos])==-1 ? j : minPos;
            }
            swap(arr, i, minPos);
        }
    }

    void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Cat[] cats = {new Cat(3, 3), new Cat(5, 5), new Cat(1, 1)};
        Sorter<Cat> catSorter = new Sorter<>();
        //Cat height 排序
        catSorter.sort(cats, new CatHeightComparator());
        System.out.println(Arrays.toString(cats));
        //Cat Weight 排序
        catSorter.sort(cats, new CatWeightComparator());
        System.out.println(Arrays.toString(cats));

        //Dog 排序
        Dog[] dogs = {new Dog(3), new Dog(5), new Dog(1)};
        Sorter<Dog> dogSorter = new Sorter<>();
        dogSorter.sort(dogs, new DogComparator());
        System.out.println(Arrays.toString(dogs));

        //lomda表达式 -> 任意写
        catSorter.sort(cats, (o1, o2)->{
            if(o1.weight < o2.weight){
                return -1;
            }else if (o1.weight>o2.weight){
                return 1;
            }else{
                return 0;
            }
        });
        System.out.println(Arrays.toString(cats));
    }
}
