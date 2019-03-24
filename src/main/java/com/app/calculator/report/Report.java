package com.app.calculator.report;

public class Report {
	
	private String clientId;
	private String transactionType;
	private String transactionDate;
	private String priority;
	private double processingFee;
	
	public Report() {
	}
	

	public Report(String clientId, String transactionType, String transactionDate, String priority, double processingFee) {
		super();
		this.clientId = clientId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.priority = priority;
		this.processingFee = processingFee;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public double getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}
	
	

}
