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
import com.cl.privilege.utils.StringUtil;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ModuleMapper moduleMapper;
	
	private void setPersonInsert(User user,User operator)
	{
		Date d = new Date();
		user.setIsLock(false);
		user.setIsDelete(false);
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
		user.setPassword(StringUtil.makeMD5(user.getPassword()));
		return userMapper.insertSelective(user);
	}
	
	@Override
	public Integer updateUserById(User user,User operator)
	{
		setPersonUpdate(user,operator);
		return userMapper.updateByPrimaryKeySelective(user);
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
	
	@Override
	public String getUserDataTables(UserSearchModel searchModel)
	{
		Integer total = getUserTotalBySearch(searchModel);
		List<User> userList = getUserListBySearch(searchModel);		
		if(userList==null || userList.size()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[",total,total));
		int i= 0;
		for(User u:userList)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,u);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	
	@Override
	public String getUserDataRow(Integer id)
	{
		User u = getUserById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb,u);
		return sb.toString();
	}

	private void addDataRow(StringBuilder sb,User u)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\" name=\\\"id[]\\\" value=\\\"").append(u.getId()).append("\\\">\"");
		sb.append(",").append(u.getId());
		sb.append(",\"").append(u.getUsername()).append("\"");
		sb.append(",\"").append(u.getFullname()).append("\"");
		sb.append(",\"").append(u.getGender()?"男":"女").append("\"");
		sb.append(",\"").append(u.getIsAdmin()?"管理员":"普通").append("\"");
		sb.append(",\"").append(u.getIsLock()?"是":"否").append("\"");
		sb.append(",\"").append(u.getDepartmentName()==null?"":u.getDepartmentName()).append("\"");
		sb.append(",\"").append(u.getUpdatePerson()).append("\"");
		sb.append(",\"").append(StringUtil.formatDate(u.getUpdateDate(),"yyyy-MM-dd HH:mm:ss")).append("\"");
		sb.append(",\"")
		.append("<a href=\\\"javascript:User.update_click('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:").append(u.getIsLock()?"User.unlock('":"User.lock('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-").append(u.getIsLock()?"un":"").append("lock\\\"></i> ").append(u.getIsLock()?"解锁":"锁定").append("</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:User.remove('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:User.assign_click('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-key\\\"></i> 分配角色</a>")
		.append("\"");
		sb.append("]");
	}
}
