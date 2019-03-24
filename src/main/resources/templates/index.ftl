<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>


<table>
<div id="resultTable" >
	<table>
		<thead>
			<th>Client Id</th>
			<th>Transaction Type</th>
			<th>Transaction Date</th>
			<th>Priority</th>
			<th>Processing Fee</th>
		</thead>
		<tbody>
		
			<#list reports as report>
			<tr>
					<td>${report.clientId}</td>
					<td>${report.transactionType}</td>
					<td>${report.transactionDate}</td>
					<td>${report.priority}</td>
					<td>${report.processingFee}</td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>
</body>
</html>