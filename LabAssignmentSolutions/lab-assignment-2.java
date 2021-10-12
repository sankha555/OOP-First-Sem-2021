import java.io.*;
import java.util.*;

/* Question 1: Reverse String */
class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		s = s.trim();
		String[] arr = s.split(" ");
		
		StringBuilder sb = new StringBuilder();
		for(int i = arr.length-1; i>=0; --i) {
			sb.append(arr[i]).append(" ");
		}
		s = sb.toString();
		s = s.trim();
		System.out.println(s);
		sc.close();
	}

}


/* Question 2: Sum of Three 3 */
class CustomReturn {
	private boolean found;
	private int[] indexes;
	public CustomReturn(){
		found = false;
	}
	public CustomReturn(int a, int b, int c) {
		found = true;
		indexes = new int[3];
		indexes[0] = a;
		indexes[1] = b;
		indexes[2] = c;
	}
	
	@Override
	public String toString() {
		if(!found) {
			return "No Valid Indexes exist!";
		}
		else {
			return indexes[0] + " " + indexes[1] + " " + indexes[2] + " " + "\n";
		}
	}
}

class ThreeSum {
	private int[] arr;
	
	private boolean isSorted;
	
	public ThreeSum(int[] arr) {
		this.arr = arr;
		isSorted = true;
	}
	
	public void replace(int first, int second) {
		int index = Arrays.binarySearch(arr, first);
		arr[index] = second;
		isSorted = false;
	}
	
	public String printArr() {
		if(!isSorted) {
			Arrays.sort(arr);
			isSorted = true;
		}
		return Arrays.toString(arr);
	}
	
	public String threeSum(int target) {
		
		if(!isSorted) {
			Arrays.sort(arr);
			isSorted = true;
		}
		for(int i = 0; i<arr.length; ++i) {
			int j = i+1, k = arr.length-1;
			while(j<k) {
				if(arr[i] + arr[j] + arr[k]==target) {
					return (new CustomReturn(i, j, k)).toString();
				}
				else if(arr[i] + arr[j] + arr[k]<target) {
					j++;
				}
				else {
					k--;
				}
			}
		}
		return (new CustomReturn()).toString();
	}	
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		s = s.trim();
		String[] arr = s.split(" ");
		
		StringBuilder sb = new StringBuilder();
		for(int i = arr.length-1; i>=0; --i) {
			sb.append(arr[i]).append(" ");
		}
		s = sb.toString();
		s = s.trim();
		System.out.println(s);
		sc.close();
	}

}


/* Question 3: Longest Common Prefix */
import java.io.*;
import java.util.*;

class LongestCommonPrefix{
  	int n;
  	/* Declare an instance variable to store the list of strings */
    String[] listOfStrings;
  
  	LongestCommonPrefix(String[] listOfStrings){
    	this.listOfStrings = listOfStrings; 
        this.n = this.listOfStrings.length;
    }
  
  	public void augment(String s, int maxLength){
    	/* 
           Implement the augment method to augment/modify a string in the list of strings in order to 
           act as a utility method to solve the given question	
        */
        while(s.length() < maxLength){
            s = s + "$";
        }
    }
  
  	public String longestPrefixString(){
    	/* Complete the method to find longest prefix in all strings of the list of strings */   
        int maxLength = 0;
        for(int i = 0; i < this.n; i++){
            String s = this.listOfStrings[i];
            if(s.length() > maxLength){
                maxLength = s.length();
            }
        }
        
        for(int i = 0; i < this.n; i++){
            this.augment(this.listOfStrings[i], maxLength);
        }
        
        String ans = "";
        for(int j = 0; j < maxLength; j++){
            String standard = this.listOfStrings[0];
            if(standard.charAt(j) == '$'){
                break;
            }
            int flag = 0;
            for(int i = 1; i < this.n; i++){
                String s = this.listOfStrings[i];
                if(s.charAt(j) != standard.charAt(j)){
                    flag = 1;
                    break;
                }
            }
            if(flag == 1){
                break;
            }else{
                ans = ans + standard.charAt(j);
            }
        }
        if(ans.length() == 0){
            return "-";
        }
        return ans;
    }
}

class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n;
        n = sc.nextInt();
        
        String[] listOfStrings = new String[n];
        for(int i = 0; i < n; i++){
            listOfStrings[i] = sc.next();
        }
        
        LongestCommonPrefix lcp = new LongestCommonPrefix(listOfStrings);
        System.out.println(lcp.longestPrefixString());
    }
}


/* Question 4: Minesweeper Queries */
class Minesweeper{
  	private int n;
  	private int m;
	/* Define an private instance variable to represent the grid */
    int[][] grid;
  
  	Minesweeper(int n, int m, int[][] grid){
    	/* Complete the definition of the constructor and initiate the instance variables */
        this.n = n;
        this.m = m;
        this.grid = grid;
    }
  
  	private boolean isValidCell(int i, int j){
    	/*
        	Implement the method to determine if a particular cell is valid for the given grid.
            This method should be used in the following two methods.
        */
        if(i <= 0 || i > n || j <= 0 || j > m){
            return false;
        }
        return true;
    }
  
  	public void countSurroundingMines(int i, int j){
    	/*
        	Complete the definition of the method to count the total number of mines in the
            surrounding cells of cell (i, j)
        */ 
        
        if(!isValidCell(i, j)){
            System.out.println("Invalid query");
            return ;
        }
        
        int[] di = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dj = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        int ans = 0;
        for(int k = 0; k < 8; k++){
            if(isValidCell(i+di[k], j+dj[k])){
                ans += grid[i+di[k]][j+dj[k]];
            }
        }
        System.out.println(ans);
    }
  
  	public void findSafeCell(int i, int j){
    	/*
        	Complete the definition of the method to find the leftmost and uppermost safe cell
            for the cell (i, j)
        */  
        if(!isValidCell(i, j)){
            System.out.println("Invalid query");
            return ;
        }
        
        int delta = 1;
        int leftMargin = j - 1;
        int rightMargin = m - j;
        int topMargin = i - 1;
        int bottomMargin = n - i;
        
        int maxDelta = leftMargin;
        if(rightMargin > maxDelta){
            maxDelta = rightMargin;
        };
        
        if(topMargin > maxDelta){
            maxDelta = rightMargin;
        }
        
        if(bottomMargin > maxDelta){
            maxDelta = bottomMargin;
        }
        
        while(delta <= maxDelta){
            int l = j-delta, r = j+delta;
            int t = i-delta, b = i+delta;
            
            for(int y = t; y <= b; y++){
                int x = l;
                if(!isValidCell(y, x)){
                    continue;
                }
                
                if(grid[y][x] == 0){
                    String ans = Integer.toString(y) + " " + Integer.toString(x);
                    System.out.println(ans);
                    return;
                }
            }
            
            for(int x = l+1; x < r; x++){
                int[] ys = {t, b};
                for(int p = 0; p <= 1; p++){
                    int y = ys[p];
                    if(!isValidCell(y, x)){
                        continue;
                    }

                    if(grid[y][x] == 0){
                        String ans = Integer.toString(y) + " " + Integer.toString(x);
                        System.out.println(ans);
                        return;
                    }
                }
            }
            
            for(int y = t; y <= b; y++){
                int x = r;
                if(!isValidCell(y, x)){
                    continue;
                }
                
                if(grid[y][x] == 0){
                    String ans = Integer.toString(y) + " " + Integer.toString(x);
                    System.out.println(ans);
                    return;
                }
            }
            delta++;
        }
        System.out.println("No safe cell exists");
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    
        int n, m;
        n = sc.nextInt()+1;
        m = sc.nextInt()+1;
        
        int[][] grid = new int[n][m];
        for(int i = 1; i < n; i++){
            String s = sc.next();
            
            for(int j = 1; j < m; j++){
    
                grid[i][j] = s.charAt(j-1) - '0';
            }
        }
        
        Minesweeper ms = new Minesweeper(n-1, m-1, grid);
        
        int q = sc.nextInt();
        for(int k = 0; k < q; k++){
            int type = sc.nextInt();
            int i = sc.nextInt();
            int j = sc.nextInt();
            if(type == 1){
                ms.countSurroundingMines(i, j);
            }else{
                ms.findSafeCell(i, j);
            }
        }
    }
}
