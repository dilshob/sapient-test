package com.app.calculator.manager;

import java.io.FileNotFoundException;
import java.util.List;

import com.app.calculator.report.Report;

public interface ReportManager {
	List<Report> generateReport() throws FileNotFoundException;
}
