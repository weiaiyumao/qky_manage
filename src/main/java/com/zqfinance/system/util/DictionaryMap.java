package com.zqfinance.system.util;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

public class DictionaryMap {
	/**
	 * 债权转让状态
	 * 0、取消 1 投标中 2、满标 3、已成交 4、流标 5、审核6、待审核
	 */
	public static Map<String,String> CREDIT_STATUS = new TreeMap<String,String>();
	static{
		CREDIT_STATUS.put("0","取消");
		CREDIT_STATUS.put("1","投标中");
		CREDIT_STATUS.put("2","满标");
		CREDIT_STATUS.put("3","已成交");
		CREDIT_STATUS.put("4","流标");
		CREDIT_STATUS.put("5","审核");
	}
	
	/**
	 * 公司提现状态
	 * 0:成功，1:失败，2:未处理，3:放款中，4：审核成功
	 */
	public static Map<String,String> COMPANYOUTMONEY_FLAG  = new TreeMap<String,String>();
	static{
		COMPANYOUTMONEY_FLAG.put("0","待处理");
		COMPANYOUTMONEY_FLAG.put("1","成功");
		COMPANYOUTMONEY_FLAG.put("2","失败");
		COMPANYOUTMONEY_FLAG.put("3","放款中");
		COMPANYOUTMONEY_FLAG.put("4","审核成功");
	}
	
	/**
	 * 提现状态
	 * 0:成功，1:失败，2:未处理，3:处理中
	 */
	public static Map<String,String> OUTMONEY_FLAG  = new TreeMap<String,String>();
	static{
		OUTMONEY_FLAG.put("0","待处理");
		OUTMONEY_FLAG.put("1","成功");
		OUTMONEY_FLAG.put("2","失败");
		OUTMONEY_FLAG.put("3","审核通过");
		OUTMONEY_FLAG.put("4","放款中");
	}
	
	/**
	 * 银行编码和名称对应关系
	 */
	public static final Map<String,String> BANK_MAP = new HashMap<String,String>();
	static {
		BANK_MAP.put("01020000", "工商银行");
		BANK_MAP.put("01030000", "农业银行");
		BANK_MAP.put("01040000", "中国银行");
		BANK_MAP.put("01050000", "建设银行");
		BANK_MAP.put("03080000", "招商银行");
		BANK_MAP.put("03100000", "浦发银行");
		BANK_MAP.put("03030000", "光大银行");
		BANK_MAP.put("03070000", "平安银行");
		BANK_MAP.put("03040000", "华夏银行");
		BANK_MAP.put("03090000", "兴业银行");
		BANK_MAP.put("03020000", "中信银行");
		BANK_MAP.put("01000000", "储蓄银行");
	}
	/**
	 * 费用提供 
	 * 0:自己 1:平台
	 */
	public static Map<String,String> OUTMONEY_FEEPROVIDER  = new TreeMap<String,String>();
	static{
		OUTMONEY_FEEPROVIDER.put("0","客户");
		OUTMONEY_FEEPROVIDER.put("1","平台");
	}
	/**
	 * 充值状态
	 * 0:成功，1:失败，2:未处理，3:处理中 4:退款
	 */
	public static Map<String,String> INMONEY_FLAG  = new TreeMap<String,String>();
	static{
		INMONEY_FLAG.put("0","成功");
		INMONEY_FLAG.put("1","失败");
		INMONEY_FLAG.put("2","未处理");
		INMONEY_FLAG.put("3","处理中");
		INMONEY_FLAG.put("4","退款");
	}
	/**
	 * 投标状态
	 * 是否通过：0:待处理 1:通过，2:不通过
	 */
	public static Map<String,String> TENDER_FLAG  = new TreeMap<String,String>();
	static{
		TENDER_FLAG.put("0","待处理");
		TENDER_FLAG.put("1","通过");
		TENDER_FLAG.put("2","不通过");
	}
	/**
	 * 投标
	 * 汇付处理结果：0:处理中，1:成功，2:失败， 9:待处理
	 */
	public static Map<String,String> TENDER_CHINAPNRFLAG  = new TreeMap<String,String>();
	static{
		TENDER_CHINAPNRFLAG.put("0","处理中");
		TENDER_CHINAPNRFLAG.put("1","成功");
		TENDER_CHINAPNRFLAG.put("2","失败");
		TENDER_CHINAPNRFLAG.put("9","待处理");
	}
	/**
	 * 投标
	 * 投标操作类型 1 手工投标，2自动投标
	 */
	public static Map<String,String> TENDER_OPERATETYPE  = new TreeMap<String,String>();
	static{
		TENDER_OPERATETYPE.put("1","手工投标");
		TENDER_OPERATETYPE.put("2","自动投标");
	}
	/**
	 * 投标
	 * is_loan 放款状态 0未操作，1已放款，2未放款
	 */
	public static Map<String,String> TENDER_ISLOAN  = new TreeMap<String,String>();
	static{
		TENDER_ISLOAN.put("0","待处理");
		TENDER_ISLOAN.put("1","成功");
		TENDER_ISLOAN.put("2","失败");
	}
	/**
	 * 借款状态：1:审核，2:投标中，3:满标，4:还款中，5:已还完，6:网站垫付，7:坏账，8:流标还款方式：
	 */
	public static Map<String,String> LOAN_LOANSTATUS  = new TreeMap<String,String>();
	static{
		LOAN_LOANSTATUS.put("1","待审核");
		LOAN_LOANSTATUS.put("2","投标中");
		LOAN_LOANSTATUS.put("3","满标");
		LOAN_LOANSTATUS.put("4","还款中");
		LOAN_LOANSTATUS.put("5","已到期");//原始是已还完
		LOAN_LOANSTATUS.put("6","网站垫付");
		LOAN_LOANSTATUS.put("7","坏账");
		LOAN_LOANSTATUS.put("8","流标");
	}
	/**
	 * 还款方式：
	 * 1:等额本息（标准），2:先息后本，3:到期本息，4:等额本息（复利）5:等额本金
	 */
	public static Map<String,String> LOAN_METHOD  = new TreeMap<String,String>();
	static{
		LOAN_METHOD.put("1"," 等额本息(标准)");
		LOAN_METHOD.put("2","先息后本");
		LOAN_METHOD.put("3","到期本息");
		LOAN_METHOD.put("4","等额本息(复利)");
		LOAN_METHOD.put("5","等额本金");
	}
	/**
	 * 还款周期：1:月，2:季度，3:年，4:到期还本息，5:每日虚拟返还
	 */
	public static Map<String,String> LOAN_CYCLE  = new TreeMap<String,String>();
	static{
		LOAN_CYCLE.put("1"," 月");
		LOAN_CYCLE.put("2","季度");
		LOAN_CYCLE.put("3","年");
		LOAN_CYCLE.put("4","到期还本息");
		LOAN_CYCLE.put("5","每日虚拟返还");
	}
	/**
	 * 借款保障类型：1:保本，2:保本息，3:保本保收益
	 */
	public static Map<String,String> LOAN_SAFETYPE  = new TreeMap<String,String>();
	static{
		LOAN_SAFETYPE.put("1"," 保本");
		LOAN_SAFETYPE.put("2","保本息");
		LOAN_SAFETYPE.put("3","保本保收益");
	}
	/**
	 * 标类型 ：0：普通贷款标   1：对接项目标
	 */
	public static Map<String,String> LOAN_TYPE  = new TreeMap<String,String>();
	static{
		LOAN_TYPE.put("0"," 普通贷款标");
		LOAN_TYPE.put("1","对接项目标");
	}
	
	/**
	 * 是否
	 */
	public static Map<String,String> YESORNO  = new TreeMap<String,String>();
	static{
		YESORNO.put("1"," 是");
		YESORNO.put("2","否");
	}
	/**
	 * 还款申请状态：0 成功 1 待处理 2 审核通过 3 审核未通过  4 还款处理中 5 失败
	 */
	public static Map<String,String> REPAY_BILL_FLAG  = new TreeMap<String,String>();
	static{
		REPAY_BILL_FLAG.put("1","待处理");
		REPAY_BILL_FLAG.put("2","审核通过");
		REPAY_BILL_FLAG.put("3"," 审核未通过");
	}
	/**
	 * 还款业务类型： 1 普通还款（单期操作） 2 委托还款 3 代偿还款（平台-->借款人-->投资人） 4 垫付还款（借款人-->平台） 5 提前还款
	 */
	public static Map<String,String> REPAY_BILL_TYPE  = new TreeMap<String,String>();
	static{
		REPAY_BILL_TYPE.put("1","普通还款");
		REPAY_BILL_TYPE.put("2","委托还款");
		REPAY_BILL_TYPE.put("3","代偿还款");
		REPAY_BILL_TYPE.put("4","垫付还款");
		REPAY_BILL_TYPE.put("5","提前还款");
	}
	/**
	 * 还款类型：0 暂未还款 1 正常还款2 部分还款3 提前还款4 逾期还款
	 */
	public static Map<String,String> REPAY_PLAN_REPAYTYPE  = new TreeMap<String,String>();
	static{
		REPAY_PLAN_REPAYTYPE.put("0","暂未还款");
		REPAY_PLAN_REPAYTYPE.put("1","正常还款");
		REPAY_PLAN_REPAYTYPE.put("2","部分还款");
		REPAY_PLAN_REPAYTYPE.put("3","提前还款");
		REPAY_PLAN_REPAYTYPE.put("4","逾期还款");
	}
	/**
	 * 贷款活动状态
	 * 状态标志:默认 1:未使用   2:已使用   3:作废   4:处理中 5:失败
	 */
	public static Map<String,String> ACTIVITY_FLAG  = new TreeMap<String,String>();
	static{
		ACTIVITY_FLAG.put("1","未使用");
		ACTIVITY_FLAG.put("2","已使用");
		ACTIVITY_FLAG.put("3","作废");
		ACTIVITY_FLAG.put("4","处理中");
		ACTIVITY_FLAG.put("5","失败");
	}
	/**
	 * 借款期限类型
	 */
	public static Map<String,String> TERM_TYPE = new TreeMap<String,String>();
	static {
		TERM_TYPE.put("0", "天");
		TERM_TYPE.put("1", "月");
	}
	
	/**
	 * 费用提供 0自己 1平台
	 */
	public static Map<String,String> OUTMONEY_FEE_PROVIDER  = new TreeMap<String,String>();
	static{
		OUTMONEY_FEE_PROVIDER.put("0","自己");
		OUTMONEY_FEE_PROVIDER.put("1","平台");
	}
	
	/**
	 * loanflag
	 */
	public static Map<String,String> LOAN_FLAG = new TreeMap<String,String>();
	static {
		LOAN_FLAG.put("0", "待处理");
		LOAN_FLAG.put("1", "通过");
		LOAN_FLAG.put("2", "不通过");
	}
	
	
	/**
	 * 还款状态：
	 *   0:已还完1:未还完  2： 已转让
	 */
	public static Map<String,String> PLAN_STATUS  = new TreeMap<String,String>();
	static{
		PLAN_STATUS.put("0","已还完");
		PLAN_STATUS.put("1","未还完");
		PLAN_STATUS.put("2","已转让");
	}
	
	/**
	 *   状态：1、审核，2投标中，3满标，4还款中，5已还完，6网站垫付，7坏账，8流标
	 */
	public static Map<String,String> PRODUCT_STATUS  = new TreeMap<String,String>();
	static{
		PRODUCT_STATUS.put("1","待审核");
		PRODUCT_STATUS.put("2","投标中");
		PRODUCT_STATUS.put("3","满标");
		PRODUCT_STATUS.put("4","还款中");
		PRODUCT_STATUS.put("5","已还完");
		PRODUCT_STATUS.put("6","网站垫付");
		PRODUCT_STATUS.put("7","坏帐");
		PRODUCT_STATUS.put("8","流标");
	}
	
	/**
	 *  理财产品投标 状态  是否通过：0:待处理 1:通过，2:失败'
	 */
	public static Map<String,String> PRODUCTORDER_FLAG  = new TreeMap<String,String>();
	static{
		PRODUCTORDER_FLAG.put("0","待处理");
		PRODUCTORDER_FLAG.put("1","通过");
		PRODUCTORDER_FLAG.put("2","未通过");
	}
	
	/**
	 *  理财产品投标 购买来源类别
	 *  0 余额 1 认证支付 2网银支付
	 */
	public static Map<String,String> PRODUCTORDER_PAYTYPE  = new TreeMap<String,String>();
	static{
		PRODUCTORDER_PAYTYPE.put("0","余额");
		PRODUCTORDER_PAYTYPE.put("1","认证支付");
		PRODUCTORDER_PAYTYPE.put("2","网银支付");
	}
	
	/**
	 *  理财产品 类型 0:活期 1：定期
	 */
	public static Map<String,String> INVESTPRODUCT_TYPE = new TreeMap<String,String>();
	static{
		INVESTPRODUCT_TYPE.put("0","活期");
		INVESTPRODUCT_TYPE.put("1","定期");
	}
	/**
	 * 合同类别 0：理财产品 1：普通标  2：债权
	 */
	public static Map<String,String> LAWTYPE = new TreeMap<String,String>();
	static{
		LAWTYPE.put("0", "理财产品");
		LAWTYPE.put("1", "普通标");
		LAWTYPE.put("2", "债权");
	}
	
	/**
	 * 类型 0:其他, 1: 注册送的本金
	 */
	public static Map<String,String> TYPE = new TreeMap<String,String>();
	static{
		TYPE.put("0", "其他");
		TYPE.put("1", "注册");
		TYPE.put("2", "抽奖");
	}
	
	/**
	 * 类型  0、待处理；1、处理成功；2、处理失败
	 */
	public static Map<String,String> INVESTDUMMYPRINCIPLE_STATUS = new TreeMap<String,String>();
	static{
		INVESTDUMMYPRINCIPLE_STATUS.put("0", "待处理");
		INVESTDUMMYPRINCIPLE_STATUS.put("1", "处理成功");
		INVESTDUMMYPRINCIPLE_STATUS.put("2", "处理失败");
	}
	
	/**
	 * 类型 0其他，1提成
	 */
	public static Map<String,String> REWARDS_TYPE = new TreeMap<String,String>();
	static{
		REWARDS_TYPE.put("0", "其他");
		REWARDS_TYPE.put("1", "提成");
		REWARDS_TYPE.put("2", "抽奖");
	}
	
	/**
	 * 类型  0、待处理；1、处理成功；2、处理失败
	 */
	public static Map<String,String> REWARDS_STATUS = new TreeMap<String,String>();
	static{
		REWARDS_STATUS.put("0", "待处理");
		REWARDS_STATUS.put("1", "处理成功");
		REWARDS_STATUS.put("2", "处理失败");
	}
	
	/**
	 * 证件类型 0 身份证 1其他
	 */
	public static Map<String,String> ID_TYPE = new TreeMap<String,String>();
	static{
		ID_TYPE.put("0", "身份证");
		ID_TYPE.put("1", "其他");
	}
	
	/**
	 * 银行卡类型(2-储蓄卡 3-信用卡)
	 */
	public static Map<String,String> CARD_TYPE = new TreeMap<String,String>();
	static{
		CARD_TYPE.put("2", "储蓄卡");
		CARD_TYPE.put("3", "信用卡");
	}
	
	/**
	 * 对公对私标志 0-对私1 –对公
	 */
	public static Map<String,String> CARD_FLAG = new TreeMap<String,String>();
	static{
		CARD_FLAG.put("0", "对私");
		CARD_FLAG.put("1", "对公");
	}
	
	/**
	 * 连连交易状态 交易状态(0-成功 5-已退款==失败)
	 */
	public static Map<String,String> TRADE_STATUS = new TreeMap<String,String>();
	static{
		TRADE_STATUS.put("0", "支付成功");
		TRADE_STATUS.put("5", "支付失败");
	}
	
	/**
	 * 0：匹配成功 1：匹配失败  2：可匹配 3:失效
	 */
	public static Map<String,String> LOAN_INVESTPRODUCT_ORDER_STATUS = new TreeMap<String,String>();
	static{
		LOAN_INVESTPRODUCT_ORDER_STATUS.put("0", "匹配成功");
		LOAN_INVESTPRODUCT_ORDER_STATUS.put("1", "匹配失败");
		LOAN_INVESTPRODUCT_ORDER_STATUS.put("2", "可匹配");
		LOAN_INVESTPRODUCT_ORDER_STATUS.put("3", "失效");
	}
	
	/**
	 * 红包类型 1：注册 2：投资
	 */
	public static Map<String,String> MONEY_TYPE = new TreeMap<String,String>();
	static{
		MONEY_TYPE.put("1", "注册");
		MONEY_TYPE.put("2", "投资");
	}
	
	/**
	 * 红包使用状态 0未使用成功 1使用成功
	 */
	public static Map<String,String> CASHGIFT_STATUS = new TreeMap<String,String>();
	static{
		CASHGIFT_STATUS.put("0", "使用失败");
		CASHGIFT_STATUS.put("1", "使用成功");
	}
	
	/**
	 * 红包抵扣金额计算类型：1按投资额比例抵扣 2按之前利息抵扣
	 */
	public static Map<String,String> CAL_TYPE = new TreeMap<String,String>();
	static{
		CAL_TYPE.put("1", "比例抵扣");
		CAL_TYPE.put("2", "利息抵扣");
	}
	/**
	 * 0=正常 2=维护中（参见连连支持银行卡银行状态）
	 */
	public static Map<String,String> PAYBANK_STATUS = new TreeMap<String,String>();
	static{
		PAYBANK_STATUS.put("0", "正常");
		PAYBANK_STATUS.put("2", "维护中");
	}
}
