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
public class MgrMenuTag extends AbstractHtmlElementTag {
	private static Logger logger = LoggerFactory.getLogger(MgrMenuTag.class);


	public static enum ValidateResult {
		OK, FAILURE
	}

	private String href;

	private String target;

	private String rel;

	private String title;
	
	private String value;
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		if(ValidateResult.FAILURE == this.validatePrincipal()){
			return 0;
		}
		writeMenu(tagWriter);
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
            logger.info("{}获取用户权限失败", MgrMenuTag.class.getName());
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
            logger.info(MgrMenuTag.class.getName() + "按钮权限标签验证失败", e);
        }
        return ValidateResult.FAILURE;
    }

	private void writeMenu(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("a");
		tagWriter.writeAttribute("href", this.href);
		tagWriter.writeAttribute("target", "navTab");
		if(StringUtils.isNotEmpty(rel)){
			tagWriter.writeAttribute("rel", this.rel);
		}
		if(StringUtils.isNotEmpty(title)){
			tagWriter.writeAttribute("title", this.title);
		}
		tagWriter.appendValue("<span>" + this.value + "</span>");
		tagWriter.endTag();
	}
	
}
