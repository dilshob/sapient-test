package com.app.calculator.manager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.app.calculator.report.Report;
import com.app.calculator.report.Transaction;
import com.app.calculator.report.TransactionRepository;
import com.app.calculator.util.CsvUtils;

@Component
public class CsvReportManager implements ReportManager {

	@Autowired
	private CsvUtils csvUtils;

	@Autowired
	private TransactionRepository repository;
	
	@Override
	public List<Report> generateReport() throws FileNotFoundException {
		List<Transaction> transactions = csvUtils.generateReport(ResourceUtils.getFile("classpath:input.csv").getPath());
		//List<Transaction> savedTransactions =repository.saveAll(transactions);
		return sortReportList(processTransactions(transactions));
		
	}
	
	private List<Report> sortReportList(List<Report> reports){
		return reports.stream().sorted(Comparator.comparing(Report::getClientId)
                .thenComparing(Report::getTransactionType)
                .thenComparing(Report::getTransactionDate)
                .thenComparing(Report::getPriority)
               ).collect(Collectors.toList());
	}
	
	private List<Report> processTransactions(List<Transaction> transactions) {
		
		List<Report> reports = new ArrayList<>();
		
		Map<String, List<Transaction>> groupByClientId = transactions.stream().collect(Collectors.groupingBy(Transaction::getClientId));
		for (Map.Entry<String, List<Transaction>> client : groupByClientId.entrySet()) {
		    System.out.println(client.getKey() + "/" + client.getValue());
		    
		    List<Transaction> clientTransations = client.getValue();
		    Map<String, List<Transaction>> groupByDate = clientTransations.stream().collect(Collectors.groupingBy(Transaction::getTransactionDate));
		    for (Map.Entry<String, List<Transaction>> clientDate : groupByDate.entrySet()) {
			    System.out.println(clientDate.getKey() + "/" + clientDate.getValue());
			    
			    
			    
			    List<Transaction> clientDateTransaction = clientDate.getValue();
			    Map<String, List<Transaction>> groupedBySecurity = clientDateTransaction.stream().collect(Collectors.groupingBy(Transaction::getSecurityId));
			    for (Map.Entry<String, List<Transaction>> clientDateSecurity : groupedBySecurity.entrySet()) {
				    System.out.println(clientDateSecurity.getKey() + "/" + clientDateSecurity.getValue());
				    
				    List<Transaction> dayTrnsForSecutity = clientDateSecurity.getValue();
				    
				    if(dayTrnsForSecutity.size() == 1) {
				    	getNormalTransactionReport(reports, dayTrnsForSecutity.get(0), client.getKey(), clientDate.getKey());
				    } else if(dayTrnsForSecutity.size() == 2 && 
				    		(dayTrnsForSecutity.get(0).getTransactionType().equalsIgnoreCase("buy") && dayTrnsForSecutity.get(1).getTransactionType().equalsIgnoreCase("sell") ||
				    		 dayTrnsForSecutity.get(0).getTransactionType().equalsIgnoreCase("sell") && dayTrnsForSecutity.get(1).getTransactionType().equalsIgnoreCase("buy"))) {
				    	reports.add(getReportOfTransaction(client.getKey(),  dayTrnsForSecutity.get(0).getTransactionType(),clientDate.getKey(),dayTrnsForSecutity.get(0).getPriorityFlag(),10));
				    	reports.add(getReportOfTransaction(client.getKey(),  dayTrnsForSecutity.get(1).getTransactionType(),clientDate.getKey(),dayTrnsForSecutity.get(1).getPriorityFlag(),10));
				    	//not handling more then one intraday transaction ( 4 transation for same security)
				    } else {
				    	dayTrnsForSecutity.forEach(transaction -> {
				    		getNormalTransactionReport(reports, transaction, client.getKey(), clientDate.getKey());
				    		
				    	});
				    }
			    }
		    }
		}
		return reports;
	}
	
	private void getNormalTransactionReport(List<Report> reports,Transaction transaction, String clientId, String trnsDate) {
		if("Y".equalsIgnoreCase(transaction.getPriorityFlag())) {
    		reports.add(getReportOfTransaction(clientId, transaction.getTransactionType(),trnsDate,transaction.getPriorityFlag(),500));
    	} else {
    		if("sell".equalsIgnoreCase(transaction.getTransactionType()) ||
    				"withdraw".equalsIgnoreCase(transaction.getTransactionType().trim())) {
    			reports.add(getReportOfTransaction(clientId, transaction.getTransactionType(),trnsDate,transaction.getPriorityFlag(),100));
    		} else if("buy".equalsIgnoreCase(transaction.getTransactionType()) ||
    				"deposit".equalsIgnoreCase(transaction.getTransactionType())) {
    			reports.add(getReportOfTransaction(clientId, transaction.getTransactionType(),trnsDate,transaction.getPriorityFlag(),50));
    		}
    	}
	}
	
	private Report getReportOfTransaction(String clientId, String reportType, String trnsDate, String priority, double fee) {
		Report report = new Report();
		report.setClientId(clientId);
		report.setTransactionType(reportType);
		report.setTransactionDate(trnsDate);
		report.setPriority(priority);
		report.setProcessingFee(fee);
		return report;
	}
	
	//added to check the grouping
	public Map<String, Map<String, Map<String, List<Transaction>>>> getGroupedList() throws FileNotFoundException {
		List<Transaction> transactions = csvUtils.generateReport(ResourceUtils.getFile("classpath:input.csv").getPath());
		return transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getClientId,
								Collectors.groupingBy(Transaction::getTransactionDate,
										Collectors.groupingBy(Transaction::getSecurityId))));
	}
	
}
