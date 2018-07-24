/**
 * 自定义标签
 * @author 许进
 */
package com.zqfinance.system.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zqfinance.module.auth.lib.security.UserDetailImpl;

@SuppressWarnings("serial")
public class MgrButtonTag extends AbstractHtmlElementTag {
	private static Logger logger = LoggerFactory.getLogger(MgrButtonTag.class);

	public static enum target {
		navTab, dialog, ajaxTodo, selectedTodo,dwzExport;
	}

	private target target;

	public static enum ValidateResult {
		OK, FAILURE
	}

	private String id;
	
	private String href;

	private String cssClass;

	private String rel;

	private String title;

	private String fresh;

	private String external;

	private String max;

	private String mask;

	private String maxable;

	private String minable;

	private String resizable;

	private String drawable;

	private String close;

	private String param;

	private String warn;
	
	private String value;
	
	private String width;
	
	private String height;

	private String postType;
	
	private String pagechecked;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFresh() {
		return fresh;
	}

	public void setFresh(String fresh) {
		this.fresh = fresh;
	}

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getMaxable() {
		return maxable;
	}

	public void setMaxable(String maxable) {
		this.maxable = maxable;
	}

	public String getMinable() {
		return minable;
	}

	public void setMinable(String minable) {
		this.minable = minable;
	}

	public String getResizable() {
		return resizable;
	}

	public void setResizable(String resizable) {
		this.resizable = resizable;
	}

	public String getDrawable() {
		return drawable;
	}

	public void setDrawable(String drawable) {
		this.drawable = drawable;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}
	
	
	public target getTarget() {
		return target;
	}

	public void setTarget(target target) {
		this.target = target;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPagechecked() {
		return pagechecked;
	}

	public void setPagechecked(String pagechecked) {
		this.pagechecked = pagechecked;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		if(ValidateResult.FAILURE == this.validatePrincipal()){
			return 0;
		}
		switch (this.target) {
		case navTab:
			this.writeButton(tagWriter);
			break;
		case ajaxTodo:
			this.writeAjaxTodoButton(tagWriter);
			break;
		case dialog:
			this.writeDialogButton(tagWriter);
			break;
		case selectedTodo:
			this.writeSelectedTodo(tagWriter);
			break;
		case dwzExport:
			this.writeDwzExport(tagWriter);
			break;
		default:
			logger.info("{}传入参数不在【MgrButtonTag】定义的范围内",
					MgrButtonTag.class.getName());
		}
		return 0;

	}
	
    /**
     * 验证该用户是否具有该按钮的权限
     * 
     * @return
     */
	private ValidateResult validatePrincipal() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() == null) {
            logger.info("{}获取用户权限失败", MgrButtonTag.class.getName());
            return ValidateResult.FAILURE;
        }

        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(auth);
            Object result = wrapper.getPropertyValue("principal");
            if (result == null) {
                return ValidateResult.FAILURE;
            }
            UserDetailImpl usr = (UserDetailImpl) result;
            List<String> actionUrlList = usr.getActionUrl();
            for(String actionUrl : actionUrlList){
            	if(this.href.contains(actionUrl)){
            		return ValidateResult.OK;
            	}
            }          
        } catch (Exception e) {
            logger.info(MgrButtonTag.class.getName() + "按钮权限标签验证失败", e);
        }
        return ValidateResult.FAILURE;
    }

	private void writeButton(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("a");
		tagWriter.writeAttribute("class", this.cssClass);
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "navTab");
		if(StringUtils.isNotEmpty(rel)){
			tagWriter.writeAttribute("rel", this.rel);
		}
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		if(StringUtils.isNotEmpty(fresh)){
			tagWriter.writeAttribute("fresh", this.fresh);
		}
		if(StringUtils.isNotEmpty(external)){
			tagWriter.writeAttribute("external", this.external);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}
	
	private void writeAjaxTodoButton(TagWriter tagWriter) throws JspException{
		tagWriter.startTag("a");
		tagWriter.writeAttribute("class", this.cssClass);
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "ajaxTodo");
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}

	private void writeDialogButton(TagWriter tagWriter) throws JspException{
		tagWriter.startTag("a");
		tagWriter.writeAttribute("class", this.cssClass);
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "dialog");
		tagWriter.writeAttribute("id", this.id);
		if(StringUtils.isNotEmpty(max)){
			tagWriter.writeAttribute("max", this.max);
		}
		if(StringUtils.isNotEmpty(mask)){
			tagWriter.writeAttribute("mask", this.mask);
		}
		if(StringUtils.isNotEmpty(maxable)){
			tagWriter.writeAttribute("maxable", this.maxable);
		}
		if(StringUtils.isNotEmpty(minable)){
			tagWriter.writeAttribute("minable", this.minable);
		}
		if(StringUtils.isNotEmpty(resizable)){
			tagWriter.writeAttribute("resizable", this.resizable);
		}
		if(StringUtils.isNotEmpty(drawable)){
			tagWriter.writeAttribute("drawable", this.drawable);
		}
		if(StringUtils.isNotEmpty(rel)){
			tagWriter.writeAttribute("rel", this.rel);
		}
		if(StringUtils.isNotEmpty(close)){
			tagWriter.writeAttribute("close", this.close);
		}
		if(StringUtils.isNotEmpty(param)){
			tagWriter.writeAttribute("param", this.param);
		}
		if(StringUtils.isNotEmpty(warn)){
			tagWriter.writeAttribute("warn", this.warn);
		}
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		if(StringUtils.isNotEmpty(width)){
			tagWriter.writeAttribute("width", this.width);
		}
		if(StringUtils.isNotEmpty(height)){
			tagWriter.writeAttribute("height", this.height);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}
	
	public void writeSelectedTodo(TagWriter tagWriter) throws JspException{
		tagWriter.startTag("a");
		tagWriter.writeAttribute("class", this.cssClass);
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "selectedTodo");
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		if(StringUtils.isNotEmpty(postType)){
			tagWriter.writeAttribute("postType", this.postType);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}
	
	public void writeDwzExport(TagWriter tagWriter) throws JspException{
		tagWriter.startTag("a");
		tagWriter.writeAttribute("class", this.cssClass);
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "dwzExport");
		tagWriter.writeAttribute("targettype", "navTab");
		tagWriter.writeAttribute("pagechecked", this.pagechecked);
		
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}
}
