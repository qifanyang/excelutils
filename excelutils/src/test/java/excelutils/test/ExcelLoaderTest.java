package excelutils.test;

import java.util.ArrayList;

import org.junit.Test;

import com.tobe.excelutils.code.ExcelLoader;
import com.tobe.excelutils.code.SheetBean;

public class ExcelLoaderTest {
	
	@Test
	public void test0(){
		ExcelLoader loader = new ExcelLoader();
		ArrayList<SheetBean> list = loader.load("/配置数据.xlsx", true);
		
		System.out.println("");
	}

}
