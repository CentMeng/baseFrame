package com.core.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误集合
 * @author mengxc （csdn@vip.163.com）
 *
 */
public class ErrorInfo {


	private static Map<String,String> msg = new HashMap<String,String>();

	static{
		msg.put("MOBILE_USED", "手机号码已注册");
		msg.put("GROUP_NOT_EXIST", "企业码不存在");
		msg.put("USER_NOT_INVITED", "用户未被邀请");
		msg.put("LOGINNAME_NOT_MATCH", "手机号与登录名不匹配");
		msg.put("MOBILE_EXISTS", "该手机号已被注册");
		msg.put("MOBILE_INVALID", "请输入正确的手机号");
		msg.put("INVALID_MOBILE_UN_REGISTER", "未知手机号");
		msg.put("LOGINNAME_INVALID","用户名必须由数字和字母组成");
		msg.put("MOBILE_CAPTCHA_INVALID", "验证码错误");
		msg.put("MOBILE_SMS_CAPTCHA_ALREADY_SEND", "验证码已发送");
		msg.put("USER_NOT_SIGN_NO_PWD_AGREEMENT", "用户未签订无密投资协议");
		msg.put("USER_AMOUNT_INSUFFICIENT_AND_INVEST_RETURN", "可用余额不足,投资已返还");
		msg.put("USER_AMOUNT_INSUFFICIENT_AND_PREPARE_CONTACT", "可用金额不足,请联系客服");
		msg.put("USER_DEPOSIT_FAILED", "用户充值失败");
		msg.put("USER_WITHDRAW_EXCEED_LIMIT", "用户提现超出限额");
		msg.put("USER_WITHDRAW_FAILED", "用户提现失败");
		msg.put("USER_NOT_BIND_BANK_CARD", "用户未绑定银行卡");
		msg.put("USER_BANK_ACCOUNT_NOT_FOUND", "用户银行卡未找到");
		msg.put("UMP_TRANSFER_ENT2SYS_FAILED", "企业向商户转账失败");
		msg.put("UMP_TENDER_NOT_FOUND", "未找到联动优势标的账户");
		msg.put("UMP_CREATE_ACCOUNT_FAILED", "联动优势账号创建失败");
		msg.put("UMP_ACCOUNT_NOT_FOUND", "未开通联动优势账户");
		msg.put("UMP_AGREEMENT_NOT_BIND", "未绑定联动优势账户相关协议");
		msg.put("UMP_AGREEMENT_INSTANT_NOT_BIND", "未绑定联动优势无密快捷协议");
		msg.put("UMP_AGREEMENT_DEBIT_NOT_BIND", "未绑定联动优势借记卡快捷协议");
		msg.put("UMP_RET_MSG", "请求联动优势返回错误信息");
	    msg.put("CHINAPNR_AUTOTENDER_OPEN_FAILED", "开通汇付自动投标失败");
		msg.put("CHINAPNR_AUTOTENDER_CLOSE_FAILED", "关闭汇付自动投标失败");
		msg.put("CHINAPNR_BANK_NOT_SUPPORT_FAST", "汇付暂不支持此银行卡快捷");
		msg.put("PAYMENT_ACCOUNT_NOT_FOUND", "未开通第三方托管账户");
		msg.put("FUND_RECORD_RECHARGE_BY_ORDERID_NOT_FOUND", "根据订单编号未找到相关资金记录");
		msg.put("LOAN_STATUS_FINISHED_OR_CANCEL", "标的已经结束或关闭");
		msg.put("LOAN_STATUS_FINISHED_AND_INVEST_RETURN", "投资失败,已满标,投资已返还");
		msg.put("LOAN_STATUS_FINISHED_AND_PREPARE_CONTACT", "已满标,请联系客服");
		msg.put("LOAN_INSUFFICIENT_AND_INVEST_RETURN", "可用余额不足,投资已返还");
		msg.put("LOAN_INSUFFICIENT_AND_PREPARE_CONTACT", "可用金额不足,请联系客服");
		msg.put("LOAN_BALANCE_ERROR", "标的可投金额错误");
		msg.put("CROWD_FUNDING_NOT_FOUND", "众筹项目未找到");
		msg.put("CROWD_FUNDING_STATUS_NOT_OPENED_OR_FINISHED", "众筹项目尚未开始或已结束");
		msg.put("CROWD_FUNDING_STATUS_FINISHED", "众筹已满");
		msg.put("ORDER_HAS_PROCESSED", "订单已经被处理");
		msg.put("AMOUNT_ERROR", "投标金额错误");
		msg.put("INVALID_PARAMS", "无效的参数");
		msg.put("INVALID_CAPTCHA", "无效的验证码");
		msg.put("INVALID_MOBILE", "无效的手机号");
		msg.put("INVALID_MOBILE_CAPTCHA", "无效的手机验证码");
		msg.put("INVALID_UMP_TENDER_AMOUNT", "无效的投标金额联动优势");
		msg.put("INVALID_AMOUNT", "无效的金额");
		msg.put("INVALID_IDNUMBER", "无效的身份证号");
		msg.put("INVALID_SESSION", "用户未登录或登录失效");
		msg.put("AGREEMENT_NOT_SIGNED", "未签署相关协议");
		msg.put("MOBILE_AND_NAME_NOT_MATCH", "手机号与真实姓名不匹配");
		msg.put("MOBILE_CAPTCHA_ALREADY_SENT", "手机验证码已经发送");
		msg.put("MOBILE_CAPTCHA_EXPIRED", "手机验证码已经过期");
		msg.put("MOBILE_ALREADY_EXISTED", "手机号已经存在");
		msg.put("LOGINNAME_ALREADY_EXISTED", "登录名已经存在");
		msg.put("IDNUMBER_ALREADY_EXISTED", "身份证号已经存在");
		msg.put("IDNUMBER_AND_NAME_NOT_MATCH", "身份证号与真实姓名不匹配");
		msg.put("MSG_RECEIVER_EMPTY", "消息接受方为空");
		msg.put("MSG_TITLE_EMPTY", "消息标题为空");
		msg.put("MSG_CONTENT_EMPTY", "消息内容为空");
		msg.put("MSG_SEND_FAILED", "消息发送失败");
		msg.put("TAG_NOT_FOUND", "标签未找到");
		msg.put("BANK_NOT_FOUND", "银行卡未找到");
		msg.put("UNKNOWN", "未知错误");
		msg.put("UNKNOWN_AND_INVEST_RETURN", "未知错误,投资已返还");
		msg.put("UNKNOWN_AND_PREPARE_CONTACT", "未知错误,请联系客服");
		msg.put("USER_NOT_FOUND", "用户名不存在");
		msg.put("PASSWORD_RESET_FAILURE", "更改密码失败");
		msg.put("PASSWORD_ERROR", "登录密码错误");
		msg.put("CREDITASSIGN_ERROR", "债权转让错误");
		msg.put("GROUP_NOT_EXIST", "没有对应的组存在");
		msg.put("USER_NOT_INVITED", "非受邀用户");
		 /*************************************************
	     * 基金
	     *************************************************/
	    msg.put("INVESTMENT_FUND_MONETARY_ACCOUNT_NOT_FOUND", "用户未开通货币型基金账户");
		msg.put("INVESTMENT_FUND_STOCK_ACCOUNT_NOT_FOUND", "用户未开通股票型基金账户");
		msg.put("INVESTMENT_FUND_SERVICE_REQUEST_FAILED", "基金服务请求错误");
	}

	public static String getMsg(String message){
		return msg.get(message) == null? msg.get("UNKNOWN") : msg.get(message);
	}
}
