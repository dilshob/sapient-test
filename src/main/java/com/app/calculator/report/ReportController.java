package com.app.calculator.report;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.calculator.manager.CsvReportManager;

@Controller
public class ReportController {

	@Autowired
	private CsvReportManager manager;

	@GetMapping(value = "/report")
	public String getReport(Model model) throws FileNotFoundException {
		model.addAttribute("reports", manager.generateReport());
		return "index";
	}

}
