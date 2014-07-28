package com.cl.privilege.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cl.privilege.biz.IUserService;
import com.cl.privilege.mapper.ModuleMapper;
import com.cl.privilege.mapper.UserMapper;
import com.cl.privilege.model.User;
import com.cl.privilege.model.UserSearchModel;
import com.cl.privilege.utils.ConstantUtil;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ModuleMapper moduleMapper;
	
	private void setPersonInsert(User user,User operator)
	{
		Date d = new Date();
		user.setCreatePerson(operator.getUsername());
		user.setUpdatePerson(operator.getUsername());
		user.setCreateDate(d);
		user.setUpdateDate(d);
	}
	private void setPersonUpdate(User user,User operator)
	{
		Date d = new Date();
		user.setUpdatePerson(operator.getUsername());
		user.setUpdateDate(d);
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
		
	}
	
	@Override
	public User getUserById(Integer id)
	{
		return userMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Integer getUserTotalBySearch(UserSearchModel searchModel)
	{
		return userMapper.getUserTotalBySearch(searchModel);
	}

	@Override
	public List<User> getUserListBySearch(UserSearchModel searchModel)
	{
		return userMapper.getUserListBySearch(searchModel, 
				new RowBounds((searchModel.getPageNo() - 1) * searchModel.getPageSize(), searchModel.getPageSize()));
	}
	
	@Override
	public Integer createUser(User user,User operator)
	{
		setPersonInsert(user,operator);
		return userMapper.insertSelective(user);
	}
	
	@Override
	public Integer updateUserById(User user,User operator)
	{
		setPersonUpdate(user,operator);
		return userMapper.updateByPrimaryKey(user);
	}
	
	@Override
	@Transactional
	public Integer deleteUserById(Integer id,User operator)
	{
		User user = getUserById(id);
		if(user==null)
		{
			return 0;
		}
				
		//本设计中，设置用户是不能物理删除的，保证了所有记录用户操作日志的地方，关联用户可以用inner join，提高效率
		userMapper.deleteUserRoleById(id);
		user.setIsDelete(true);
		setPersonUpdate(user,operator);
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	@Transactional
	public String assignRole(Integer id,String selectedStr)
	{
		String[] selectedArr = selectedStr.split(",");
		List<Integer> roleIds = new ArrayList<Integer>();
		
		for(String s:selectedArr)
		{
			if(StringUtils.hasText(s))
			{
				roleIds.add(Integer.parseInt(s));
			}
		}
		
		userMapper.deleteUserRoleById(id);
		if(roleIds.size()>0)
		{
			userMapper.assignRoles(roleIds, id);
		}
		
		return ConstantUtil.Success;
	}

	public static void main(String[] args)
	{
		CharSequence s = "d青ddd";
		System.out.print(s.subSequence(0,2));
	}
}
