

package com.zqfinance.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqfinance.module.sys.dao.IdMapper;
import com.zqfinance.module.sys.service.IdService;
@Service
public class IdServiceImpl implements IdService {
	@Autowired
	private IdMapper idMapper;
	
	public long getUserId() {
		return idMapper.getUserId();		
	}

	public long getModuleId() {
		return idMapper.getModuleId();
		
	}

	@Override
	public long getActionId() {
		return idMapper.getActionId();
		
	}

	@Override
	public long getRoleId() {
		return idMapper.getRoleId();
		
	}

	@Override
	public long getRoleActionMapId() {
		return idMapper.getRoleActionMapId();
		
	}

	@Override
	public long getUserRoleMapId() {
		return idMapper.getUserRoleMapId();
		
	}

}

