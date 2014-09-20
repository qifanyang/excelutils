excelutils
==========


一个类似于apache dbutils用于查询excel的库,没有实现全部的handler

用法:

    //需自己构建selectSQL,可以忽略字段,添加条件等
    SelectSQL sql = new SelectSQL();
    sql.where("name", "单笔充值")

    ExcelRunner runner = new ExcelRunner("/数据.xlsx");
		
		//vo是excel列字段构成的一个javabean,这里返回一行记录
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		
		DataHelper.print(runner.getRs().getHeaders(), vo);
		
		//每次查询后都需要新new一个runner
		runner = new ExcelRunner("/数据.xlsx");
		
		//这里返回所有记录
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		
		//查询多sheet
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		SelectSQL sql = new SelectSQL();
		
		//每个select对应一个sheet,顺序必须与sheet一致
		List<SelectSQL> selectList = new ArrayList<SelectSQL>();
		selectList.add(sql);
		
		//每个Class对应一个sheet,
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		typeList.add(ActivityVO.class);
		typeList.add(ActivityVOO.class);
		
		//
		List<?> multiQueryToList = runner.multiQueryToList(selectList, new MultiBeanListHandler(typeList));
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		Map<String, Object> multiQueryToMap = runner.multiQueryToMap(selectList, new MultiBeanMapHandler(typeList));
