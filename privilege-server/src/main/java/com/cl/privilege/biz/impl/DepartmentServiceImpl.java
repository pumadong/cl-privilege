package com.cl.privilege.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.privilege.biz.IDepartmentService;
import com.cl.privilege.mapper.DepartmentMapper;
import com.cl.privilege.model.Department;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.StringUtil;
import com.cl.privilege.utils.ConstantUtil;


@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;
	
	private void setDepartmentInsert(Department department,User operator)
	{
		Date d = new Date();
		department.setCreatePerson(operator.getUsername());
		department.setUpdatePerson(operator.getUsername());
		department.setCreateDate(d);
		department.setUpdateDate(d);
	}
	private void setDepartmentUpdate(Department department,User operator)
	{
		Date d = new Date();
		department.setUpdatePerson(operator.getUsername());
		department.setUpdateDate(d);
	}
	
	
	@Override
	public List<Department> getDepartmentList()
	{
		return departmentMapper.getDepartmentList();
	}
	
	@Override
	public Department getDepartmentById(Integer id) {
		return departmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer createDepartment(Department department,User user) 
	{		
		//生成structure
		String structure = "1";
		Department parentDepartment = departmentMapper.selectByPrimaryKey(department.getParentId());
		List<Department> departments = departmentMapper.getDepartmentListByParentId(department.getParentId());
		if(departments==null || departments.size()==0)
		{
			structure = parentDepartment.getStructure()+"-1";
		} else {
			Integer parentLevel = parentDepartment.getStructure().split("-").length;
			
			for(Department r:departments)
			{
				String[] structures = r.getStructure().split("-");
				if(structures.length == parentLevel + 1)
				{
					if(StringUtil.isNumber(structures[structures.length-1])&&StringUtil.compareTo(structures[structures.length-1], structure)>0)
					{
						structure = structures[structures.length-1];
					}
				}				
			}
			structure = String.valueOf(Integer.parseInt(structure) + 1);
			structure = parentDepartment.getStructure()+"-" + structure;
		}
		department.setStructure(structure);
		
		setDepartmentInsert(department,user);
		return departmentMapper.insertSelective(department);
	}

	@Override
	public Integer updateDepartmentById(Department department,User user) {
		setDepartmentUpdate(department,user);
		return departmentMapper.updateByPrimaryKeySelective(department);
	}

	@Override
	public Integer deleteDepartmentById(Integer id)
	{
		return departmentMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Boolean isUsedByUser(Integer departmentId)
	{
		return departmentMapper.isUsedByUser(departmentId);
	}
	
	@Override
	public String getDepartmentTree()
	{
		List<Department> departmentList = departmentMapper.getDepartmentList();
		if(departmentList==null || departmentList.size()==0) return ConstantUtil.EmptyJsonObject;
		
		Collections.sort(departmentList,new ComparatorDepartment());
		
		Set<Integer> setParent = new HashSet<Integer>();
		for(Department d:departmentList)
		{
			setParent.add(d.getParentId());
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for(Department d:departmentList)
		{
			int level = d.getStructure().split("-").length;
			if(i!=0)
			{
				sb.append(",");
			}
			i++;
			sb.append("{")
				.append("\"id\":\"").append(d.getId()).append("\"")
				.append(",\"parent\":\"").append(d.getParentId()==0?"#":d.getParentId()).append("\"")
				.append(",\"text\":\"").append(d.getName()).append("\"")
				.append(",\"li_attr\":{\"sortNo\":").append(d.getSortNo()).append("}");;
			//前两个级别默认打开
			if(level <=3)
			{
				sb.append(",\"state\":{\"opened\":true}");
			}
			//最后一个级别换个绿色图标
			if(!setParent.contains(d.getId()))
			{
				sb.append(", \"icon\": \"fa fa-briefcase icon-success\"");
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public List<Department> getDepartmentListForOption()
	{
		List<Department> departments = getDepartmentList();
		if(departments==null || departments.size()==0)
		{
			return null;
		}
			
		List<Department> tempImmediateDepartments = new ArrayList<Department>();
		for(Department d:departments)
		{
			if(d.getParentId()==0)
			{
				//一级子节点
				tempImmediateDepartments.add(d);
			}
		}
		
		return buildDepartmentListForOption(departments,tempImmediateDepartments,"s");
	}
	
	/**
	 * 这种写法和直接对所有部门列表进行循环的写法比较
	 * 优点是：更少的循环次数，所以也就是更少的CPU计算和更快的返回时间；缺点是：更多的内存占用
	 * 也可以看做是一种空间换时间
	 * @param descendantDepartments
	 * @param immediateDepartments
	 * @param structure
	 * @return
	 */
	private List<Department> buildDepartmentListForOption(List<Department> descendantDepartments,List<Department> immediateDepartments,String structure)
	{
		if(descendantDepartments == null || descendantDepartments.size()==0
				||immediateDepartments == null || immediateDepartments.size()==0)
		{
			return null;
		}
		
		Collections.sort(immediateDepartments,new ComparatorDepartment());
		
		List<Department> result = new ArrayList<Department>();

		Integer index = 0;
		Integer level = structure.split("-").length;
		String prefix = "";
		for(int i=0;i<level-1;i++)
		{
			prefix += "&nbsp;&nbsp;&nbsp;";
		}
		for(Department department:immediateDepartments)
		{
			if(department.getStructure().split("-").length != level+1 
					|| !department.getStructure().startsWith(structure+"-")
					)
			{
				continue;
			}
			department.setName(prefix + department.getName());
			result.add(department);

			List<Department> tempDescendantDepartment = new ArrayList<Department>();
			List<Department> tempImmediateDepartment = new ArrayList<Department>();
			for(Department r:descendantDepartments)
			{
				if(r.getStructure().startsWith(department.getStructure()+"-"))
				{
					if(r.getStructure().split("-").length == level + 2 )
					{
						//第一级子节点
						tempImmediateDepartment.add(r);
					}
					//所有子节点
					tempDescendantDepartment.add(r);
				}
			}
			if(tempDescendantDepartment!=null && tempDescendantDepartment.size()>0
					&& tempImmediateDepartment!=null && tempImmediateDepartment.size()>0)
			{
				List<Department> sub = buildDepartmentListForOption(tempDescendantDepartment,tempImmediateDepartment, department.getStructure());
				
				if(sub!=null && sub.size()>0)
				{
					result.addAll(sub);
				}
			}			
			index++;
		}
		return result;
	}
	
	/**
	 * Department排序器，保证jsTree可以按照SortNo字段显示
	 */
	class ComparatorDepartment implements Comparator<Department> {
		public int compare(Department r1, Department r2) {
			int l1 = r1.getStructure().length();
			int l2 = r2.getStructure().length();
			if(l1 == l2 )
			{
				return r1.getSortNo().compareTo(r2.getSortNo());
			}
			return l1>l2?1:-1;
		}
	}
}
