package Assignment2;
import java.util.*;
import java.util.Queue;
import java.io.*;

public class employeeTree {
	employeeBST eBST = new employeeBST();
	LinkedList<Node> ceo = new LinkedList<Node>();
	employeeTree(String CEOname){  //constructor for a new tree//
		Node ceoNode = new Node(CEOname, 0);
		ceo.add(ceoNode);
		ceo.get(0).parent = null; 
		//ceo.get(0).next = null; //ceo has no parent/sibling//
		eBST.root.name = CEOname;
		eBST.root.pointer = ceo.get(0);
	}
	class Node{
		Node parent;
		LinkedList<Node> child;
		//Node next; //sibling
		String name;
		int level;
		
		public Node(String name, int level){
			this.name = name;
			this.level = level;
		}
	}
	
	public void AddEmployee(String Empname, String existingEmpname ) {
		try {
			eBST.addEmployee(Empname);
			Node parent = eBST.searchBST(eBST.root, existingEmpname).pointer;
			
			if (parent.child == null) {
				LinkedList<Node> children = new LinkedList<Node>();
				Node temp = new Node(Empname, (parent.level) + 1);
				children.addFirst(temp);
				parent.child = children;
				children.get(0).parent = parent;
				eBST.searchBST(eBST.root, Empname).pointer = children.get(0);
			}else {
				Node temp = new Node(Empname, (parent.level) + 1);
				parent.child.push(temp);
				temp.parent = parent;
				eBST.searchBST(eBST.root, Empname).pointer = parent.child.getFirst();
			}	
		}catch( NoSuchEmployeeException e) {
			
		}
	}
	public void PrintEmployees() {
		Queue<LinkedList<Node>> q = new LinkedList<>();
		q.add(this.ceo);
		while(!q.isEmpty()) {
			LinkedList<Node> temp = new LinkedList<Node>();
			temp = q.remove();
			for (int i = 0; i < temp.size(); i++) {
				System.out.println(temp.get(i).name);
				if(temp.get(i).child!=null) {
					q.add(temp.get(i).child);
				}
			}
		}
	}
	public void DeleteEmployee(String Empname, String Newboss) {
		try {
			Node temp1 = eBST.searchBST(eBST.root, Empname).pointer;
			Node temp2 = eBST.searchBST(eBST.root, Newboss).pointer;
			if (temp2.child != null) {
				while(temp1.child != null && !temp1.child.isEmpty()) {
					temp2.child.push(temp1.child.pop());
					temp2.child.get(0).parent = temp2;
					temp2.child.get(0).level = temp2.level + 1;
				}
			}else {
				LinkedList<Node> abc = new LinkedList<Node>();
				temp2.child = abc;
				while(temp1.child != null && !temp1.child.isEmpty()) {
					temp2.child.push(temp1.child.pop());
					temp2.child.get(0).parent = temp2;
					temp2.child.get(0).level = temp2.level + 1;
				}
			}
			
			LinkedList<Node> current = new LinkedList<Node>();
			current = temp1.parent.child;
			if (current.get(0).name.compareTo(temp1.name) == 0) {
				current.pop();
			}else {
				for(int i = 1; i < current.size(); i++ ) {
					if (current.get(i).name.compareTo(temp1.name) == 0) {
						current.remove(i);
					}
				}
			}
		}catch (NoSuchEmployeeException e) {
			
		}
		
	}
	public void LowestCommonBoss(String emp1, String emp2) {
		try {
			LinkedList<Node> b1 = new LinkedList<Node>();
			LinkedList<Node> b2 = new LinkedList<Node>();
			Node e1 = eBST.searchBST(eBST.root, emp1).pointer;
			Node e2 = eBST.searchBST(eBST.root, emp2).pointer;
			b1.addFirst(e1);
			b2.addFirst(e2);
			
			int l = e1.level;
			for (int i = 0; i < l; i++) {
				Node temp = e1.parent;
				b1.addFirst(temp);
				e1 = e1.parent;
			}
			
			int k = e2.level;
			for (int i = 0; i < k; i++) {
				Node temp = e2.parent;
				b2.addFirst(temp);
				e2 = e2.parent;
			}
			int i;
			Node lcb;
			if(b1.size() < b2.size()) {
				i = b1.size();
				lcb = b1.getFirst();
			}else {
				i = b2.size();
				lcb = b2.getFirst();
			}
			
			for (int j = 1; j < i; j++) {
				if(b1.get(j).name.compareTo(b2.get(j).name) == 0) {
					lcb = b1.get(j);
				}	
			}
			System.out.println(lcb.name);
					
		}catch (NoSuchEmployeeException e) {
			
		}
	}
	
	public static void main(String[] args) {
		
		try {
			FileInputStream f = new FileInputStream("C:\\Users\\ankit\\Onedrive\\Desktop\\4Tree.txt");
			Scanner scan = new Scanner(f);
			
			int n = Integer.parseInt(scan.next());
			String st = scan.next();
			employeeTree E = new employeeTree(scan.next());
			
			FileInputStream file = new FileInputStream("C:\\Users\\ankit\\Onedrive\\Desktop\\4Tree.txt");
			Scanner s = new Scanner(file);
			
			int num_employee = Integer.parseInt(s.next());
			while(num_employee >1) {
				String name1 = s.next();
				String name2 = s.next();
				E.AddEmployee(name1, name2);			
				
				num_employee--;
			}
			int commands = Integer.parseInt(s.next());
			while(commands >0) {
				int q = Integer.parseInt(s.next());
				if (q == 3) {
					E.PrintEmployees();
				}else if(q==2) {
					String emp1 = s.next();
					String emp2 = s.next();
					E.LowestCommonBoss(emp1, emp2);
				}else if(q==1) {
					String Empname = s.next();
					String Newboss = s.next();
					E.DeleteEmployee(Empname, Newboss);
				}else {
					String Empname = s.next();
					String existingEmpname = s.next();
					E.AddEmployee(Empname, existingEmpname);
				}
				commands--;
			}
			s.close();
			
		} catch(Exception e) {
			
		}
		
		
	}
	
}
class employeeBST{
	employeeBST(){
	}
	BSTNode root = new BSTNode();
	
	class BSTNode{
		String name;
		BSTNode right;
		BSTNode left;
		employeeTree.Node pointer;
		
		BSTNode(String name){
			this.name = name;
		}
		BSTNode(){
			
		}
	}

	void addEmployee(String name) {
		BSTNode temp = new BSTNode (name);
		if (root == null) {
			root = temp;
			return;
		}else {
			insertNode(root, temp);
		}
	}
	void insertNode(BSTNode current, BSTNode newNode) {
		if ((newNode.name).compareTo(current.name) < 0){
			if (current.left == null) {
				current.left = newNode;
				return;
			}else {
				insertNode(current.left, newNode);
			}
		}else if ((newNode.name).compareTo(current.name) > 0) {
			if (current.right == null) {
				current.right = newNode;
				return;
			}else {
				insertNode(current.right, newNode);
			}
		}
	}
	BSTNode searchBST(BSTNode temp, String name) throws NoSuchEmployeeException{ //temp should be root of the BSTree//
			if(temp == null)  {
				throw new NoSuchEmployeeException();
			}else if (name.compareTo(temp.name) == 0) {
				 return temp;
			}else if (name.compareTo(temp.name) < 0) {
				return searchBST(temp.left, name);
			}else {
				return searchBST(temp.right, name);	
			}	
	}
	void inOrder(BSTNode temp) { // temp should be root of the BSTree//
		if (temp == null) {
			return;
		}
		inOrder(temp.left);
		System.out.println(temp.name);
		inOrder(temp.right);
	}
}
class NoSuchEmployeeException extends Exception {
	NoSuchEmployeeException() {
		super();
	}
}