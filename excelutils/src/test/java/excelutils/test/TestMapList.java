package excelutils.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.handler.MapListHandler;

public class TestMapList {

	@Test
	public void test0() throws Exception{
		SelectSQL sql = new SelectSQL();
//		sql.where("name", "单笔充值").where("targetnum", "[1000]");//查询条件,name字段值必须是单笔充值,不是的不放入查询结果中 , 多个条件
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		List<Map<String, Object>> list = runner.query(sql, new MapListHandler());
		
		for(Map<String, Object> map : list){
			Assert.assertNotNull(map.get("groupId"));
		}
		
		System.out.println("");
	}
}
