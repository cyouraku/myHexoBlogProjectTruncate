<!doctype html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-XSS-Protection" content="0">
    <style type="text/css">
      table {
        border-collapse: collapse;
      }
      table th, table td {
        border: solid 1px black;
        /*実線 1px 黒*/
        text-align: center;
      }
    </style>
  </head>
  <body>
  <!-- <body onLoad="document.forms[0].send.click();document.forms[0].send.hide()"> -->
<!--   <form id="form0" name="sendAttributes" action="http://localhost1:8081/api/1/medy?rl=http://localhost:8081/top/" method="POST"> -->
  <form id="form0" name="sendAttributes" action="http://localhost:8081/api/1/medy?rl=http%3A%2F%2Flocalhost%3A8081%2F" method="POST">
<!--   <form id="form0" name="sendAttributes" action="http://localhost:9112/d2puser" method="POST"> -->
<!--   <form id="form0" name="sendAttributes" action="http://localhost:8081/api/1/medy?rl=http://localhost:8081/top/" method="POST"> -->
<ul>
  <li><textarea name="data" rows="30" cols="150">
  {"status":0,"md":"2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232","responseData":{"attributes":{"BirthDate":"1970-01-01T00:00:00+09:00","ConfirmationLevel":"0","DoctorLicenseRegistYear":"5","FacilityNameAbbr":"テスト病院 AAA","FacilityPrefecture":"東京都","FamilyName":"テスト","FamilyNameKana":"テスト","Gender":"男性","GivenName":"太郎","GivenNameKana":"タロウ","Job":"0","MDBFacilityCode":"003630276","MailAddress":"sample@sample.com","OldSchool":"テスト大学　医学部","RawMailAddresses":"sample@sample.com","UserName":"testmedy001","Workplace":"テスト病院"},"claimedId":"dao44cfcwpyl","contractId":"24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e"}}
  </textarea></li>
  <li><button name=""  type="submit" form="form0" value="Submit">Submit</button><li>
</ul>
  </form>
<!--   <button name="" type="submit" form="form0" value="Submit">Submit</button> -->
  <table>
      <tbody>
        <tr>
          <th>user_id</th>
          <th>login_id(claimedId)</th>
          <th></th>
        </tr>
        <tr>
          <td>0</td>
          <td>test1234</td>
          <td>Optin</td>
        </tr>
        <tr>
          <td>100000163</td>
          <td>saku4cfcwuqq</td>
          <td>Optin</td>
        </tr>
        <tr>
          <td>100000172</td>
          <td>eres4cfcwzop</td>
          <td>プロフィール設定</td>
        </tr>
        <tr>
          <td>100000192</td>
          <td>dao44cfcwpyl</td>
          <td>Top</td>
        </tr>
        <tr>
          <td>100000200</td>
          <td>s7zp4cfco2a2</td>
          <td>Top</td>
        </tr>
        <tr>
          <td>100000640</td>
          <td>v7574cftv1v3</td>
          <td>Top</td>
        </tr>
      </tbody>
    </table>
</html>