import java.io.*;
import java.util.*;

/* Question 1: Static Affairs */
class TestStatic1{
	static int a = 10;
  
  	static void setA(){
    	a = 6;
    }
}

public class Solution {

    public static void main(String[] args) {
        TestStatic1 ts1 = new TestStatic1();
        TestStatic1.setA();
        System.out.println(ts1.a);
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        TestStatic1 ts2 = new TestStatic1();
        TestStatic1.a = n;
        System.out.println(ts2.a);
    }
}


/* Question 2: this has super powers */
class Parent{
	int a;
    int b;
  
    Parent(){
    	a = 5;
        b = 10;
    }
  
    Parent(int a, int b){
        this.a = a;
        this.b = b;
    }
}

class Child extends Parent{
	int b;
  
    Child(){ 
        b = 3;
    }
  
    Child(int a, int b){
        super(a, 2*b);
        this.b = b;
    }
  
    void print(){
        System.out.println(a);
        System.out.println(super.b);
        System.out.println(this.b);
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        Child c1 = new Child();
        Child c2 = new Child(x, y);

        c1.print();
        c2.print();
    }
}


/* Question 3: Overriding Powers */
class Card{
	String name = "John Doe";	
  
  	int credits = 10;

  	void print(){
     	System.out.println("This card belongs to " + name);
    }
}

class SilverCard extends Card{
	SilverCard(int amountPaid){
        if(amountPaid >= 1000 && amountPaid <= 1999){
            this.credits = 5;
        }else if(amountPaid >= 2000 && amountPaid <= 3999){
            this.credits = 10;
        }else if(amountPaid >= 4000 && amountPaid <= 9999){
            this.credits = 15;
        }else if(amountPaid >= 10000){
            this.credits = 20;
        }
    }
  
    void print(){
         System.out.println("Credits left in the card: " + this.credits);
         System.out.println("This card belongs to " + name);
    }
  
  	void deduct(int creditsSpent){
        if(creditsSpent > this.credits){
            System.out.println("Insufficient balance");
        }else{
            this.credits -= creditsSpent;
        }
    }
}

class GoldCard extends SilverCard{
    final int FREE_CREDITS = 10;
    
    GoldCard(int amountPaid){
        super(amountPaid);
        this.credits += FREE_CREDITS;
    }
  
  	void print(){
         System.out.println("Credits left in the card: " + credits);
         System.out.println("This card belongs to " + name);
    }
  
    private void topUp(){
        this.credits += 5;
        System.out.println("Credits left in the card: " + this.credits);
    }
  
    void deduct(int creditsSpent){
        while(this.credits < creditsSpent){
            topUp();
        }
        this.credits -= creditsSpent;
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cardType = sc.next();
        int amount = sc.nextInt();
        int numOps = sc.nextInt();

        if (cardType.equals("Silver")) {
            SilverCard silver = new SilverCard(amount);
            for (int i = 0; i < numOps; i++) {
                int opType = sc.nextInt();
                if (opType == 1) {
                    silver.print();
                } else {
                    int creditSpent = sc.nextInt();
                    silver.deduct(creditSpent);
                }
            }
        } else {
            GoldCard gold = new GoldCard(amount);
            for (int i = 0; i < numOps; i++) {
                int opType = sc.nextInt();
                if (opType == 1) {
                    gold.print();
                } else {
                    int creditSpent = sc.nextInt();
                    gold.deduct(creditSpent);
                }
            }
        }
    }
}


/* Question 4: Pattern Printing */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        
        int last = 1;
        for(int line = 1; line <= n; line++){
            for(int i = 1; i <= line; i++){
                System.out.print(last++ + " ");
            }
            last--;
            System.out.println();
        }
    }
}
