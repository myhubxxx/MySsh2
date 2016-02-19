package Test;

import org.junit.Test;

public class TestString {
	@Test
	 public void test1() {  
	        String str = "abc";  
	        String str1 = "abc";  
	        String str2 = new String("abc");  
	        String str3 = new String("temp");
//	        System.out.println(str3.intern() == "temp");
	        System.out.println(str == str1);  // true
	        System.out.println(str1 == "abc");  // true
	        System.out.println(str2 == "abc");  // false
	        System.out.println(str1 == str2);  // false
	        System.out.println(str1.equals(str2));  // true
	        System.out.println(str1 == str2.intern());  // true
	        System.out.println(str2 == str2.intern());  // true
	        System.out.println(str1.hashCode() == str2.hashCode());// true  
	    }  
	@Test
	public void test2(){
		/*首先查找StringPool中是否存在内容为abc的对象
		若已存在,则不会在StringPool中创建abc对象
		此时发现没有内容为abc对象,接着会把该对象放到StringPool中
		接下来执行new String("abc")构造生成一个对象,该对象存于Java堆内存中
		这就造成了StringPool中有一个abc对象,堆内存中也有一个abc对象*/
		String s = new String("abc");
		//此时StringPool中已存在abc对象,它不会在StringPool中再生成新字符串对象
		String s1 = "abc";
		/*执行下行代码时,则在堆内存中又会生成一个新的abc对象,并将其引用地址赋给s2*/
		String s2 = new String("abc");
		/*对于8个原生数据类型,==比较其值是否一样
		对于引用类型,==判断其引用地址是否一样,即判断两个引用是否指向同样一对象*/
		System.out.println("s==s1:"+(s==s1)); // false
		System.out.println("s==s2:"+(s==s2));//false
		System.out.println("s1==s2:"+(s1==s2));//false
		System.out.println("s.equals(s1):"+s.equals(s1));// 
		System.out.println("s.equals(s2):"+s.equals(s2));
		System.out.println("s1.equals(s2):"+s1.equals(s2));
		
		System.out.println("s==s.intern():"+(s==s.intern()));// false
		System.out.println("s1==s1.intern():"+(s1==s1.intern()));// true
		System.out.println("s==s2.intern():"+(s.intern()==s2.intern()));// true
	}
	@Test
	public void test3(){
		/*若加号两边的都是常量值,则将这两个值拼起来得到一个对象,然后检查StringPool中有没有该对象存在
		若StringPool中没有,则添加该对象到StringPool中
		若StringPool中存在,则不生成新的对象而是直接返回StringPool中的该对象
		若加号两边至少有一个是变量,则将这两个值拼起来后不检查StringPool而是直接在堆内存中生成新对象*/
		String hello = "hello";
		String hel = "hel";
		String lo = "lo";
		System.out.println("hello=='hel'+'lo':"+(hello=="hel"+"lo"));
		System.out.println("hello=='hel'+lo:"+(hello=="hel"+lo));
		System.out.println("hello==hel+lo:"+(hello==hel+lo));
	}
	
}
