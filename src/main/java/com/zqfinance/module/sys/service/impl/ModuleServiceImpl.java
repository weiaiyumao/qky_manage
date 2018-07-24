
/**
 * 功能模块管理Service实现
 * @author 许进
 */
package com.zqfinance.module.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zqfinance.module.sys.dao.ActionMapper;
import com.zqfinance.module.sys.dao.ModuleMapper;
import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Module;
import com.zqfinance.module.sys.service.ModuleService;
import com.zqfinance.system.common.TreeActionVo;
import com.zqfinance.system.common.TreeVo;
@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private ActionMapper actionMapper;
	
	public void saveModule(Module module) {
		moduleMapper.saveModule(module);
	}

	@Override
	public void updateModule(Module module) {
		moduleMapper.updateModule(module);	
	}

	@Override
	public List<Module> getModuleList(Module module) {
		return moduleMapper.getModuleList(module);	
	}

	@Override
	public void delModule(long moduleId) {
		List<Long> moduleIdList = new ArrayList<Long>();
		moduleIdList.add(moduleId);
		moduleIdList.addAll(moduleMapper.getSubIdList(moduleId));	
		List<Long> actionIdList =actionMapper.getActionByModuleIdList(moduleIdList);	
		//删除模块对应的action
		actionMapper.delActionByModuleIdList(moduleIdList);
		//删除role action对应关系
		if(!CollectionUtils.isEmpty(actionIdList)){
			actionMapper.delRoleActionMapByActionIdList(actionIdList);
		}	
		//删除模块
		moduleMapper.delModule(moduleId);
		//删除子模块
		moduleMapper.delSubModule(moduleId);
	}

	@Override
	public int getModuleCount() {
		return moduleMapper.getModuleCount();
		
	}

	@Override
	public Module getModuleById(long id) {
		return moduleMapper.getModuleById(id);
		
	}

	@Override
	public List<Module> getFirstLevelModule() {
		return moduleMapper.getFirstLevelModule();
		
	}

	@Override
	public List<Module> getSecondModule(long parentId) {
		return moduleMapper.getSecondModule(parentId);
		
	}

	@Override
	public List<Module> getAllSecondModule() {
		return moduleMapper.getAllSecondModule();
		
	}

	/**
	 * 取得模块树的json
	 * (non-Javadoc)
	 * @see com.zqfinance.module.sys.service.ModuleService#getModuleJson()
	 */
	public String getModuleJson(String id,String parentId) {
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		TreeVo treeVoBase = new TreeVo();
		treeVoBase.setId(0);
		treeVoBase.setpId(-1);
		treeVoBase.setName("模块展示");
		treeVoBase.setOpen(true);
		if(StringUtils.isNotEmpty(parentId)&&Long.valueOf(parentId)==0){
			treeVoBase.setChecked(true);
		}
		treeVoList.add(treeVoBase);

		List<Module> moduleList = moduleMapper.getAllModule();
		if(!CollectionUtils.isEmpty(moduleList)){
			for(Module module : moduleList){
				TreeVo treeVo = new TreeVo();
				if(StringUtils.isNotEmpty(id)&& module.getId() != Long.valueOf(id.trim())){			
					treeVo.setId(module.getId());
					treeVo.setName(module.getModuleName());
					treeVo.setpId(module.getParentId());
					if(StringUtils.isNotEmpty(parentId)){
						if(module.getId() == Long.valueOf(parentId.trim())){
							treeVo.setChecked(true);
						}
					}				
				}else{
					treeVo.setId(module.getId());
					treeVo.setName(module.getModuleName());
					treeVo.setpId(module.getParentId());
					if(StringUtils.isNotEmpty(parentId)){
						if(module.getId() == Long.valueOf(parentId.trim())){
							treeVo.setChecked(true);
						}
					}
				}
				treeVoList.add(treeVo);
			}	
		}
		return JSONArray.fromObject(treeVoList).toString();
		
	}
	
	
	public String getModuleJsonForAction(String moduleId) {
		String returnJson = new String();
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		List<Module> moduleList = moduleMapper.getAllModule();
		if(!CollectionUtils.isEmpty(moduleList)){
			for(Module module : moduleList){
					TreeVo treeVo = new TreeVo();
					treeVo.setId(module.getId());
					treeVo.setName(module.getModuleName());
					treeVo.setpId(module.getParentId());
					if(StringUtils.isNotEmpty(moduleId)){
						if(module.getId() == Long.valueOf(moduleId)){
							treeVo.setChecked(true);
						}
					}
					//判断是否是leaf节点
					if(!isLeafNode(module.getId(),moduleList)){
						treeVo.setNocheck(true);
					}
					treeVoList.add(treeVo);
			}
			returnJson = JSONArray.fromObject(treeVoList).toString();
		}
		return returnJson;
		
	}
	
	private boolean isLeafNode(long moduleId,List<Module> moduleList){
		for(Module module : moduleList){
			if(module.getParentId() == moduleId ){
				return false;
			}
		}
		return true;
	}
	
	public String getJsonForModuleAndAction(List<Long> actionIdList){
		List<TreeActionVo> treeVoList = new ArrayList<TreeActionVo>();
		List<Module> moduleList = moduleMapper.getAllModule();
		if(!CollectionUtils.isEmpty(moduleList)){
			for(Module module : moduleList){
					TreeActionVo treeVo = new TreeActionVo();
					treeVo.setId("M"+String.valueOf(module.getId()));
					treeVo.setName(module.getModuleName());
					treeVo.setpId("M"+String.valueOf(module.getParentId()));
					treeVo.setIsParent(true);
					treeVoList.add(treeVo);
			}
		}
		
		List<Action> actionList = actionMapper.getAllActionList();
		for(Action action :actionList){
			TreeActionVo treeVo = new TreeActionVo();
			treeVo.setId(String.valueOf(action.getId()));
			treeVo.setName(action.getActionName());
			treeVo.setpId("M"+String.valueOf(action.getModuleId()));
			treeVoList.add(treeVo);
			if(!CollectionUtils.isEmpty(actionIdList) && actionIdList.contains(action.getId())){
				treeVo.setChecked(true);
			}
		}
		return JSONArray.fromObject(treeVoList).toString();
	}

	@Override
	public Module getModuleByNameOrId(Module module) {
		return moduleMapper.getModuleByNameOrId(module);
	}
}

