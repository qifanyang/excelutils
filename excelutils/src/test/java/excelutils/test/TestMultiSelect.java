package excelutils.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.bean.ActivityVO;
import com.tobe.excelutils.bean.ActivityVOO;
import com.tobe.excelutils.handler.MultiBeanListHandler;
import com.tobe.excelutils.handler.MultiBeanMapHandler;

public class TestMultiSelect {
	
	@Test
	public void test() throws Exception{
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		SelectSQL sql = new SelectSQL();
		
		//每个select对应一个sheet,顺序必须与sheet一致
		List<SelectSQL> selectList = new ArrayList<SelectSQL>();
		selectList.add(sql);
		
		//每个Class对应一个sheet,
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		typeList.add(ActivityVO.class);
		typeList.add(ActivityVOO.class);
		
		//这里泛型推断不会弄,就这样子,
		List<?> multiQuery = runner.multiQueryToList(selectList, new MultiBeanListHandler(typeList));
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		Map<String, Object> multiQueryToMap = runner.multiQueryToMap(selectList, new MultiBeanMapHandler(typeList));
		System.out.println("");
	}

}
