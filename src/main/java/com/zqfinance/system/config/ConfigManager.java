/**
 * 读取配置文件
 */

package com.zqfinance.system.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class ConfigManager {
	private static final Log log = LogFactory
			.getLog(ConfigManager.class);

	private static Properties props = new Properties();

	public static String CARD_FLAG_PRI="0";//银行卡对私
	public static String CARD_FLAG_PUB="1";//银行卡对私
	
	public static String FILE_SEPARATOR = "/";

	private ConfigManager() {
	}

	static {
		Resource resource = new ClassPathResource("/app.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("解析url.properties出现异常", e);
		}
		
		String sp = System.getProperty("file.separator");
		if(null != sp) {
			if("\\".equals(sp)) {
				sp = "\\\\";
			}
			FILE_SEPARATOR = sp;
		}
	}
	
	/**理财交易系统访问地址**/
	public static final String LICAITRADING_URL = props.getProperty("licaitrading.url");
	/**交易系统访问地址**/
	public static final String TRADING_URL = props.getProperty("trading.url");
	/**licaipay系统访问地址**/
	public static final String LICAIPAY_URL = props.getProperty("licaipay.url");
	
	public static final String  USER_URL = props.getProperty("user.url");
	
	public static final String LOAN_IMAGE_SAVEPATH =props.getProperty("LOAN_IMAGE_SAVEPATH"); //贷款标图片存放地址
	
	public static final String CFG_LCTRADING_PRIKEY = props.getProperty("LCTRADING_PRIKEY");  // 签名私钥
	public static final String CFG_LCTRADING_SIGNKEY = props.getProperty("LCTRADING_SIGNKEY");  // 签名密码
	public static final String CFG_LCTRADING_SIGNPARAM = props.getProperty("LCTRADING_SIGNPARAM");    // 验签请求参数名
	
	
	public static final String CFG_P2B_PRIKEY = props.getProperty("P2B_PRIKEY");  // 签名私钥
	public static final String CFG_P2B_SIGNKEY = props.getProperty("P2B_SIGNKEY");  // 签名密码
	public static final String CFG_P2B_SIGNPARAM = props.getProperty("P2B_SIGNPARAM");    // 验签请求参数名
	public static final String CFG_P2B_COOKIE_SID = props.getProperty("P2B_COOKIE_SID");

	//连连代付订单申请
	public static final String COMPANYOUTMONEY_LOANPAY=props.getProperty("COMPANYOUTMONEY_LOANPAY");
	public static final String OUTMONEY_LOANPAY=props.getProperty("OUTMONEY_LOANPAY");
	//连连代付提现，对账
	public static final String OUTMONEY_AUTORECONCILIATION=props.getProperty("OUTMONEY_AUTORECONCILIATION");
	//连连代付订单描述
	public static final String OUTMONEY_LOAN_INFOORDER=props.getProperty("OUTMONEY_LOAN_INFOORDER");

	public static final String OUTMONEY_AUDIT = props.getProperty("OUTMONEY_AUDIT");
	
	/**获取客户列表**/
	public static final String CUSTOMER_LIST = LICAITRADING_URL + "/customer/customerList";
	public static final String  LOAN_CUSTOMER_LIST = USER_URL + "/api/Customer/list.do";
	public static final String  LOAN_SINGLECUSTOMER_LIST = USER_URL + "/api/Customer/singleList.do";
	public static final String  LOAN_FINDBYCUSTOMERID = USER_URL + "/api/Customer/getCustomerListByCustomerIds.do";
	public static final String CUSTOMERINFO_FINDBYCUSTOMERID = LICAITRADING_URL +"/customerInfo/getCustomerInfoByCustomerId";
	
	public static final String PRODUCTORDER_GETSUMMONEYBYCUSTOMER = LICAITRADING_URL +"/productOrder/getSumMoneyByCustomer";
	public static final String PRODUCTORDER_SAVE = LICAITRADING_URL + "/productOrder/addProductOrder";
	public static final String PRODUCTORDER_GETBYID = LICAITRADING_URL + "/productOrder/getProductOrder";
	public static final String PRODUCTORDER_UPDATESTATUS = LICAITRADING_URL + "/productOrder/updateProductOrderStatus";
	public static final String REPAIRPRODUCTORDERSTATUS = LICAITRADING_URL + "/productOrder/repairProductOrderStatus";
	public static final String PRODUCTORDER_LIST =LICAITRADING_URL + "/productOrder/getInvestProductOrderList";
    public static final String PRODUCTORDERDEL_BYIDS = LICAITRADING_URL + "/productOrder/delByIds";
    public static final String PRODUCIORDER_QUERYORDERPAYSTATUS = LICAIPAY_URL + "/queryOrder/queryOrderStatus";
    public static final String PRODUCTORDER_GETSUMMONEYBYINVESTPRODUCTID = LICAITRADING_URL + "/productOrder/getSumMoneyByInvestProductId";
    public static final String PRODUCTORDER_GETORDERSUMMONEYINFO = LICAITRADING_URL + "/productOrder/getInvestProductOrderMoneyInfo";
	
	public static final String CUSTOMERCL_SAVE = LICAITRADING_URL + "/customerLC/addCustomerLC";
	public static final String CUSTOMERCL_GETBYID = LICAITRADING_URL + "/customerLC/getById";
	public static final String CUSTOMERCL_DELBYIDS = LICAITRADING_URL + "/customerLC/delByIds";
	public static final String CUSTOMERLC_FINDBYCONDITION = LICAITRADING_URL + "/customerLC/findByCondition";
	public static final String CUSTOMERCL_UPDATE = LICAITRADING_URL + "/customerLC/updateCustomerLC";
	public static final String CUSTOMERCL_GETBYIDNO = LICAITRADING_URL + "/customerLC/getByIdNo";
	public static final String CUSTOMERCL_GETBYCUSTOMERID = LICAITRADING_URL + "/customerLC/getByCustomerId";
	public static final String INVESTPRODUCT_GETALL = LICAITRADING_URL + "/investProduct/getAllProductList";
	
	public static final String INVESTPRODUCT_SAVE = LICAITRADING_URL + "/investProduct/addInvestProduct";
	public static final String INVESTPRODUCT_GETPAGE = LICAITRADING_URL + "/investProduct/findByCondition";
	public static final String INVESTPRODUCT_GETBYID = LICAITRADING_URL + "/investProduct/getById";
	public static final String INVESTPRODUCT_DEL = LICAITRADING_URL + "/investProduct/delInvestProduct";
	public static final String INVESTPRODUCT_AUDIT = LICAITRADING_URL + "/investProduct/auditProductByIds";
	public static final String INVESTPRODUCT_UPDATE =LICAITRADING_URL + "/investProduct/updateInvestProduct";
	
	public static final String INVESTPRODUCTAPR_LIST = LICAITRADING_URL + "/investProductApr/getByInvestProductId";
	
	public static final String CREDIT_GETCREDITLIST = TRADING_URL + "/creditAssign/getCreditList";
	public static final String INVESTPRODUCTORDER_MAPPINGLOANORCREDIT = TRADING_URL + "/investProductOrder/mappingLoanOrCredit";
	
	public static final String  LOAN_FINDLIST =LICAITRADING_URL+ "/loan/findList";
	public static final String  LOAN_Add = LICAITRADING_URL +"/loan/save";
	public static final String  LOAN_UPDATE = LICAITRADING_URL +"/loan/update";
	public static final String  LOAN_GETBYID = LICAITRADING_URL+ "/loan/getById";	
	public static final String  LOAN_UPDATE_BY_STATUS = LICAITRADING_URL +"/loan/updateFlagById";
	public static final String UPDATE_FLAG_BYIDS = LICAITRADING_URL + "/loan/updateFlagByIds";
	public static final String  LOAN_LOANRATE_LIST = LICAITRADING_URL + "/loan/getLoanRateList";
	public static final String  LOANBILL_DELETE_BYIDS = LICAITRADING_URL +"/loan/del";
	public static final String  LOANBILL_GETBYID = LICAITRADING_URL  + "/loan/getById";
	public static final String LOAN_FINDBYVIEWLIST = LICAITRADING_URL + "/loan/findViewList";
	public static final String LOAN_FINDBYORDERID = LICAITRADING_URL + "/loan/findViewByOrderId";
	
	public static final String LOANINVESTPRODUCTORDER_MATCHLOAN = LICAITRADING_URL + "/loanInvestProductOrder/matchLoan";
	public static final String LOANINVESTPRODUCTORDER_AUTOMATCHLOAN = LICAITRADING_URL + "/loanInvestProductOrder/autoMatchLoan";
	public static final String LOANINVESTPRODUCTORDER_GETMATCHMONEY = LICAITRADING_URL + "/loanInvestProductOrder/getMatchMoney";
	
	public static final String LAW_SAVE = LICAITRADING_URL + "/law/addLaw";
	public static final String LAW_UPDATE = LICAITRADING_URL + "/law/updataLaw";
	public static final String LAW_DELBYIDS = LICAITRADING_URL + "/law/delByIds";
	public static final String LAW_DELETEDBYIDS = LICAITRADING_URL + "/law/deletedByIds";
	public static final String LAW_FINDLIST = LICAITRADING_URL + "/law/findList";
	public static final String LAW_FINDBYID = LICAITRADING_URL + "/law/findById";
	public static final String LAW_FINDBYALL = LICAITRADING_URL + "/law/findByAll";
	
	public static final String RULEASSETAPR_ADD = LICAITRADING_URL + "/ruleAssetapr/addRuleAssetapr";
	public static final String RULEASSETAPR_UPDATE = LICAITRADING_URL + "/ruleAssetapr/updateRuleAssetapr";
	public static final String RULEASSETAPR_UPDATETITLE = LICAITRADING_URL + "/ruleAssetapr/updateRuleAssetaprTitle";
	public static final String RULEASSETAPR_DEL = LICAITRADING_URL + "/ruleAssetapr/delByIds";
	public static final String RULEASSETAPR_GETBYID = LICAITRADING_URL + "/ruleAssetapr/getById";
	public static final String RULEASSETAPR_FINDBYCONDITION = LICAITRADING_URL + "/ruleAssetapr/findByCondition";
	public static final String RULEASSETAPR_GETBYLASTRULEASSETAPR = LICAITRADING_URL + "/ruleAssetapr/findByLastRuleAssetapr";
	
	public static final String COMPANYOUTMONEY_GETCOMPANYBANKANDCOMPANYOUTMONEY=LICAITRADING_URL + "/companyOutMoney/getCompanyBankAndCompanyOutMoneyById";
	public static final String COMPANYOUTMONEYBYID=LICAITRADING_URL +"/companyOutMoney/getCompanyOutMoneyById";
	public static final String COMPANYOUTMONEY_LIST=LICAITRADING_URL +"/companyOutMoney/getCompanyOutMoneyList";
	public static final String COMPANYOUTMONEY_SAVE=LICAITRADING_URL +"/companyOutMoney/saveCompanyOutMoney";
	public static final String COMPANYOUTMONEYBYID_AUDIT=LICAITRADING_URL +"/companyOutMoney/aduitCompanyOutMoneyById";
	public static final String COMPANYOUTMONEY_UPDATEAFTERPAYSUCCESS=LICAITRADING_URL +"/companyOutMoney/updateStatusAfterPaySuccess";
	
	public static final String PAYBANK_FINDALL = LICAITRADING_URL + "/paybank/findAll";
	
	public static final String OUTMONEY_GETOUTMONEYBYCUSTOMERID=LICAITRADING_URL +"/outMoney/getOutMoneyByCustomerId";
	public static final String OUTMONEY_USABLESUMMONEY=LICAITRADING_URL + "/outMoney/getUsableSumMoney";
	public static final String OUTMONEY_USABLESUMMONEYBYMANAGE=LICAITRADING_URL + "/outMoney/getUsableSumMoneyByManage";
	public static final String OUTMONEY_LIST=LICAITRADING_URL + "/outMoney/getOutMoneyList";
	public static final String OUTMONEY_ADVANCEDSEARCHSUMMONEY=LICAITRADING_URL + "/outMoney/advancedSearchSumMoney";
	public static final String OUTMONEYBYID=LICAITRADING_URL + "/outMoney/getOutMoneyById";
	public static final String OUTMONEYAUDIT=LICAITRADING_URL +"/outMoney/auditOutMoney";
	public static final String OUTMONEY_UPDATEPAYSUCCESS =LICAITRADING_URL +"/outMoney/updatePaySuccess";
	public static final String UPDATERECONCILIATIONBYID = LICAITRADING_URL +"/outMoney/updateIsReconciliationById";
	public static final String DELETEOUTMONEY=LICAITRADING_URL +"/outMoney/deleteOutMoney";
	public static final String OUTMONEYBYIDS=LICAITRADING_URL +"/outMoney/getOutMoneyByIds";
	public static final String OUTMONEYFLOW_SAVEOUTMONEYRECONCILIATION=LICAITRADING_URL+"/outMoneyReconciliation/saveOutMoneyReconciliation";
	public static final String OUTMONEYINVERSTPRODUCTBYID=LICAITRADING_URL + "/outMoneyInverstProduc/getOutMoneyInverstProductById";
	public static final String OUTMONEY_GETCUSTOMERBANKANDOUTMONEY=LICAITRADING_URL + "/outMoney/getCutstomerBankAndOutMoneyById";
	public static final String CUSTOMERLC_FINDBYCUSTOMERIDANDCARDNO = LICAITRADING_URL + "/customerLC/findByCustomerIdAndCardNo";
	//连连代付提现
	public static final String OUTMONEY_PAY_GETCUSTOMERBANKANDOUTMONEY=LICAIPAY_URL+"/outMoney/outMoneyPay";
	public static final String OUTMONEYOPERATIONFLOWBYOUTMONEYID = LICAITRADING_URL+"/outMoneyOperationFlow/getOutMoneyOperationFlowByOutMoneyId";
	public static final String OUTMONEYFLOW_RECONCILIATIONSEARCH=LICAITRADING_URL+"/outMoneyReconciliation/reconciliationSearch";
	public static final String BUY_SAVEBUYRECONCILIATION = LICAITRADING_URL+"/outMoney/saveBuyReconciliation";
	public static final String VALIDATE_LASTCOUNTAPR = LICAITRADING_URL+"/outMoney/validateLastCountapr";
	public static final String VALIDATE_CARDIDANDMONEY = LICAITRADING_URL+"/outMoney/validateCardIdAndMoney";
	
	public static final String INVESTDUMMYPRINCIPLE_SAVE = LICAITRADING_URL + "/investdummyprinciple/save";
	public static final String INVESTDUMMYPRINCIPLE_UPDATE = LICAITRADING_URL + "/investdummyprinciple/update";
	public static final String INVESTDUMMYPRINCIPLE_FINDBYID = LICAITRADING_URL + "/investdummyprinciple/findById";
	public static final String INVESTDUMMYPRINCIPLE_FINDBYCONDITION = LICAITRADING_URL + "/investdummyprinciple/findByCondition";
	public static final String INVESTDUMMYPRINCIPLE_DELBYIDS = LICAITRADING_URL + "/investdummyprinciple/del";
	
	public static final String REWARDS_ADD = LICAITRADING_URL + "/rewards/add";
	public static final String REWARDS_FINDBYID = LICAITRADING_URL + "/rewards/getById";
	public static final String REWARDS_FINDBYCONDITION = LICAITRADING_URL + "/rewards/findByCondition";
	public static final String REWARDS_FINDBYORDERID = LICAITRADING_URL + "/rewards/getByOrderId";
	public static final String REWARDS_UPDATE = LICAITRADING_URL + "/rewards/update";
	public static final String REWARDS_DELBYIDS = LICAITRADING_URL + "/rewards/delByIds";
	
	public static final String COMPANYOUTMONEYBANK_FINDBYPAGE = LICAITRADING_URL + "/company/findByPage";
	public static final String COMPANYOUTMONEYBANK_SAVE = LICAITRADING_URL + "/company/save";
	public static final String COMPANYOUTMONEYBANK_UPDATE = LICAITRADING_URL + "/company/update";
	public static final String COMPANYOUTMONEYBANK_DEL = LICAITRADING_URL + "/company/del";
	public static final String COMPANYOUTMONEYBANK_FINDBYID = LICAITRADING_URL + "/company/findById";
	
	public static final String OUTMONEYFLOW_SAVEPROVINCECITY = LICAITRADING_URL + "/outMoney/saveProvinceCity";
	public static final String OUTMONEYFLOW_FINDPROVINCE = LICAITRADING_URL + "/outMoney/findProvince";
	public static final String OUTMONEYFLOW_FINDALLCITY = LICAITRADING_URL + "/outMoney/findAllcity";
	public static final String OUTMONEYFLOW_FINDCITY = LICAITRADING_URL + "/outMoney/findCity";
	
	public static final String PAYBANK_SAVE = LICAITRADING_URL + "/paybank/save";
	public static final String PAYBANK_DELBYID = LICAITRADING_URL + "/paybank/del";
	public static final String PAYBANK_UPDATE = LICAITRADING_URL + "/paybank/update";
	public static final String PAYBANK_FINDPAGE = LICAITRADING_URL + "/paybank/findList";
	public static final String PAYBANK_FINDBYID = LICAITRADING_URL + "/paybank/findById";
	public static final String PAYBANK_UPATEPAYBANSTATUS = LICAIPAY_URL + "/queryCard/querySupportBank";
	
	public static final String BUY_SAVEANDRECONCILIATION = LICAITRADING_URL + "/outMoney/saveAndReconciliation";
	
	//======rpt========
	public static final String ORDERRPT_GETORDERRPTMONTH = LICAITRADING_URL + "/orderRpt/getOrderRptMonth";	
	public static final String ORDERRPT_GETORDERRPTAGE = LICAITRADING_URL + "/orderRpt/getOrderRptAge";
	public static final String OUTMONEY_ORDERRPT_GETORDERRPTMONTH = LICAITRADING_URL + "/outMoneyOrderRpt/getOrderRptMonth";
	public static final String OUTMONEY_ORDERRPT_GETORDERRPTAGE = LICAITRADING_URL + "/outMoneyOrderRpt/getOrderRptAge";
	public static final String COMPANYORDERRPT_GETORDERRPTAGE = LICAITRADING_URL + "/companyOrderRpt/getCompanyOrderRptYear";
	public static final String COMPANYORDERRPT_GETOUTMONEYORDERRPTAGE =LICAITRADING_URL + "/companyOrderRpt/getCompanyOutMoneyOrderRptYearList";
	
	public static final String COMPANYORDER_CREATE = LICAITRADING_URL + "/companyOrder/createCompanyOrder";
	public static final String COMPANYORDER_GETCOMPANYORDERPAGEVIEW =LICAITRADING_URL + "/companyOrder/getCompanyOrderViewPage";
	public static final String COMPANYORDER_FINDBYID = LICAITRADING_URL + "/companyOrder/findById";
	public static final String COMPANYORDER_UPDATEORDERSTAUTS = LICAITRADING_URL + "/companyOrder/updateOrderStatus";
	
	//fulr
	public static final String FUNDUTILIZATIONRATE_GETORDERRESIDUALMONEYBYDATE = LICAITRADING_URL + "/fulr/getOrderResidualMoneyByDate";
	public static final String FUNDUTILIZATIONRATE_GETORDERRESIDUALMONEYBYDAY = LICAITRADING_URL + "/fulr/getOrderResidualMoneyByDay";
	public static final String FUNDUTILIZATIONRATE_GETORDERRESIDUALMONEYBYTODAY = LICAITRADING_URL + "/fulr/getOrderResidualMoneyByToday";
	public static final String FUNDUTILIZATIONRATE_GETCOUNTORDERMONEYBYDAY = LICAITRADING_URL + "/fulr/getCountOrderMoneyByDay";
	
	//红包
	public static final String CASHGIFTOUT_FINDBYCONDITION = LICAITRADING_URL + "/cashGiftOut/findByCondition";
	public static final String CASHGIFTOUT_GETSUMMONEY = LICAITRADING_URL + "/cashGiftOut/getSumMoney";
	public static final String CASHGIFTOUT_GETUSEDCASHGIFTOUTBYORDERID = LICAITRADING_URL + "/cashGiftOut/getUsedCashGiftOutByOrderId";
	public static final String CASH_FINDBYCONDITION =  LICAITRADING_URL + "/cash/findByCondition";
	public static final String CASH_GETSUMMONEY = LICAITRADING_URL + "/cash/getSumMoney";
	
	
	//lcpay encypt
	public static final String CFG_LCPAY_PRIKEY = props.getProperty("LCPAY_PRIKEY");
	public static final String CFG_LCPAY_SIGNKEY = props.getProperty("LCPAY_SIGNKEY");
	public static final String CFG_LCPAY_SIGNPARAM = props.getProperty("LCPAY_SIGNPARAM");
	
	//bankgate pay url
	public static final String BANKGATE_PAY = LICAIPAY_URL + "/webpay/bankgatewayPay";
	
	// 放款限额
	public static final String OUTMONEY_QUOTA = props.getProperty("OUTMONEY_QUOTA");
}

