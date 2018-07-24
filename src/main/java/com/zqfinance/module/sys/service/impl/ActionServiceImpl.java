
/**
 * action service impl
 * @author 许进
 */
package com.zqfinance.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqfinance.module.sys.dao.ActionMapper;
import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.service.ActionService;
@Service(value="actionService")
public class ActionServiceImpl implements ActionService {
	@Autowired
	private ActionMapper actionMapper;
	
	public List<Action> getActionList(Action action) {
		return actionMapper.getActionList(action);
		
	}

	@Override
	public void addAction(Action action) {
		actionMapper.addAction(action);	
	}

	@Override
	public void updateAction(Action action) {
		actionMapper.updateAction(action);
		
	}

	@Override
	public void delAction(long id) {
		actionMapper.delAction(id);
		//删除功能和角色对应关系
		actionMapper.delRoleActionMapByActionId(id);
	}

	@Override
	public Action getActionById(long id) {
		return actionMapper.getActionById(id);       
		
	}

	@Override
	public int getActionCount() {
		return actionMapper.getActionCount();
		
	}

	@Override
	public List<Action> getActionListByModule(long moduleId) {
		return actionMapper.getActionListByModule(moduleId);
		
	}

	@Override
	public List<Action> getAllActionList() {
		return actionMapper.getAllActionList();		
	}

	@Override
	public List<Role> getRoleListByActionId(long actionId) {
		return actionMapper.getRoleListByActionId(actionId);
		
	}

	@Override
	public Action getActionByNameOrId(Action action) {
		// TODO Auto-generated method stub
		return actionMapper.getActionByNameOrId(action);
	}

}

