<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><meta http-equiv="X-XSS-Protection" content="0">
</head>
<body onLoad="document.forms[0].send.click();document.forms[0].send.hide()">
<!-- <body onLoad="document.forms[0].send.click()"> -->
<!-- <form name="sendAttributes" action="http://192.168.56.1:8081/api/1/medy?rl=http%3A%2F%2F192.168.56.1%3A8081%2F" method="POST"> -->
<form name="sendAttributes" action="http://localhost:8081/api/1/medy?rl=http%3A%2F%2Flocalhost%3A8081%2F" method="POST">
<!-- <input type="hidden" -->
<!--  value="%7B%22md%22%3A%222324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232%22%2C%22messageDigest%22%3A%222324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232%22%2C%22responseData%22%3A%7B%22attributes%22%3A%7B%22birthDate%22%3A%221970-01-01T00%3A00%3A00%2B09%3A00%22%2C%22confirmationLevel%22%3A%220%22%2C%22doctorLicenseRegistYear%22%3A%225%22%2C%22facilityFullName%22%3Anull%2C%22facilityMunicipalityCode%22%3Anull%2C%22facilityNameAbbr%22%3A%22%E3%83%86%E3%82%B9%E3%83%88%E7%97%85%E9%99%A2+AAA%22%2C%22facilityPrefecture%22%3A%22%E6%9D%B1%E4%BA%AC%E9%83%BD%22%2C%22facilityPrefectureCode%22%3Anull%2C%22familyName%22%3A%22%E3%83%86%E3%82%B9%E3%83%88%22%2C%22familyNameKana%22%3A%22%E3%83%86%E3%82%B9%E3%83%88%22%2C%22gender%22%3A%22%E7%94%B7%E6%80%A7%22%2C%22givenName%22%3A%22%E5%A4%AA%E9%83%8E%22%2C%22givenNameKana%22%3A%22%E3%82%BF%E3%83%AD%E3%82%A6%22%2C%22job%22%3A%220%22%2C%22mailAddress%22%3A%22sample%40sample.com%22%2C%22mdbFacilityCode%22%3A%22003630276%22%2C%22mdbPersonalCode%22%3Anull%2C%22mdbService%22%3Anull%2C%22mdbServiceCategory%22%3Anull%2C%22oldSchool%22%3A%22%E3%83%86%E3%82%B9%E3%83%88%E5%A4%A7%E5%AD%A6%E3%80%80%E5%8C%BB%E5%AD%A6%E9%83%A8%22%2C%22rawMailAddresses%22%3A%22sample%40sample.com%22%2C%22userName%22%3A%22testmedy001%22%2C%22workingDepartment%22%3Anull%2C%22workplace%22%3A%22%E3%83%86%E3%82%B9%E3%83%88%E7%97%85%E9%99%A2%22%7D%2C%22claimedId%22%3A%22saku4cfcwuqq%22%2C%22contractId%22%3A%2224f1ce0d-0bb1-46d7-a6b8-20fc56548c7e%22%7D%2C%22status%22%3A%220%22%7D" -->
<!--  name="data"> -->
<input type="hidden"
 value="{&quot;md&quot;:&quot;2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232&quot;,&quot;responseData&quot;:{&quot;attributes&quot;:{&quot;BirthDate&quot;:&quot;1970-01-01T00:00:00+09:00&quot;,&quot;ConfirmationLevel&quot;:&quot;0&quot;,&quot;DoctorLicenseRegistYear&quot;:&quot;5&quot;,&quot;FacilityFullName&quot;:null,&quot;FacilityMunicipalityCode&quot;:null,&quot;FacilityNameAbbr&quot;:&quot;テスト病院 AAA&quot;,&quot;FacilityPrefecture&quot;:&quot;東京都&quot;,&quot;FacilityPrefectureCode&quot;:null,&quot;FamilyName&quot;:&quot;テスト&quot;,&quot;FamilyNameKana&quot;:&quot;テスト&quot;,&quot;Gender&quot;:&quot;男性&quot;,&quot;GivenName&quot;:&quot;太郎&quot;,&quot;GivenNameKana&quot;:&quot;タロウ&quot;,&quot;Job&quot;:&quot;0&quot;,&quot;MDBFacilityCode&quot;:&quot;003630276&quot;,&quot;MDBPersonalCode&quot;:null,&quot;MDBService&quot;:null,&quot;MDBServiceCategory&quot;:null,&quot;MailAddress&quot;:&quot;sample@sample.com&quot;,&quot;OldSchool&quot;:&quot;テスト大学　医学部&quot;,&quot;RawMailAddresses&quot;:&quot;sample@sample.com&quot;,&quot;UserName&quot;:&quot;testmedy001&quot;,&quot;WorkingDepartment&quot;:null,&quot;Workplace&quot;:&quot;テスト病院&quot;},&quot;claimedId&quot;:&quot;saku4cfcwuqq&quot;,&quot;contractId&quot;:&quot;24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e&quot;},&quot;status&quot;:0}"
 name="data">
<!-- <input type="hidden" -->
<%--  value= "${message}" --%>
<!--  name="data"> -->
<script>
document.write('<input type="submit" value="SEND" name="send" style="visibility:hidden">');
</script>
<noscript>
<input type="submit" value="SEND" name="send" ></noscript>
</form>
</body>
</html>
