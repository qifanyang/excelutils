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
		SelectSQL sql = new SelectSQL();
//		sql.where("name", "单笔充值").where("targetnum", "[1000]");//查询条件,name字段值必须是单笔充值,不是的不放入查询结果中 , 多个条件
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		typeList.add(ActivityVO.class);
		typeList.add(ActivityVOO.class);
		List multiQuery = runner.multiQuery(sql, new MultiBeanListHandler(typeList));
		
		
		System.out.println("");
	}

}
