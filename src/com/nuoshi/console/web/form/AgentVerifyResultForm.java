package com.nuoshi.console.web.form;

import com.nuoshi.console.common.Globals;

public class AgentVerifyResultForm {
	private Integer agentId;
	private Integer headResult = Globals.AUDIT_RESULT_PASS;
	private String headMsg;
	private Integer idCardResult = Globals.AUDIT_RESULT_PASS;
	private String idCardMsg;
	private Integer nameCardResult = Globals.AUDIT_RESULT_PASS;
	private String nameCardMsg;

	public String getHeadMsg() {
		return headMsg;
	}

	public void setHeadMsg(String headMsg) {
		this.headMsg = headMsg;
	}

	public String getIdCardMsg() {
		return idCardMsg;
	}

	public void setIdCardMsg(String idCardMsg) {
		this.idCardMsg = idCardMsg;
	}

	public String getNameCardMsg() {
		return nameCardMsg;
	}

	public void setNameCardMsg(String nameCardMsg) {
		this.nameCardMsg = nameCardMsg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * @Author dongyj
	 */
	@Override
	public boolean equals(Object obj) {
		AgentVerifyResultForm agentVerifyResultForm = (AgentVerifyResultForm) obj;
		if (agentVerifyResultForm.getAgentId().intValue() == this.agentId) {
			return true;
		}
		return false;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public void setHeadResult(Integer headResult) {
		this.headResult = headResult;
	}

	public void setIdCardResult(Integer idCardResult) {
		this.idCardResult = idCardResult;
	}

	public void setNameCardResult(Integer nameCardResult) {
		this.nameCardResult = nameCardResult;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public Integer getHeadResult() {
		return headResult;
	}

	public Integer getIdCardResult() {
		return idCardResult;
	}

	public Integer getNameCardResult() {
		return nameCardResult;
	}

}
