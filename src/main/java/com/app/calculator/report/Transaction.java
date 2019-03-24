package com.app.calculator.report;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Transaction {
	
	private String extTranctionId;
	private String clientId;
	private String securityId;
	private String transactionType;
	private String transactionDate;
	private double marketValue;
	private String priorityFlag;
	
	@Id
	private long id;
	
	public Transaction() {
	}
	
	public Transaction(String extTranctionId, String clientId, String securityId, String transactionType,
			String transactionDate, double marketValue, String priorityFlag) {
		this.extTranctionId = extTranctionId;
		this.clientId = clientId;
		this.securityId = securityId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.marketValue = marketValue;
		this.priorityFlag = priorityFlag;
	}
	public String getExtTranctionId() {
		return extTranctionId;
	}
	public void setExtTranctionId(String extTranctionId) {
		this.extTranctionId = extTranctionId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	public String getPriorityFlag() {
		return priorityFlag;
	}
	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}
	
	@Override
	public String toString() {
        try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "empty";
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
