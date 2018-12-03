package sample.medyPass.entity;

import net.arnx.jsonic.JSONHint;

public class Attributes {
	/** ユーザID */
	@JSONHint(name="UserName")
	public String userName = null;

	/** 性 */
	@JSONHint(name="FamilyName")
	public String familyName = null;
	/** 名前 */
	@JSONHint(name="GivenName")
	public String givenName = null;
	/** 性(カナ) */
	@JSONHint(name="FamilyNameKana")
	public String familyNameKana = null;
	/** 名前(カナ) */
	@JSONHint(name="GivenNameKana")
	public String givenNameKana = null;

	/** 勤務先/学校名 */
	@JSONHint(name="Workplace")
	public String workplace = null;
	/** 所属部署(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="WorkingDepartment")
	public String workingDepartment = null;

	/** 勤務先(「医師」のアカウントみ取得可) */
	@JSONHint(name="MDBFacilityCode")
	public String mdbFacilityCode = null;

	/** 出身校(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="OldSchool")
	public String oldSchool = null;

	/** MDB 個人コード(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="MDBPersonalCode")
	public String mdbPersonalCode = null;
	/** MDB 診療科(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="MDBService")
	public String mdbService = null;
	/** MDB 診療科 大項目(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="MDBServiceCategory")
	public String mdbServiceCategory = null;

	/** 国家 資格取得年(職種「医師」 「薬剤師」のアカウントみ取得可) */
	@JSONHint(name="DoctorLicenseRegistYear")
	public String doctorLicenseRegistYear = null;

	/** 生年月日 */
	@JSONHint(name="BirthDate")
	public String birthDate = null;
	/** 性別 */
	@JSONHint(name="Gender")
	public String gender = null;

	/** 職種 */
	@JSONHint(name="Job")
	public String job = null;

	/** medyメールアドレス */
	@JSONHint(name="MailAddress")
	public String mailAddress = null;
	/** メールアドレス */
	@JSONHint(name="RawMailAddresses")
	public String rawMailAddresses = null;

	/** 本人確認レベル */
	@JSONHint(name="ConfirmationLevel")
	public String confirmationLevel = null;

	/** 勤務先 DCF DCF施設正式名(職種「医師」のアカウントみ取得可)  */
	@JSONHint(name="FacilityFullName")
	public String facilityFullName = null;
	/** 勤務先 DCF DCF施設 略式名(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="FacilityNameAbbr")
	public String facilityNameAbbr = null;

	/** 勤務先 都道府県(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="FacilityPrefecture")
	public String facilityPrefecture = null;
	/** 勤務先 都道府県 JIS コード(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="FacilityPrefectureCode")
	public String facilityPrefectureCode = null;

	/** 勤務先 市区町村 JIS コード(職種「医師」のアカウントみ取得可) */
	@JSONHint(name="FacilityMunicipalityCode")
	public String facilityMunicipalityCode = null;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}

	public void setGivenNameKana(String givenNameKana) {
		this.givenNameKana = givenNameKana;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public void setWorkingDepartment(String workingDepartment) {
		this.workingDepartment = workingDepartment;
	}

	public void setMdbFacilityCode(String mdbFacilityCode) {
		this.mdbFacilityCode = mdbFacilityCode;
	}

	public void setOldSchool(String oldSchool) {
		this.oldSchool = oldSchool;
	}

	public void setMdbPersonalCode(String mdbPersonalCode) {
		this.mdbPersonalCode = mdbPersonalCode;
	}

	public void setMdbService(String mdbService) {
		this.mdbService = mdbService;
	}

	public void setMdbServiceCategory(String mdbServiceCategory) {
		this.mdbServiceCategory = mdbServiceCategory;
	}

	public void setDoctorLicenseRegistYear(String doctorLicenseRegistYear) {
		this.doctorLicenseRegistYear = doctorLicenseRegistYear;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public void setRawMailAddresses(String rawMailAddresses) {
		this.rawMailAddresses = rawMailAddresses;
	}

	public void setConfirmationLevel(String confirmationLevel) {
		this.confirmationLevel = confirmationLevel;
	}

	public void setFacilityFullName(String facilityFullName) {
		this.facilityFullName = facilityFullName;
	}

	public void setFacilityNameAbbr(String facilityNameAbbr) {
		this.facilityNameAbbr = facilityNameAbbr;
	}

	public void setFacilityPrefecture(String facilityPrefecture) {
		this.facilityPrefecture = facilityPrefecture;
	}

	public void setFacilityPrefectureCode(String facilityPrefectureCode) {
		this.facilityPrefectureCode = facilityPrefectureCode;
	}

	public void setFacilityMunicipalityCode(String facilityMunicipalityCode) {
		this.facilityMunicipalityCode = facilityMunicipalityCode;
	}
}
