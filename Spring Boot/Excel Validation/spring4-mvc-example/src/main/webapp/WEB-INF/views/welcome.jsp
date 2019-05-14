<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
body {
	background-color: #FFFFFF;
}
</style>
<title>Displaying Excel Sheets</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel='stylesheet' href='<c:url value="/resources/css/style.css" />'
	type='text/css' media='all' />
</head>
<body>
	<h1>Timesheet Comparison</h1>

	<h4>Upload Status</h4>
	<p style="color: green; font-size: 20px;">${message}</p>
	<form method="POST" action="upload" enctype="multipart/form-data">
		<h4>ESA Timesheet :</h4>
		<input type="file" name="files" accept=".xls,.xlsx" required /><br />
		<h4>Fieldglass Timesheet :</h4>
		<input type="file" name="files" accept=".xls,.xlsx" required /><br />
		<br> <input type="submit" value="Upload" />
	</form>
	<br>
	<br>
	<br>
	<br>
	<c:url value="/getSelectedData" var="getSelectedData" />
	<form action="${getSelectedData}" method="POST">
		<c:if test="${not empty esaHeadersList}">
			<h3>ESA Timesheet</h3>
			<select name="selectedHeaderExcelOne" onChange="radio(this.value)" id="selectedHeaderExcelOne" required>
				<c:forEach var="line" items="${esaHeadersList}">
					<option><c:out value="${line}" /></option>
				</c:forEach>
			</select>
		</c:if>
		<br>

		<c:if test="${not empty fieldglassHeadersList}">
			<h3>Fieldglass Timesheet</h3>
			<select name="selectedHeaderExcelTwo" onChange="radiotwo(this.value)" id="selectedHeaderExcelTwo" required>
				<c:forEach var="line" items="${fieldglassHeadersList}">
					<option><c:out value="${line}" /></option>
				</c:forEach>
			</select>
			<br>
			<br>
			Enter Project Id: <input type="text" name="projectId" required>
			<select name="reportType">
			<option>Daily</option>
			<option id="daily" disabled>Weekly</option>
			</select>
			<br>
			<br>
			<br>
			<input type="submit" value="Submit" />
		</c:if>

		<c:if test="${not empty data}">
			<h3>Comparison Result</h3>
			<table style="border: 1px solid black; border-collapse: collapse;">
				<c:forEach items="${data}" var="row">
					<tr>
						<c:forEach items="${row.value}" var="cell">
							<td
								style="border:1px solid black;height:20px;width: 100px;
                        background-color:${cell.bgColor};color:${cell.textColor};
                        font-weight:${cell.textWeight};font-size:${cell.textSize}pt;">
								${cell.content}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<br> <br>
	</form>
	<br>
	<br>
	<br>
	<br>
	<script>
	function radio(value) {
		e=value;
	   if(value=="Time Quantity"){
		   document.getElementById("daily").disabled = false;
	   }
	   else{
		   document.getElementById("daily").disabled = true;
	   }
	}
     
	function radiotwo(value){
		if(value=="Time Sheet Billable Hours"){
			if(document.getElementById("daily").disabled){
				document.getElementById("daily").disabled = true;	
			}else{
	      document.getElementById("daily").disabled = false;
			}
		}
		else{
		      document.getElementById("daily").disabled = true;
		}
	}
	
	</script>
</body>
</html>