import java.io.*;
import java.util.*;

/* Question 1: Left Rotate */
public static List<Integer> rotateLeft(int d, List<Integer> arr) {
    int n = arr.size();
    
    int[] rotatedArray = new int[n];
    for(int i = 0; i < n; i++){
        rotatedArray[(i+n-d)%n] = arr.get(i);
    }
    
    for(int i = 0; i < n; i++){
        arr.set(i, rotatedArray[i]);
    }
    
    return arr;
}


/* Question 2: Comparing the Triplets */
public static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
    int aliceScore = 0, bobScore = 0;
    for(int i = 0; i < a.size(); i++){
        if(a.get(i) > b.get(i)){
            aliceScore++;
        }else if(a.get(i) < b.get(i)){
            bobScore++;
        }
    }
    List<Integer> ans = new ArrayList<Integer>();
    ans.add(aliceScore);
    ans.add(bobScore);
    
    return ans;
}


/* Question 3: Grading Students */
public static List<Integer> gradingStudents(List<Integer> grades) {
    int n = grades.size();
    for(int i = 0; i < n; i++){
        int grade = grades.get(i);
        
        if(grade < 38){
            continue;
        }
        
        int onesPlace = grade % 10;
        int restOfNumber = grade / 10;
        
        int nextMultipleOf5 = 0;
        if(onesPlace > 5){
            nextMultipleOf5 = (restOfNumber + 1)*10; 
        }else{
            nextMultipleOf5 = restOfNumber*10 + 5;
        }
        
        if(nextMultipleOf5 - grade < 3){
            grade = nextMultipleOf5;
        }
        
        grades.set(i, grade);
    }
    return grades;
}


/* Question 4: Diagonal Difference */
public static int diagonalDifference(List<List<Integer>> arr) {
        int n = arr.size();
        int m = arr.get(0).size();
        
        int d1 = 0, d2 = 0;
        for(int i = 0; i < n; i++){
            d1 += arr.get(i).get(i);
            d2 += arr.get(i).get(n-i-1);
        }
        return d2 >= d1 ? d2 - d1 : d1 - d2;
    }