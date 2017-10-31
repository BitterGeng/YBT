package com.sinosoft.service.business.ui.CustomerID.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.service.api.PubFun;
import com.sinosoft.service.api.TimeFormat;
import com.sinosoft.service.business.db.dao.LdcustomerDao;
import com.sinosoft.service.business.db.dao.LdcustomeraccountDao;
import com.sinosoft.service.business.db.dao.LdcustomeraddressDao;
import com.sinosoft.service.business.db.dao.LdcustomeridDao;
import com.sinosoft.service.business.ui.CustomerID.bean.Ldcustomeraddress;
import com.sinosoft.service.business.ui.CustomerID.utils.TestWbeSphereMQ;
import com.sinosoft.service.business.ui.parseXML.utils.GlobalInput;
import com.sinosoft.service.business.utils.Base64DecoderS;

@Service
public class CustomerIDService1 {
	private GlobalInput mGlobalInput = new GlobalInput();

	// CustomerIDXML customerIDXML=new CustomerIDXML();

	TestWbeSphereMQ testWbeSphereMQ=new TestWbeSphereMQ();
	private String mOperate;
	private String bankName;
	private String YH;
	private String BX;
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	@Autowired
	private LdcustomeraddressDao ldcustomeraddressDao;
	@Autowired
	private LdcustomerDao ldcustomerDao;
	@Autowired
	private LdcustomeridDao ldcustomeridDao;
	@Autowired
	private LdcustomeraccountDao ldcustomeraccountDao;
	
	
	public static void main(String[] args) throws Exception {
//		dealData();
		String a=TimeFormat.TimeDateFormat2(new Date());
		String currentTime = TimeFormat.getCurrentTime();
		System.out.println("Z"+TimeFormat.TimeDateFormat2(new Date())+"T"+TimeFormat.getCurrentTime()+".000069 +08:00");
	}

	// 解析文件
	public  Map dealData() throws Exception {
		String mOutStr = null;
		try {
			String content = new String();
			StringBuffer sbContent = new StringBuffer();
			sbContent
					.append("ISM Fixed V02.00YNHSBC_YBT                                                       YBT_CN_HUB                                                      ");
			sbContent
					.append("ENQCUSINFO                                                                                                                                                                                                                                              ");
//			sbContent.append("Z2017-10-20T10:20:28.000069 +08:00                                                                                                                                                                                                                                                                              ");
			sbContent.append("Z"+TimeFormat.TimeDateFormat2(new Date())+"T"+TimeFormat.getCurrentTime()+".000069 +08:00"+"                                                                                                                                                                                                                                                                              ");
			sbContent.append("0000007890000000000000001290000004530000002070010101OH_SERVICE_HEADER               00860100000   INTBCNHSBC                                                                  0101000199000199YBT_CN_HUB                                                      ENQCUSINFO                                                      01.0001.00                                                                                                                                                                                                                                                                                                           01000199");
			StringBuffer headContent = new StringBuffer();
			headContent.append(TimeFormat.formatToYYYYMMDD());// 交易日/期
			headContent.append(PubFun.getCurrentTime2()+"  ");// 交易时间
			headContent.append("01");// 银行代码Y
			headContent.append("0000012345");// 网点代码========
			headContent.append("0000003525");// 柜员代码Y
	
			headContent.append("cifquery            ");// 交易类型
			headContent.append("Branch    ");// 渠道代码Y
			headContent.append("00356981231003215630");// /交易流水号
			headContent.append("MELI                ");// 保险公司代码Y
			headContent.append("CNHSBC706009446     ");// CifidY

			System.out.println(content);
			mOutStr = testWbeSphereMQ.sendInfoToCRPT(content);

			System.out.println("下面是接收到的文件+++++++++++++++++++");
			System.out.println(mOutStr + "============");
		} catch (Exception e) {
			throw new Exception(e.getMessage());// 页面提示信息 结束
		}
		
		
		
		
//		FileReader reader = null;
//		BufferedReader buff = null;
//		String lineData = null;
//		String str = "";
//		reader = new FileReader("C:/Users/Admin/Desktop/ISM.txt");
//		buff = new BufferedReader(reader);
//		while((lineData = buff.readLine()) != null){
//			str += lineData;
//		}
//		System.out.println(str);
//		System.out.println(str.length());
		System.out.println("开始解析报文");
		String body = mOutStr.substring(1332);// 文件体
		String Flag = body.substring(0, 12);// 交易结果代码 char(12)
		String Desc = body.substring(12, 132);// 交易结果描述 char(120)
		String FullName = body.substring(132, 202);// 姓名 char(70)
		String FullNameChinese = body.substring(202, 482);// 中文名 char(280)
		FullNameChinese = Base64DecoderS.decode(FullNameChinese.trim());
		String IDType = body.substring(482, 484);// 证件类型 char(2)
		String IDNumber = body.substring(484, 564);// 证件号码 char(80)
		IDNumber = Base64DecoderS.decode(IDNumber.trim());

		String IDExpiryDate = body.substring(564, 572);// 证件有效期至 char(8)
		String DateofBirth = body.substring(572, 580);// 出生日期 char(8)
		String Gender = body.substring(580, 581);// 性别 char(1)
		String Nationality = body.substring(581, 584);// 国籍 char(3)
		String MaritalStatus = body.substring(584, 585);// 婚姻状况 char(1)
		String MobilePhoneNo = body.substring(585, 617);// 手机 char(32)
		
	
		/**
		 * 居住地址信息地址
		 */
		Ldcustomeraddress ldcustomeraddress = new Ldcustomeraddress();
		String residentialaddresstype = body.substring(617, 652); // 地区类型// char(35)
		String residentialprovince = body.substring(652, 687); // 省 char(35)
		String residentialcity = body.substring(687, 722);// 市 char(35)
		String residentialdistrict = body.substring(722, 757);// 区 char(35)
		String residentialdetailedaddress = body.substring(757, 792);// 详细地址
		String residentialpostcode = body.substring(792, 802);// 邮编 char(10)
		ldcustomeraddress.setEresidentialaddresstype(residentialaddresstype.trim());
		ldcustomeraddress.setEresidentialprovince(residentialprovince.trim());
		ldcustomeraddress.setEresidentialcity(residentialcity.trim());
		ldcustomeraddress.setEresidentialdistrict(residentialdistrict.trim());
		ldcustomeraddress.setEresidentialdetailedaddress(residentialdetailedaddress.trim());
		ldcustomeraddress.setResidentialpostcode(residentialpostcode.trim());
		ldcustomeraddress.setCifid("123456");
//		ldcustomeraddress.setLdcustomerno(ldcustomeraddressDao.select());
//		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress);

		// Base64转换
		String residentialaddresstype1 = Base64DecoderS.decode(body.substring(802, 942).trim());
		// String residentialaddresstype1 = body.substring(802, 942); //地区类型
		// char(140)
		String residentialprovince1 = Base64DecoderS.decode(body.substring(942, 1082).trim());
		// String residentialprovince1 = body.substring(942, 1082); //省
		// char(140)
		String residentialcity1 = Base64DecoderS.decode(body.substring(1082, 1222).trim());
		// String residentialcity1 = body.substring(1082, 1222);//市 char(140)
		String residentialdistrict1 = Base64DecoderS.decode(body.substring(1222, 1362).trim());
		// String residentialdistrict1 = body.substring(1222, 1362);//区
		// char(140)
		String residentialdetailedaddress1 = Base64DecoderS.decode(body.substring(1362, 1502).trim());
		// String residentialdetailedaddress1 = body.substring(1362,1502);//详细地址
		// char(140)
		ldcustomeraddress.setResidentialaddresstype(residentialaddresstype1);
		ldcustomeraddress.setResidentialprovince(residentialprovince1);
		ldcustomeraddress.setResidentialcity(residentialcity1);
		ldcustomeraddress.setResidentialdistrict(residentialdistrict1);
		ldcustomeraddress.setResidentialdetailedaddress(residentialdetailedaddress1);
//		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress1);
		
		/**
		 * 联系地址信息
		 */

		String contactaddresstype = body.substring(1502, 1537);// 地区类型 char(35)
		String contactprovince = body.substring(1537, 1572);// 省 char(35)
		String contactcity = body.substring(1572, 1607);// 市 char(35)
		String contactdistrict = body.substring(1607, 1642);// 区 char(35)
		String contactdetailedaddress = body.substring(1642, 1677);// 详细地址// char(35)
		String contactpostcode = body.substring(1677, 1687);// 详细地址 char(10)
		ldcustomeraddress.setEcontactaddresstype(contactaddresstype.trim());
		ldcustomeraddress.setEcontactprovince(contactprovince.trim());
		ldcustomeraddress.setEcontactcity(contactcity.trim());
		ldcustomeraddress.setEcontactdistrict(contactdistrict.trim());
		ldcustomeraddress.setEcontactdetailedaddress(contactdetailedaddress.trim());
		ldcustomeraddress.setContactpostcode(contactpostcode.trim());
		
		// Base64转换
		String contactaddresstype1 = Base64DecoderS.decode(body.substring(1687, 1827).trim());
		// String contactaddresstype1 = body.substring(1687,1827);
		String contactprovince1 = Base64DecoderS.decode(body.substring(1827, 1967).trim());
		// String contactprovince1 = body.substring(1967,2107);
		String contactcity1 = Base64DecoderS.decode(body.substring(1967, 2107).trim());
		// String contactcity1 = body.substring(2107,2247);
		String contactdistrict1 = Base64DecoderS.decode(body.substring(2107, 2247).trim());
		// String contactdistrict1 = body.substring(2247,2387);
		String contactdetailedaddress1 = Base64DecoderS.decode(body.substring(2247, 2387).trim());
		// String contactdetailedaddress1 = body.substring(2387,2527);
		ldcustomeraddress.setContactaddresstype(contactaddresstype1);
		ldcustomeraddress.setContactprovince(contactprovince1);
		ldcustomeraddress.setContactcity(contactcity1);
		ldcustomeraddress.setContactdistrict(contactdistrict1);
		ldcustomeraddress.setContactdetailedaddress(contactdetailedaddress1);
		
		/**
		 * 家庭地址
		 */
		String homeaddresstype = body.substring(2387, 2422);
		String homeprovince = body.substring(2422, 2457);
		String homecity = body.substring(2457, 2492);
		String homedistrict = body.substring(2492, 2527);
		String homedetailedaddress = body.substring(2527, 2562);
		String homepostcode = body.substring(2562, 2572);
		ldcustomeraddress.setEhomeaddresstype(homeaddresstype);
		ldcustomeraddress.setEhomeprovince(homeprovince);
		ldcustomeraddress.setEhomecity(homecity);
		ldcustomeraddress.setEhomedistrict(homedistrict);
		ldcustomeraddress.setEhomedetailedaddress(homedetailedaddress);
		ldcustomeraddress.setHomepostcode(homepostcode);
		
		// Base64转换
		String homeaddresstype1 = Base64DecoderS.decode(body.substring(2572, 2712).trim());
		// String homeaddresstype1 = body.substring(2712,2852);
		String homeprovince1 = Base64DecoderS.decode(body.substring(2712, 2852).trim());
		// String homeprovince1 = body.substring(2852,2992);
		String homecity1 = Base64DecoderS.decode(body.substring(2852, 2992).trim());
		// String homecity1 = body.substring(2992,3132);
		String homedistrict1 = Base64DecoderS.decode(body.substring(2992, 3132).trim());
		// String homedistrict1 = body.substring(3132,3272);
		String homedetailedaddress1 = Base64DecoderS.decode(body.substring(3132, 3272).trim());
		// String homedetailedaddress1 = body.substring(3272,3412);
		ldcustomeraddress.setHomeaddresstype(homeaddresstype1);
		ldcustomeraddress.setHomeprovince(homeprovince1);
		ldcustomeraddress.setHomecity(homecity1);
		ldcustomeraddress.setHomedistrict(homedistrict1);
		ldcustomeraddress.setHomedetailedaddress(homedetailedaddress1);

		/**
		 * 永久地址信息
		 */
		String PermanentAddressType = body.substring(3272, 3307);// char(35)
		String PermanentProvince = body.substring(3307, 3342);// char(35)
		String PermanentCity = body.substring(3342, 3377);// char(35)
		String PermanentDistrict = body.substring(3377, 3412);// char(35)
		String PermanentDetailedAddress = body.substring(3412, 3447);// char(35)
		String PermanentPostCode = body.substring(3447, 3457);// char(10)
		ldcustomeraddress.setEpermanentaddresstype(PermanentAddressType);
		ldcustomeraddress.setEpermanentcity(PermanentCity);
		ldcustomeraddress.setEpermanentprovince(PermanentProvince);
		ldcustomeraddress.setEpermanentdistrict(PermanentDistrict);
		ldcustomeraddress.setEpermanentdetailedaddress(PermanentDetailedAddress);
		ldcustomeraddress.setPermanentpostcode(PermanentPostCode);
		
		// Base64转码
		String PermanentAddressType1 = Base64DecoderS.decode(body.substring(3457, 3597).trim());
		// String PermanentAddressType1 = body.substring(3597,3737);//char(140)
		String PermanentProvince1 = Base64DecoderS.decode(body.substring(3597, 3737).trim());
		// String PermanentProvince1 = body.substring(3737,3877);//char(140)
		String PermanentCity1 = Base64DecoderS.decode(body.substring(3737, 3877).trim());
		// String PermanentCity1 = body.substring(3877,4017);//char(140)
		String PermanentDistrict1 = Base64DecoderS.decode(body.substring(3877, 4017).trim());
		// String PermanentDistrict1=body.substring(4017,4157);//char(140)
		String PermanentDetailedAddress1 = Base64DecoderS.decode(body.substring(4017, 4157).trim());
		// String PermanentDetailedAddress1 = body.substring(4157,4297);//char(140)
		ldcustomeraddress.setPermanentaddresstype(PermanentAddressType1);
		ldcustomeraddress.setPermanentcity(PermanentCity1);
		ldcustomeraddress.setPermanentdetailedaddress(PermanentDetailedAddress1);
		ldcustomeraddress.setPermanentprovince(PermanentProvince1);
		ldcustomeraddress.setPermanentdistrict(PermanentDistrict1);
		
		/**
		 * 曾经居住地址信息
		 */
		String previouAddressType = body.substring(4157, 4192);// char(35)
		String previouProvince = body.substring(4192, 4227);// char(35)
		String previouCity = body.substring(4227, 4262);// char(35)
		String previouDistrict = body.substring(4262, 4297);// char(35)
		String previouDetailedAddress = body.substring(4297, 4332);// char(35)
		String previouPostCode = body.substring(4332, 4342);// char(10)
		ldcustomeraddress.setEpreviouaddresstype(previouAddressType);
		ldcustomeraddress.setEpreviouprovince(previouProvince);
		ldcustomeraddress.setEprevioucity(previouCity);
		ldcustomeraddress.setEprevioudistrict(previouDistrict);
		ldcustomeraddress.setEprevioudetailedaddress(previouDetailedAddress);
		ldcustomeraddress.setPrevioupostcode(previouPostCode);
		
		// Base64转码
		String previouAddressType1 = Base64DecoderS.decode(body.substring(4342, 4482).trim());
		// String previouAddressType1 = body.substring(4297,4332);//char(140)
		String previouProvince1 = Base64DecoderS.decode(body.substring(4482, 4622).trim());
		// String previouProvince1=body.substring(4332,4367);//char(140)
		String previouCity1 = Base64DecoderS.decode(body.substring(4622, 4762).trim());
		// String previouCity1 = body.substring(4367,4402);//char(140)
		String previouDistrict1 = Base64DecoderS.decode(body.substring(4762, 4902).trim());
		// String previouDistrict1 = body.substring(4402,4437);//char(140)
		String previouDetailedAddress1 = Base64DecoderS.decode(body.substring(4902, 5042).trim());
		// String previouDetailedAddress1 =
		// body.substring(4437,4472);//char(140)
		String previoucompany1 = Base64DecoderS.decode(body.substring(5042, 5182).trim());
		String workposition = Base64DecoderS.decode(body.substring(5182, 5262).trim());
		
		// String previoucompany1 = body.substring(4472,4612);//char(140)
		ldcustomeraddress.setPreviouaddresstype(previouAddressType1);
		ldcustomeraddress.setPreviouprovince(previouProvince1);
		ldcustomeraddress.setPrevioucity(previouCity1);
		ldcustomeraddress.setPrevioudistrict(previouDistrict1);
		ldcustomeraddress.setPrevioudetailedaddress(previouDetailedAddress1);
		ldcustomeraddress.setPrevioucompany(previoucompany1);
		ldcustomeraddress.setWorkposition(workposition);
		/**
		 * 工作地址
		 */
		// Work place
		// ===========================================================
		String CompanyAddress1 = body.substring(5262, 5297);// char(35)
		String CompanyAddress2 = body.substring(5297, 5332);// char(35)
		String CompanyAddress3 = body.substring(5332, 5367);// char(35)
		String CompanyAddress4 = body.substring(5367, 5402);// char(35)
		String CompanyAddress5 = body.substring(5402, 5437);// char(35)
		String PostCode = body.substring(5437, 5447);// char(10)
		ldcustomeraddress.setCompanyaddress1(CompanyAddress1);
		ldcustomeraddress.setCompanyaddress2(CompanyAddress2);
		ldcustomeraddress.setCompanyaddress3(CompanyAddress3);
		ldcustomeraddress.setCompanyaddress4(CompanyAddress4);
		ldcustomeraddress.setCompanyaddress5(CompanyAddress5);
		ldcustomeraddress.setWorkposition(PostCode);
		
		// Base64转码
		String WorkAddressType = Base64DecoderS.decode(body.substring(5447, 5587).trim());
		// String WorkAddressType = body.substring(4797,4937);//char(140)
		String WorkProvince = Base64DecoderS.decode(body.substring(5587, 5727).trim());
		// String WorkProvince = body.substring(4937,5077);//char(140)
		String WorkCity = Base64DecoderS.decode(body.substring(5727, 5867).trim());
		// String WorkCity = body.substring(5077,5217);//char(140)
		String WorkDistrict = Base64DecoderS.decode(body.substring(5867, 6007).trim());
		// String WorkDistrict = body.substring(5217,5357);//char(140)
		String WorkDetailedAddress = Base64DecoderS.decode(body.substring(6007, 6147).trim());
		// String WorkDetailedAddress = body.substring(5357,5497);//char(140)
		ldcustomeraddress.setWorkaddresstype(WorkAddressType);
		ldcustomeraddress.setWorkprovince(WorkProvince);
		ldcustomeraddress.setWorkcity(WorkCity);
		ldcustomeraddress.setWorkdistrict(WorkDistrict);
		ldcustomeraddress.setWorkdetailedaddress(WorkDetailedAddress);
		
		String IndustryType = body.substring(6147, 6153);// char(6)
		String Occupation = body.substring(6153, 6155);// char(2)
		String EmailAddress = body.substring(6155, 6215);// char(60)
		
		String Income = body.substring(6215, 6216);// char(1)
		String FamilyIncome = body.substring(6216, 6217);// char(1)
		ldcustomeraddress.setIndustrytype(IndustryType);
		ldcustomeraddress.setOccupation(Occupation);
		ldcustomeraddress.setEmailaddress(EmailAddress);
		ldcustomeraddress.setIncome(Income);
		ldcustomeraddress.setFamilyincome(FamilyIncome);
		
		/**
		 * 
		 * FATCA信息
		 */
		String MNCH = body.substring(6217, 6218);// char(1)多重国籍/国籍
		String CDN = body.substring(6218, 6220);// char(2)双重国籍国
		String CTN = body.substring(6220, 6222);// char(2)第三国籍国
		String CBP = body.substring(6222, 6224);// char(2)出生地国
		String PAI = body.substring(6224, 6225);// char(1)授权书指示符
		String HSBCBG = body.substring(6225, 6226);// char(1)汇丰银行集团
		String RM = body.substring(6226, 6229);// char(3)//客户关系经理
		String CTCC = body.substring(6229, 6231);// char(2)//客户税分类国家
		String CTCR = body.substring(6231, 6241);// char(10)//顾客税分类制度
		String FATCAnumbeof = body.substring(6241, 6246);// char(5)//FATCA条数
		String FATCAcontent = body.substring(6246, 7056);// char(810)//FATCA具体内容
		// 空48
		String TCR = body.substring(7056, 7058);// char(2)Tax Classification
												// Repeating 条数
		String TC = body.substring(7058, 7578);// char(520)Tax Classification
												// 具体内容
		
		/**
		 * CRS信息
		 */
		
		String COUNTRY_CODE  = body.substring(7578, 7580);//char(2)
		String DCGM = body.substring(7580, 7584);//char(4)
		String CBN = body.substring(7584, 7587);// char(3)
		String CSN = body.substring(7587, 7593);// char(6)
		String SCS = body.substring(7593, 7595);// char(2)
		String IDD_VALUE  = body.substring(7595, 7596);// char(1)
		String SCRD  =  body.substring(7596, 7604);// char(8)
		String DUE_DATE    =  body.substring(7604, 7612);// char(8)
		String DATE_REQUEST =  body.substring(7612, 7620);// char(8)
		String RTP =  body.substring(7620, 7621);// char(1)
		String EMPLOYEE_ID =  body.substring(7621, 7629);// char(8)
		String COMMUNICATION_RECORD =  body.substring(7629, 7637);// char(8)
		String PLACE_OF_BIRTH  =  body.substring(7637, 7657);//char(20)
		
		
		
		
		//税收1
		String SDEC = body.substring(7657, 7658);//char(1)
		String DEV1 = body.substring(7658, 7659);//char(1)
		String CTY1 = body.substring(7659, 7661);//char(2)
		String TIN1 = body.substring(7661, 7681);//char(20)
		String NTIN1 = body.substring(7681, 7682);//char(1)
		String RSN1 = body.substring(7682, 7712);//char(30)
		
		
		//有两个是空的，空三个char
		//税收2
		String CTY2 = body.substring(7715, 7735);//char(20)
		String TIN2 = body.substring(7735, 7736);//char(1)
		String NTIN2 = body.substring(7736, 7766);//char(30)
		String RSN2 = body.substring(7766, 7767);//char(1)
		
//		税收三
		String CTY3 = body.substring(7767,7769);//char(2)
		String TIN3 = body.substring(7769,7789);//char(20)
		String NTIN3 = body.substring(7789,7790);//char(1)
		String RSN3 = body.substring(7790, 7820);//char(30)
		
		
		String dev4  = body.substring(7820,7821);//char(1)
		String COUNTRY_4 = body.substring(7821,7823);//char(2)
		String tin4 = body.substring(7823,7843);//char(20)
		
		String SFNT = body.substring(7843,7844);//char(1)
		String REASON_DETAIL = body.substring(7844,7874);//char(30)
		String DEV5 = body.substring(7874,7875);//char(1)
		String COUNTRY_5 = body.substring(7875,7877);//char(2)
		String tin5 = body.substring(7877,7897);//char(20)
		
		
		String rfnt = body.substring(7897,7898);//char(1)
		String REASON_DETAIL2 = body.substring(7898,7928);//char(30)
		String FOBOP = body.substring(7928,7929);//char(1)
		String CAPACITY = body.substring(7929,7932);//char(3)
		String SPFN = body.substring(7932,7972);//char(40)
		String SPFN2 = body.substring(7972,8012);//char(40)
		
		String SPBD = body.substring(8012,8020);//char(8)
		String ADDITIONAL_INFORMATION =  body.substring(8020,8090);//char(70)
		String ACKNOWLEDGEMENT = body.substring(8090,8091);//char(1)
		String RM_ATTESTATION =  body.substring(8091,8092);//char(1)
		String RM_ATTESTATION_DATE = body.substring(8092,8100);//char(8)
		String RM_NAME =  body.substring(8100,8130);//char(30)
		String Residential_Address_ID = body.substring(8130,8132);//char(2)
		String Mailing_Address_ID =  body.substring(8132,8134);//char(2)
		String BIRTH_PROVINCE = body.substring(8134,8159);//char(25)
		String BIRTH_PROVINCE_2ND_LANGUAGE = body.substring(8159,8259);//char(100)
		BIRTH_PROVINCE_2ND_LANGUAGE = Base64DecoderS.decode(BIRTH_PROVINCE_2ND_LANGUAGE.trim());
		String BIRTH_CITY = body.substring(8259,8284);//char(25)
		String BIRTH_CITY_2ND_LANGUAGE = body.substring(8284,8384);//char(100)
		
		BIRTH_CITY_2ND_LANGUAGE = Base64DecoderS.decode(BIRTH_CITY_2ND_LANGUAGE.trim());
		
		
		String RESIDENTIAL_ADDR_PROVINCE = body.substring(8284,8409);//char(25)
		String RESIDEN_ADDR_PROVINCE_2ND_LANGUAG = body.substring(8409,8509);//char(100)
		
		RESIDEN_ADDR_PROVINCE_2ND_LANGUAG=Base64DecoderS.decode(RESIDEN_ADDR_PROVINCE_2ND_LANGUAG.trim());
		
		
		
		String RESIDENTIAL_ADDR_CITY = body.substring(8509,8534);//char(25)
		
		String RESIDEN_ADDR_CITY_2ND_LANGUAGE = body.substring(8534,8634);//char(100)
		
		RESIDEN_ADDR_CITY_2ND_LANGUAGE = Base64DecoderS.decode(RESIDEN_ADDR_CITY_2ND_LANGUAGE.trim());
		String MAILING_ADDR_PROVINCE = body.substring(8634,8659);//char(25)
		String MAILING_ADDR_PROVINCE_2ND_LANGUAGE = body.substring(8659,8759);//char(100)
		MAILING_ADDR_PROVINCE_2ND_LANGUAGE = Base64DecoderS.decode(MAILING_ADDR_PROVINCE_2ND_LANGUAGE.trim());
		
		String MAILING_ADDR_CITY = body.substring(8759,8784);//char(25)
		String MAILING_ADDR_CITY_2ND_LANGU = body.substring(8784,8884);//char(100)
		MAILING_ADDR_CITY_2ND_LANGU = Base64DecoderS.decode(MAILING_ADDR_CITY_2ND_LANGU.trim());
		
		
		
		
		/**
		 * 账户信息
		 */

		String Accountcount = body.substring(8884, 8886);// char(2)数量
		String Account = body.substring(8886, 12586);// char(3700)账户信息
//		String AccountNumber = body.substring(11606, 11624);// char(18)账户信息
//		String AccountCCY = body.substring(11624, 11627);// char(3)账户信息
//		String AccountBalance = body.substring(11627, 11642);// char(15)账户信息
//		String AccountStatus = body.substring(11642, 11643);// char(1)账户信息

		return null;// 此map中封装了页面中需要的保费总额
	}

	public Map submitData(String bankName, String YH, String BX, String cOperate) throws Exception {
		System.out.println(ldcustomeraddressDao == null);
		System.out.println(ldcustomerDao == null);
		this.bankName = bankName;
		this.YH = YH;
		this.BX = BX;
		this.mOperate = cOperate;
		System.out.println("now in ContNewXMLUI ");
		// 将外部传入的数据分解到本类的属性中，准备处理
		return this.dealData();
	}

}
