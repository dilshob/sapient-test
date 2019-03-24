package com.app.calculator.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.calculator.report.Transaction;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class CsvUtils {
	private static final String[] CSV_HEADER = { "extTranctionId","clientId","securityId","transactionType"
			,"transactionDate","marketValue","priorityFlag"};

	public List<Transaction> generateReport(String file) {
		List<Transaction> transactions = new ArrayList<>();
		BufferedReader fileReader = null;
		CsvToBean<Transaction> csvToBean = null;

		try {
			fileReader = new BufferedReader(new FileReader(file));

			ColumnPositionMappingStrategy<Transaction> mappingStrategy = new ColumnPositionMappingStrategy<>();

			mappingStrategy.setType(Transaction.class);
			mappingStrategy.setColumnMapping(CSV_HEADER);

			csvToBean = new CsvToBeanBuilder<Transaction>(fileReader).withMappingStrategy(mappingStrategy)
					.withSkipLines(1).withIgnoreLeadingWhiteSpace(true).build();

			transactions = csvToBean.parse();

			for (Transaction transaction : transactions) {
				System.out.println(transaction);
			}
		} catch (Exception e) {
			System.out.println("Exception while reading CSV");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Exception while closing fileReader");
				e.printStackTrace();
			}
		}
		return transactions;
	}
}