package excelutils.test;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.handler.BeanHandler;
import com.tobe.excelutils.handler.BeanListHandler;
import com.tobe.excelutils.test.ActivityVO;

public class TestExcelUtils {
	
//	@Test
	public void testIgnore() throws Exception {
		
		SelectSQL sql = new SelectSQL();
		sql.ignore("name").ignore("start");
//		sql.select("singledesc");
//		sql.where("name", "单笔充值");
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		//忽略name字段,所以值应为null
		Assert.assertNull(vo.getName());
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		for(ActivityVO v : list){
			Assert.assertNull(v.getName());
		}
		
		System.out.println("");
	}
	
//	@Test
	public void testSelectField() throws Exception{
		SelectSQL sql = new SelectSQL();
		sql.select("singledesc");
//		sql.where("name", "单笔充值");
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		ActivityVO vo1 = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		
		Assert.assertNull(vo1.getEnd());//只查询singledesc字段,其它字段应为空或者为0
		Assert.assertEquals(0, vo1.getAutoCommit());
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		//查询所有字段
		ActivityVO vo2 = runner.query(new SelectSQL(), new BeanHandler<ActivityVO>(ActivityVO.class));
		
		System.out.println("");
	}
	
	@Test
	public void testWhere() throws Exception{
		SelectSQL sql = new SelectSQL();
//		sql.where("name", "单笔充值").where("targetnum", "[1000]");//查询条件,name字段值必须是单笔充值,不是的不放入查询结果中 , 多个条件
		
		sql.where("groupdesc", "单笔充值");//支持表达式
		
//		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		ExcelRunner runner = new ExcelRunner("E:/sourcetreerepo/excelutils/excelutils/src/main/resource/商业活动.xlsx");
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		Assert.assertEquals(1, vo.getGroupId());
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		for(ActivityVO v : list){
			Assert.assertEquals("单笔充值", v.getName());
		}
		System.out.println("");
	}

}
