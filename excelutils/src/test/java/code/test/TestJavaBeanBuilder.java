package code.test;

import org.junit.Test;

import com.tobe.excelutils.code.Field;
import com.tobe.excelutils.code.JavaBeanBuilder;

public class TestJavaBeanBuilder {
	
	@Test
	public void test0(){
		JavaBeanBuilder bb = new JavaBeanBuilder("com.tobe.excelutils.code", "Sheet1");
		Field f1 = new Field();
		f1.setJavaClassName("String");
		f1.setName("name");
		f1.setExplain("名字");
		bb.getFields().add(f1);
		
		Field f2 = new Field();
		f2.setJavaClassName("int");
		f2.setName("age");
		bb.getFields().add(f2);
		
		Field f3 = new Field();
		f3.setJavaClassName("List<String>");
		f3.setName("list");
		bb.getFields().add(f3);
		
		Field f4 = new Field();
		f4.setJavaClassName("Map<String, String>");
		f4.setName("map");
		bb.getFields().add(f4);
		
		
		System.out.println(bb.code());
	}

}
