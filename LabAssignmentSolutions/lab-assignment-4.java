import java.io.*;
import java.util.*;

/* Question 1: Sort and Count */
class StringComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return -1*s1.compareTo(s2);
    }
}

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Integer input = sc.nextInt();
		TreeMap<String, Integer> mp = new TreeMap<>(new StringComparator());
		while(input > 0) {
			String read = sc.nextLine();
			if(read == "") {
				continue;
			}
			if(!mp.containsKey(read)) {
				mp.put(read, 1);
			}
			else {
				mp.put(read, mp.get(read)+1);
			}
			input--;
		}
		sc.close();
		for(Map.Entry<String, Integer> entry : mp.entrySet()) {
			System.out.println(entry.getKey() + ":"+ entry.getValue());
		}
	}

}


/* Question 2: Subset Array */
class FindSub{
	private List<Integer> list1;
	private List<Integer> list2;
	private HashSet<Integer> set;
	public FindSub(List<Integer>bigList, List<Integer>smallList) {
		this.list1 = bigList;
		this.list2 = smallList;
		set = new HashSet<>(list1);
	}
	public String isSubset() {
		for(Integer i : list2) {
			if(!set.contains(i)) {
				return i.toString();
			}
		}
		return "Subset";
	}
	
}

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Integer n1 = sc.nextInt();
		List<Integer> list1 = new ArrayList<>();
		while(n1>0) {
			list1.add(sc.nextInt());
			n1--;
		}
		Integer n2 = sc.nextInt();
		List<Integer> list2 = new ArrayList<>();
		while(n2>0) {
			list2.add(sc.nextInt());
			n2--;
		}
		sc.close();
		FindSub obj = list1.size()>list2.size()? new FindSub(list1,list2) : new FindSub(list2, list1);
		System.out.println(obj.isSubset());
	}

}


/* Question 3: Balanced Parantheses Class */
class BalancedParantheses{
	private Stack<Character> braces;
  	
  	BalancedParantheses(){
    	/* implement the constructor to initiate the stack of braces */ 
        this.braces = new Stack<Character>();
    }
  
  	public void handleOpeningBrace(){
        this.braces.push('{');  
    }
  
  	public void handleClosingBrace(){
        if(!this.braces.empty()){
            if(this.braces.peek() == '{'){
                this.braces.pop();
            }else{
                this.braces.push('}');
            }
        }else{
            this.braces.push('}');
        }
    }
  
  	public boolean testForBalance(){
    	return this.braces.empty();
    }
}

class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        BalancedParantheses bp = new BalancedParantheses();
        String s = sc.next();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '{'){
                bp.handleOpeningBrace();
            }else{
                bp.handleClosingBrace();
            }
        }
        
        if(bp.testForBalance()){
            System.out.println("Balanced");
        }else{
            System.out.println("Not balanced");
        }
    }
}


/* Question 4: Linked List Combination */
class LinkedListHandler{
  	private LinkedList<Integer> ll;
  
  	LinkedListHandler(){
  		/* implement the constructor to initialize the linked list */
        this.ll = new LinkedList<Integer>();
  	}
	
  	public void populate(int element){
    	/* implement the overloaded method to add an element to the linked list taken from standard input */
        this.ll.add(new Integer(element));
    }
  
  	public void populate(LinkedList<Integer> r, LinkedList<Integer> s){
    	/* implement the overloaded method to populate elements from two linked lists r and s */
        for(int i = 0; i < r.size(); i++){
            this.ll.add(r.get(i));
        }
        
        for(int i = 0; i < s.size(); i++){
            this.ll.add(s.get(i));
        }
    }
  
  	public void queryResponse(int pos){
    	/* 
        	implement this method to return the element of the linked list at position "pos"
        	or indicate an invalid query
        */	  
        if(pos <= 0 || pos > this.ll.size()){
            System.out.println("Invalid query");
        }else{
            System.out.println(this.ll.get(pos-1));
        }
    }
}

class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        LinkedListHandler r = new LinkedListHandler();
        LinkedListHandler s = new LinkedListHandler();
        LinkedListHandler t = new LinkedListHandler();
        
        LinkedList<Integer> listR = new LinkedList<Integer>();
        LinkedList<Integer> listS = new LinkedList<Integer>();
        
        
        int lr, ls;
        lr = sc.nextInt();
        for(int i = 0; i < lr; i++){
            int x = sc.nextInt();
            r.populate(x);
            listR.add(x);
        }
        
        ls = sc.nextInt();
        for(int i = 0; i < ls; i++){
            int x = sc.nextInt();
            s.populate(x);
            listS.add(x);
        }
        Collections.reverse(listS);
        
        t.populate(listR, listS);
        
        int q = sc.nextInt();
        for(int i = 0; i < q; i++){
            int a, b;
            a = sc.nextInt();
            b = sc.nextInt();
            
            if(a == 0){
                r.queryResponse(b);
            }else if(a == 1){
                s.queryResponse(b);
            }else{
                t.queryResponse(b);
            }
        }
    }
}