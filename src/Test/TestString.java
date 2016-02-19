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
		/*���Ȳ���StringPool���Ƿ��������Ϊabc�Ķ���
		���Ѵ���,�򲻻���StringPool�д���abc����
		��ʱ����û������Ϊabc����,���Ż�Ѹö���ŵ�StringPool��
		������ִ��new String("abc")��������һ������,�ö������Java���ڴ���
		��������StringPool����һ��abc����,���ڴ���Ҳ��һ��abc����*/
		String s = new String("abc");
		//��ʱStringPool���Ѵ���abc����,��������StringPool�����������ַ�������
		String s1 = "abc";
		/*ִ�����д���ʱ,���ڶ��ڴ����ֻ�����һ���µ�abc����,���������õ�ַ����s2*/
		String s2 = new String("abc");
		/*����8��ԭ����������,==�Ƚ���ֵ�Ƿ�һ��
		������������,==�ж������õ�ַ�Ƿ�һ��,���ж����������Ƿ�ָ��ͬ��һ����*/
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
		/*���Ӻ����ߵĶ��ǳ���ֵ,��������ֵƴ�����õ�һ������,Ȼ����StringPool����û�иö������
		��StringPool��û��,����Ӹö���StringPool��
		��StringPool�д���,�������µĶ������ֱ�ӷ���StringPool�еĸö���
		���Ӻ�����������һ���Ǳ���,��������ֵƴ�����󲻼��StringPool����ֱ���ڶ��ڴ��������¶���*/
		String hello = "hello";
		String hel = "hel";
		String lo = "lo";
		System.out.println("hello=='hel'+'lo':"+(hello=="hel"+"lo"));
		System.out.println("hello=='hel'+lo:"+(hello=="hel"+lo));
		System.out.println("hello==hel+lo:"+(hello==hel+lo));
	}
	
}
