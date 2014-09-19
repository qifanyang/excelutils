package excelutils.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.bean.ActivityVO;
import com.tobe.excelutils.bean.ActivityVOO;
import com.tobe.excelutils.handler.MultiBeanListHandler;

public class TestMultiSelect {
	
	@Test
	public void test() throws Exception{
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		SelectSQL sql = new SelectSQL();
//		sql.where("name", "单笔充值").where("targetnum", "[1000]");//查询条件,name字段值必须是单笔充值,不是的不放入查询结果中 , 多个条件
		
		//每个select对应一个sheet,顺序必须与sheet一致
		List<SelectSQL> selectList = new ArrayList<SelectSQL>();
		selectList.add(sql);
		
		//每个Class对应一个sheet,
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		typeList.add(ActivityVO.class);
		typeList.add(ActivityVOO.class);
		
		//这里泛型推断不会弄,就这样子,
		List multiQuery = runner.multiQuery(selectList, new MultiBeanListHandler(typeList));
		
		System.out.println("");
	}

}
