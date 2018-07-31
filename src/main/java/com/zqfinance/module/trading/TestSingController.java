package com.zqfinance.module.trading;

import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestSingController {

    @Autowired
    private SendRequestService sendRequestService;

    @RequestMapping("/signTest")
    @ResponseBody
    public DataMessage switchTemplateIsOpen(HttpServletRequest request) {
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        jsonObject.put("test","123456");
        String resultString = sendRequestService.sendRequest("http://localhost:8088/test/demo2",jsonObject);
        DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
        return dataMessage;
    }
}
