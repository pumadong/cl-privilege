package com.cl.privilege.biz.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.privilege.biz.IResourceService;
import com.cl.privilege.mapper.ModuleMapper;
import com.cl.privilege.mapper.ResourceMapper;
import com.cl.privilege.model.Module;
import com.cl.privilege.model.Resource;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.StringUtil;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	
	private void setResourceInsert(Resource resource,User operator)
	{
		Date d = new Date();
		resource.setCreatePerson(operator.getUsername());
		resource.setUpdatePerson(operator.getUsername());
		resource.setCreateDate(d);
		resource.setUpdateDate(d);
	}
	private void setResourceUpdate(Resource resource,User operator)
	{
		Date d = new Date();
		resource.setUpdatePerson(operator.getUsername());
		resource.setUpdateDate(d);
	}
	
	@Override
	public List<Resource> getResourceList()
	{
		return resourceMapper.getResourceList();		
	}
	
	@Override
	public List<Resource> getResourceListByModuleFlag(List<String> moduleFlags,Integer userId) {
		return resourceMapper.getResourceListByModuleFlag(moduleFlags,userId);
	}

	@Override
	public Resource getResourceById(Integer id)
	{
		return resourceMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Integer createResource(Resource resource,User user)
	{		
		//生成structure
		String structure = "1";
		Resource parentResource = resourceMapper.selectByPrimaryKey(resource.getParentId());
		List<Resource> resources = resourceMapper.getResourceListByParentId(resource.getParentId());
		if(resources==null || resources.size()==0)
		{
			structure = parentResource.getStructure()+"-1";
		} else {
			Integer parentLevel = parentResource.getStructure().split("-").length;
			
			for(Resource r:resources)
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
			structure = parentResource.getStructure()+"-" + structure;
		}
		
		resource.setStructure(structure);
		
		setResourceInsert(resource,user);
		
		return resourceMapper.insertSelective(resource);

	}
	
	@Override
	public Integer updateResourceById(Resource resource,User user)
	{
		setResourceUpdate(resource,user);
		return resourceMapper.updateByPrimaryKeySelective(resource);
	}
	
	@Override
	public Integer deleteResourceById(Integer id)
	{
		return resourceMapper.deleteByPrimaryKey(id);
	}
	

	@Override
	public Boolean isUsedByRole(Integer resourceId) {
		return resourceMapper.isUsedByRole(resourceId);	
	}
	
	@Override
	public List<Resource> getResourceListByRoleId(Integer roleId)
	{
		return resourceMapper.getResourceListByRoleId(roleId);
	}
	
	@Override
	public String getResourceTree(Integer roleId)
	{
		Set<Integer> setResource = new HashSet<Integer>();
		
		if(roleId != null)
		{
			List<Resource> tempResourceList = resourceMapper.getResourceListByRoleId(roleId);
			if(tempResourceList!=null && tempResourceList.size()>0)
			{
				for(Resource r:tempResourceList)
				{
					setResource.add(r.getId());
				}
			}
		}
		
		List<Module> moduleList = moduleMapper.getModuleList();
		List<Resource> resourceList = resourceMapper.getResourceList();
		Collections.sort(moduleList,new ComparatorModule());
		Collections.sort(resourceList,new ComparatorResource());
		
		Set<Integer> setParent = new HashSet<Integer>();
		for(Resource r:resourceList)
		{
			setParent.add(r.getParentId());
		}
		
		StringBuilder sb = new StringBuilder();
		Map<String,Integer> mapModule = new HashMap<String,Integer>();
		sb.append("[");
		int i = 0;		
		for(Module m:moduleList)
		{
			mapModule.put(m.getFlag(), m.getId());
			if(i!=0)
			{
				sb.append(",");
			}
			i++;
			sb.append("{")
				.append("\"id\":\"").append("m_").append(m.getId()).append("\"")
				.append(",\"parent\":\"").append("#\"")
				.append(",\"text\":\"").append(m.getName()).append("\"")
				.append(",\"li_attr\":{\"flag\":\"").append(m.getFlag()).append("\"}");
			//前两个级别默认打开
			sb.append(",\"state\":{");
			sb.append("\"opened\":true");
			sb.append("}");
			sb.append("}");
		}
		i = 0;
		for(Resource r:resourceList)
		{
			int level = r.getStructure().split("-").length;
			
			sb.append(",");
			
			i++;
			sb.append("{")
			.append("\"id\":\"").append(r.getId()).append("\"")
			.append(",\"parent\":\"").append(r.getParentId()==0?("m_"+mapModule.get(r.getModuleFlag())):r.getParentId()).append("\"")
			.append(",\"text\":\"").append(r.getName()).append("\"")
			.append(",\"li_attr\":{\"flag\":\"").append(r.getModuleFlag())
				.append("\",\"sortNo\":").append(r.getSortNo()).append("}");
			//前两个级别默认打开
			if(level <=2)
			{
				sb.append(",\"state\":{\"opened\":true");
				if(setResource.contains(r.getId()))
				{
					sb.append(",\"selected\":true");
				}
				sb.append("}");
			} else {
				
				if(setResource.contains(r.getId()))
				{
					sb.append(",\"state\":{\"opened\":true}");
				}
			}
			//最后一个级别换个绿色图标
			if(!setParent.contains(r.getId()))
			{
				sb.append(", \"icon\": \"fa fa-briefcase icon-success\"");
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Module排序器，保证jsTree可以按照SortNo字段显示
	 */
	class ComparatorModule implements Comparator<Module> {
		public int compare(Module r1, Module r2) {
			return r1.getSortNo().compareTo(r2.getSortNo());
		}
	}
	
	/**
	 * Resource排序器，保证jsTree可以按照SortNo字段显示
	 */
	class ComparatorResource implements Comparator<Resource> {
		public int compare(Resource r1, Resource r2) {
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
