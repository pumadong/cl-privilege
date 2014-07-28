package com.cl.privilege.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.privilege.biz.IResourceService;
import com.cl.privilege.mapper.ResourceMapper;
import com.cl.privilege.model.Resource;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.CommonUtil;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
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
					if(CommonUtil.isNumber(structures[structures.length-1])&&CommonUtil.compareTo(structures[structures.length-1], structure)>0)
					{
						structure = structures[structures.length-1];
					}
				}				
			}
			structure = String.valueOf(Integer.parseInt(structure) + 1);
			structure = parentResource.getStructure()+"-" + structure;
		}
		resource.setCreatePerson(user.getUsername());
		resource.setUpdatePerson(user.getUsername());
		resource.setStructure(structure);
		return resourceMapper.insertSelective(resource);

	}
	
	@Override
	public Integer updateResourceById(Resource resource,User user)
	{
		resource.setUpdatePerson(user.getUsername());
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
}
