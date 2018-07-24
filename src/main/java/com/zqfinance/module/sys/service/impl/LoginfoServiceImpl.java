package com.zqfinance.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqfinance.module.sys.dao.LoginfoMapper;
import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.module.sys.service.LoginfoService;

@Service(value="loginfoService")
public class LoginfoServiceImpl implements LoginfoService{

	@Autowired
	private LoginfoMapper LoginfoMapper;
	
	@Override
	public List<Loginfo> getLoginfoList(Loginfo loginfo) {
		return LoginfoMapper.getLoginfoList(loginfo);
	}
	
	@Override
	public int getLoginfoCount(Loginfo loginfo) {
		return LoginfoMapper.getLoginfoCount(loginfo);
	}

	@Override
	public void addLoginfo(Loginfo loginfo) {
		LoginfoMapper.addLoginfo(loginfo);
	}

	@Override
	public Loginfo getLoginfoByNameOrId(Loginfo loginfo) {
		return LoginfoMapper.getLoginfoByNameOrId(loginfo);
	}


}
