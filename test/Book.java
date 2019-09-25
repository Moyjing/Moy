package 第12题;

public class Book {
	String  Bookname;
	String Author;
	int Monthlysalas;
	Book(){
	}
	Book(String Bookname,String Author,int Monthlysalas){
	   this.Bookname=Bookname;
	   this.Author=Author;
	   this.Monthlysalas=Monthlysalas;
	}
	void setBook(String Bookname,String Author,int Monthlysalas){
	   this.Bookname=Bookname;
	   this.Author=Author;
	   this.Monthlysalas=Monthlysalas;
	}
	void printBook(){
	   Book Book1=new Book();
	   Book1.setBook("To live","Yu Hua",1000);
	System.out.println("第一次属性设置："+"书名："+Book1.Bookname+','+"作者："+Book1.Author+','+"月销售量："+Book1.Monthlysalas);
	Book Book2=new Book("The Old Man and Sea","Hemingway",1500);
	System.out.println("第二次属性设置："+"书名："+Book2.Bookname+','+"作者："+Book2.Author+','+"月销售量："+Book2.Monthlysalas);
	}
	
}
