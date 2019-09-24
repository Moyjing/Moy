import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class Counter extends JFrame implements ActionListener {
	JPanel jp;
	JButton[] btn;
	JTextArea jta;
	JScrollPane sp=null;
	String str[] = {"文件选择","字符统计","词统计","行统计","空行/注释行/代码行统计"};
	String file_path = null;
	
	//图形界面的组件定义
	public Counter(){
      btn = new JButton[str.length];
	  jp = new JPanel(new GridLayout(6,1,0,20));
	  jta = new JTextArea();
	  sp=new JScrollPane();
	  getContentPane().add(jp,BorderLayout.WEST);
	  getContentPane().add(jta,BorderLayout.EAST);
      for(int i=0;i<str.length;i++){
    	  btn[i] = new JButton(str[i]);
    	  jp.add(btn[i]);
    	  btn[i].addActionListener(this);
    	  btn[i].setActionCommand(str[i]);
      }
      sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	  sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	  sp.setViewportView(jta);
	  getContentPane().add(sp);
	  	  
	}
	
	//按钮事件监听
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	 if(e.getActionCommand().equals("文件选择")){
	    class MyChooser extends JFileChooser{
				MyChooser(String path){
				 super(path);
				}
				public void approveSelection(){
					File f=new File(getSelectedFile().getAbsolutePath());
					if(f.exists())
						super.approveSelection();
					else
						JOptionPane.showMessageDialog(null, "当前路径下不存在此文件，无法打开");
				 }
		 }
		 MyChooser myc=new MyChooser(null);
		 myc.setDialogTitle("选择将要打开的文件");
		 myc.setVisible(true);
		 int op=myc.showOpenDialog(null);
	     if(op==MyChooser.APPROVE_OPTION){
			file_path=myc.getSelectedFile().getAbsolutePath();
			FileReader fr=null;
			BufferedReader br=null;
			try{
			     fr=new FileReader(file_path);
			     br=new BufferedReader(fr);
			     String s1=new String();
			     String s2=new String();
			     while((s1=br.readLine())!=null){
				          s2+=s1+"\r\n";}
			
			     jta.setFont(new Font("宋体",0,20));
			     jta.setText(s2);
		     }catch(Exception e1){ e1.printStackTrace();}
			 finally{
			    try{
			        fr.close();
			        br.close();
			    }catch(Exception e1){}
	         }
	      }
       }else if(e.getActionCommand().equals("字符统计")){
    	   	  
			try {
				JOptionPane.showMessageDialog(this,"字符数:"+countchar(file_path));
			} catch (IOException e1) {
				e1.printStackTrace();}
		   	     	   
       }else if(e.getActionCommand().equals("词统计")){
    	   
    	   try {
			JOptionPane.showMessageDialog(this,"词数:"+countword(file_path));
	       } catch (IOException e1) {
			e1.printStackTrace();}
    	   
       }else if(e.getActionCommand().equals("行统计")){
    	   
    	   try {
			JOptionPane.showMessageDialog(this,"行数:"+countline(file_path));
		   } catch (IOException e1) {
			e1.printStackTrace();}
    	   
       }else if(e.getActionCommand().equals("空行/注释行/代码行统计")){
				int [] num  = new int[3];
				try {
					num = countcomplex_data(file_path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    	    JOptionPane.showMessageDialog(this, "空行数:"+num[0]
					+"\n"+"注释行数:"+num[1]+"\n"+"代码行数："+num[2]);   	   
       } 
	 
    }
	
	//统计字符数
	public static int countchar(String filepath) throws IOException{
		  File file = new File(filepath);
		  if(file.isFile()){
			  
			BufferedReader btin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int numOfchar = 0;
			String s;
			while((s=btin.readLine())!=null)  
			   numOfchar = numOfchar + s.length();
			btin.close();
			System.out.println("字符数:"+numOfchar);
			return numOfchar;
			
		   }else {
			   System.out.println("文件不存在，请重新输入！");
			   return -1;
			}			
			
		}
	//统计词数
	public static int countword(String filepath) throws IOException{
		  File file = new File(filepath);
		  if(file.isFile()) {
			  
			BufferedReader btin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int numOfword = 0;
			String s;
			String concat_str="";
			while ((s = btin.readLine()) != null) {
				concat_str += s + " ";
			}
			String REGEX="[\u4e00-\u9fa5]|[a-zA-Z_][\\w]{0,}";
			Pattern p = Pattern.compile(REGEX);
			Matcher m = p.matcher(concat_str);
			while(m.find())
			  numOfword++;
			btin.close();
			System.out.println("词数:"+numOfword);
			return numOfword;
			
		   }else {
			   System.out.println("文件不存在，请重新输入！");
			   return -1;
			}

		}
	//统计行数
	public static int countline(String filepath) throws IOException{
		  File file = new File(filepath);
		  if(file.isFile()){
				
			BufferedReader btin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int numOfline = 0;
			while (btin.readLine() != null)
				 numOfline++;
			btin.close();
			System.out.println("行数:"+numOfline);
			return numOfline;
		   }else {
			   System.out.println("文件不存在，请重新输入！");
			   return -1;
			}
		
		}
	//递归得到目录下所有文件的路径
	public static void multi_file(File f,List<String> paths){
			if(f!=null){
				 File[] files=f.listFiles();
				 if(files!=null)
					 for (int i = 0; i < files.length; i++){
					     if(files[i].isDirectory())
						   multi_file(files[i], paths);
					     else
					       paths.add(files[i].getPath());
					 }
			  }
		}
		
	//处理多文件
	public  static void dealwith_multifile(String command,String filepath) throws IOException{
		    String endwish = filepath.substring(filepath.lastIndexOf("."));
		    String directory = filepath.substring(0,filepath.lastIndexOf("\\"));
		    File f = new File(directory);
		    System.out.println(directory);
			List<String> paths = new ArrayList<String>();
			multi_file(f,paths);
			String s;
			for (int i=0;i<paths.size();i++){
				s = paths.get(i);
				if(s.endsWith(endwish)){
					System.out.println("文件路径为:"+s);
					if(command.equals("-c"))
						countchar(s);
					else if(command.equals("-w"))
						countword(s);
					else if(command.equals("-l"))
						countline(s);
					else{
						countcomplex_data(s);
					}					
				} 
			 }
	}
	// 返回更复杂的数据(代码行 / 空行 / 注释行)
	public static int[] countcomplex_data(String filepath) throws IOException{
		  File file = new File(filepath);
		  if(file.isFile()){
			  
			BufferedReader btin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));	
			int numOfblank = 0;
			int numOfcode = 0;
			int numOfnote = 0;
			int numOfline = 0;
			int [] num = new int[3];
			String s;
			boolean mark = false;
			
			String REGEX_for_blank="\\s*(.)?\\s*";
			String REGEX_for_note1 ="\\s*(\\{|\\})?//.*"; //单行注释
			String REGEX_for_note2 ="\\s*/\\*.*";  //多行注释开始
			String REGEX_for_note3 =".*\\*/";   //多行注释结束
			
			while((s=btin.readLine())!=null){
			  numOfline++;
			  if (s.matches(REGEX_for_note2)){
				  numOfnote++;
				  if (s.matches(REGEX_for_note3))
				     mark = false;
				  else mark = true;
			  }
			  else if (s.matches(REGEX_for_blank) && mark==false){
				  numOfblank++;
			  }
			  else if(s.matches(REGEX_for_note1) && mark==false){
				  numOfnote++;
			  }
			  else if(mark==true){
				  numOfnote++;
				  if (s.matches(REGEX_for_note3))
					  mark=false;
			  }
			}
			numOfcode = numOfline-numOfblank-numOfnote;
			System.out.println("空行数:"+numOfblank);
			System.out.println("注释行数:"+numOfnote);
			System.out.println("代码行数:"+numOfcode);
			btin.close();
			num[0] = numOfblank;
			num[1] = numOfnote;
			num[2] = numOfcode;
			return num;
		   }else {
			   System.out.println("文件不存在，请重新输入！");
			   return null;
		   }
	}
		
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    Counter wc = new Counter();
	    wc.setTitle("WC");
	    wc.setSize(800,500);
	    wc.setResizable(false);
	    wc.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2-460,
				  (Toolkit.getDefaultToolkit().getScreenSize().height)/2-290,800,500);
	    wc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    Scanner in = new Scanner(System.in);
        String[] s = null;
	    System.out.println("WC实现的功能如下"+ "([命令参数]:[功能]):\n");
	    System.out.println("-c:字符数\n-w:单词数\n-l:行数\n-s:处理多个文件\n-a:代码行/空行/注释行\n-x:图形界面\n");

	    while(true){
	      System.out.println("请按照程序处理用户需求的模式[parameter] [file_name]输入:");
          s= in.nextLine().split(" ");
          if(s.length>=2){
		      if (s[0].equals("-s")){
		    	  dealwith_multifile(s[1],s[2]);
		      }
		      else if(s[0].equals("-c")){
                  countchar(s[1]);	    	  
		      }
		      else if(s[0].equals("-w")){
		    	  countword(s[1]);
		      }
		      else if(s[0].equals("-l")){
		    	  countline(s[1]);
		      }	  
	    	  else if (s[0].equals("-a")){
	    		  countcomplex_data(s[1]);
	    	  }
	    	  else if(s[0].equals("-x")){
	    		  wc.setVisible(true);
	    	  }
	    	  else 
	    	  {  System.out.println("输入的需求模式有误，请重新输入！");
	    		 break;
	    	  }	
           }else {
             System.out.println("输入的需求模式有误，请重新输入！");
             break;}
	     }
	     in.close();	    
    }
	
}
