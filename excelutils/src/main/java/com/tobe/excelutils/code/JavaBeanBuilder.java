package com.tobe.excelutils.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据字段类表构建一个javaBean,复杂的考虑使用FreeMarker 这里的支持不同类型的字段
 * 
 * @author TOBE
 * 
 */
public class JavaBeanBuilder implements ICodeBuilder {

	protected String packageName;

	protected String className;

	protected List<Field> fields = new ArrayList<Field>();

	protected static Map<String, String> importsmap = new HashMap<String, String>();

	static {
		importsmap.put("list", "import java.util.List;");
		importsmap.put("map", "import java.util.Map;");
		importsmap.put("set", "import java.util.Set;");
	}

	public JavaBeanBuilder(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	/**
	 * 生成类的包名,默认为空,没有包
	 * 
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String code() {
		return buildCode(fields);
	}

	/**
	 * list为字段列表
	 * 
	 * @param fields
	 * @return
	 */
	public String buildCode(List<Field> fields) {
		StringBuilder sb = new StringBuilder();

		if (null != packageName && packageName.length() > 0) {
			sb.append("package ").append(packageName).append(";\n\n");
		}

		// 导入包
		Set<String> impsset = new HashSet<String>();
		for (Field f : fields) {
			String jcn = f.getJavaClassName().toLowerCase();
			if (jcn.startsWith("list<")) {
				impsset.add("list");
			} else if (jcn.startsWith("map<")) {
				impsset.add("map");
			} else if (jcn.startsWith("set<")) {
				impsset.add("set");
			}
		}
		for (String s : impsset) {
			sb.append(importsmap.get(s)).append("\n");
		}
		sb.append("\n");

		// 注释

		// 类名
		sb.append("public class ").append(toUpper(className, 0)).append(" { \n\n");

		// 字段
		for (int i = 0, size = fields.size(); i < size; i++) {
			Field f = fields.get(i);
			if (!isEmpty(f.getExplain())) {
				sb.append("\t/** ").append(f.getExplain()).append(" */\n");
			}
			sb.append("\tprivate ").append(f.getJavaClassName()).append(" ").append(f.getName()).append(";\n\n");
		}

		// get set 方法
		for (int i = 0, size = fields.size(); i < size; i++) {
			Field f = fields.get(i);

			sb.append("\tpublic void ").append("set").append(toUpper(f.getName(), 0)).append("(").append(f.getJavaClassName()).append(" ")
					.append(f.getName()).append(") {\n")// 方法名
					.append("\t\tthis.").append(f.getName()).append(" = ").append(f.getName()).append(";")// 方法体
					.append("\n\t}\n\n");

			sb.append("\tpublic ").append(f.getJavaClassName()).append(" get").append(toUpper(f.getName(), 0)).append("(").append(") {\n")// 方法名
					.append("\t\treturn ").append(f.getName()).append(";")// 方法体
					.append("\n\t}\n\n");
		}

		// 结束
		sb.append("}");

		return sb.toString();

	}

	public boolean isEmpty(String s) {
		if (null == s || s.length() == 0) {
			return true;
		}
		return false;
	}

	public static String toUpper(String s, int index) {
		if (null == s || s.length() == 0) {
			throw new RuntimeException("s can not null or length must not 0");
		}
		if (index == 0) {
			// 首字母大写
			String upperCase = s.substring(0, 1).toUpperCase();
			String suffix = s.substring(1);
			return upperCase + suffix;
		} else {
			String pre = s.substring(0, index);
			String upperCase = s.substring(index, index + 1).toUpperCase();
			String suffix = s.substring(index + 1);
			return pre + upperCase + suffix;
		}

	}

}
