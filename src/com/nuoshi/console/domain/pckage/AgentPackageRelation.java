package com.nuoshi.console.domain.pckage;

public class AgentPackageRelation {
	public AgentPackageRelation() {}
	
	public AgentPackageRelation(int agentId, int packageId,float price) {
		this.agentId = agentId;
		this.packageId = packageId;
		this.price = price;
	}
	
	private int agentId;
	private int packageId;
	private float price;
	
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
