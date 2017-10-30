package com.sinosoft.service.business.ui.CustomerID.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.service.api.PubFun;
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

	TestWbeSphereMQ testWbeSphereMQ = new TestWbeSphereMQ();

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

	// 解析文件
	public Map dealData() throws Exception {
		String mOutStr = null;
		try {
			String content = new String();
			StringBuffer sbContent = new StringBuffer();
			sbContent
					.append("ISM Fixed V02.00YNHSBC_YBT                                                       YBT_CN_HUB                                                      ");
			sbContent
					.append("ENQCUSINFO                                                                                                                                                                                                                                              ");
			sbContent
					.append("Z2017-10-20T10:20:28.000069 +08:00                                                                                                                                                                                                                                                                              ");
			sbContent
					.append("0000007890000000000000001290000004530000002070010101OH_SERVICE_HEADER               00860100000   INTBCNHSBC                                                                  0101000199000199YBT_CN_HUB                                                      ENQCUSINFO                                                      01.0001.00                                                                                                                                                                                                                                                                                                           01000199");
			StringBuffer headContent = new StringBuffer();
			headContent.append("171020  ");// 交易日期
			headContent.append("151430  ");// 交易时间
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

		System.out.println("开始解析报文");
		String body = mOutStr.substring(1332);// 文件体
		String Flag = body.substring(0, 12);// 交易结果代码 char(12)
		String Desc = body.substring(12, 132);// 交易结果描述 char(120)
		String FullName = body.substring(132, 202);// 姓名 char(70)
		String FullNameChinese = body.substring(202, 482);// 中文名 char(280)
		String IDType = body.substring(482, 484);// 证件类型 char(2)
		String IDNumber = body.substring(484, 564);// 证件号码 char(80)
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
		String residentialaddresstype = body.substring(617, 652); // 地区类型
																	// char(35)
		String residentialprovince = body.substring(652, 687); // 省 char(35)
		String residentialcity = body.substring(687, 722);// 市 char(35)
		String residentialdistrict = body.substring(722, 757);// 区 char(35)
		String residentialdetailedaddress = body.substring(757, 792);// 详细地址
																		// char(35)
		String residentialpostcode = body.substring(792, 802);// 邮编 char(10)
		ldcustomeraddress.setResidentialaddresstype(residentialaddresstype);
		ldcustomeraddress.setResidentialprovince(residentialprovince);
		ldcustomeraddress.setResidentialcity(residentialcity);
		ldcustomeraddress.setResidentialdistrict(residentialdistrict);
		ldcustomeraddress.setResidentialdetailedaddress(residentialdetailedaddress);
		ldcustomeraddress.setResidentialpostcode(residentialpostcode);
		ldcustomeraddress.setCifid("123456");
		ldcustomeraddress.setLdcustomerno(ldcustomeraddressDao.select());
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress);

		// Base64转换
		Ldcustomeraddress ldcustomeraddress1 = new Ldcustomeraddress();
		String residentialaddresstype1 = Base64DecoderS.decode(body.substring(802, 942));
		// String residentialaddresstype1 = body.substring(802, 942); //地区类型
		// char(140)
		String residentialprovince1 = Base64DecoderS.decode(body.substring(942, 1082));
		// String residentialprovince1 = body.substring(942, 1082); //省
		// char(140)
		String residentialcity1 = Base64DecoderS.decode(body.substring(1082, 1222));
		// String residentialcity1 = body.substring(1082, 1222);//市 char(140)
		String residentialdistrict1 = Base64DecoderS.decode(body.substring(1222, 1362));
		// String residentialdistrict1 = body.substring(1222, 1362);//区
		// char(140)
		String residentialdetailedaddress1 = Base64DecoderS.decode(body.substring(1362, 1502));
		// String residentialdetailedaddress1 = body.substring(1362,1502);//详细地址
		// char(140)
		ldcustomeraddress1.setResidentialaddresstype(residentialaddresstype1);
		ldcustomeraddress1.setResidentialprovince(residentialprovince1);
		ldcustomeraddress1.setResidentialcity(residentialcity1);
		ldcustomeraddress1.setResidentialdistrict(residentialdistrict1);
		ldcustomeraddress1.setResidentialdetailedaddress(residentialdetailedaddress1);
		ldcustomeraddress1.setCifid("123456");
		ldcustomeraddress1.setLdcustomerno(ldcustomeraddressDao.select());
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress1);

		/**
		 * 联系地址信息
		 */

		Ldcustomeraddress ldcustomeraddress2 = new Ldcustomeraddress();
		String contactaddresstype = body.substring(1502, 1537);// 地区类型 char(35)
		String contactprovince = body.substring(1537, 1572);// 省 char(35)
		String contactcity = body.substring(1572, 1607);// 市 char(35)
		String contactdistrict = body.substring(1607, 1642);// 区 char(35)
		String contactdetailedaddress = body.substring(1642, 1677);// 详细地址
																	// char(35)
		String contactpostcode = body.substring(1677, 1687);// 详细地址 char(10)
		ldcustomeraddress2.setContactaddresstype(contactaddresstype);
		ldcustomeraddress2.setContactprovince(contactprovince);
		ldcustomeraddress2.setContactcity(contactcity);
		ldcustomeraddress2.setContactdistrict(contactdistrict);
		ldcustomeraddress2.setContactdetailedaddress(contactdetailedaddress);
		ldcustomeraddress2.setContactpostcode(contactpostcode);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress2);

		// Base64转换
		Ldcustomeraddress ldcustomeraddress3 = new Ldcustomeraddress();
		String contactaddresstype1 = Base64DecoderS.decode(body.substring(1687, 1827));
		// String contactaddresstype1 = body.substring(1687,1827);
		String contactprovince1 = Base64DecoderS.decode(body.substring(1967, 2107));
		// String contactprovince1 = body.substring(1967,2107);
		String contactcity1 = Base64DecoderS.decode(body.substring(2107, 2247));
		// String contactcity1 = body.substring(2107,2247);
		String contactdistrict1 = Base64DecoderS.decode(body.substring(2247, 2387));
		// String contactdistrict1 = body.substring(2247,2387);
		String contactdetailedaddress1 = Base64DecoderS.decode(body.substring(2387, 2527));
		// String contactdetailedaddress1 = body.substring(2387,2527);
		ldcustomeraddress3.setContactaddresstype(contactaddresstype1);
		ldcustomeraddress3.setContactprovince(contactprovince1);
		ldcustomeraddress3.setContactcity(contactcity1);
		ldcustomeraddress3.setContactdistrict(contactdistrict1);
		ldcustomeraddress3.setContactdetailedaddress(contactdetailedaddress1);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress3);

		/**
		 * 家庭地址
		 */
		Ldcustomeraddress ldcustomeraddress4 = new Ldcustomeraddress();
		String homeaddresstype = body.substring(2527, 2562);
		String homeprovince = body.substring(2562, 2597);
		String homecity = body.substring(2597, 2632);
		String homedistrict = body.substring(2632, 2667);
		String homedetailedaddress = body.substring(2667, 2702);
		String homepostcode = body.substring(2702, 2712);
		ldcustomeraddress4.setHomeaddresstype(homeaddresstype);
		ldcustomeraddress4.setHomeprovince(homeprovince);
		ldcustomeraddress4.setHomecity(homecity);
		ldcustomeraddress4.setHomedistrict(homedistrict);
		ldcustomeraddress4.setHomedetailedaddress(homedetailedaddress);
		ldcustomeraddress4.setHomepostcode(homepostcode);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress4);

		// Base64转换
		Ldcustomeraddress ldcustomeraddress5 = new Ldcustomeraddress();
		String homeaddresstype1 = Base64DecoderS.decode(body.substring(2712, 2852));
		// String homeaddresstype1 = body.substring(2712,2852);
		String homeprovince1 = Base64DecoderS.decode(body.substring(2852, 2992));
		// String homeprovince1 = body.substring(2852,2992);
		String homecity1 = Base64DecoderS.decode(body.substring(2992, 3132));
		// String homecity1 = body.substring(2992,3132);
		String homedistrict1 = Base64DecoderS.decode(body.substring(3132, 3272));
		// String homedistrict1 = body.substring(3132,3272);
		String homedetailedaddress1 = Base64DecoderS.decode(body.substring(3272, 3412));
		// String homedetailedaddress1 = body.substring(3272,3412);
		ldcustomeraddress5.setHomeaddresstype(homeaddresstype1);
		ldcustomeraddress5.setHomeprovince(homeprovince1);
		ldcustomeraddress5.setHomecity(homecity1);
		ldcustomeraddress5.setHomedistrict(homedistrict1);
		ldcustomeraddress5.setHomedetailedaddress(homedetailedaddress1);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress5);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress5);
		/**
		 * 永久地址信息
		 */
		Ldcustomeraddress ldcustomeraddress6 = new Ldcustomeraddress();
		String PermanentAddressType = body.substring(3412, 3447);// char(35)
		String PermanentProvince = body.substring(3447, 3482);// char(35)
		String PermanentCity = body.substring(3482, 3517);// char(35)
		String PermanentDistrict = body.substring(3517, 3552);// char(35)
		String PermanentDetailedAddress = body.substring(3552, 3587);// char(35)
		String PermanentPostCode = body.substring(3587, 3597);// char(10)
		ldcustomeraddress6.setPermanentaddresstype(PermanentAddressType);
		ldcustomeraddress6.setPermanentcity(PermanentCity);
		ldcustomeraddress6.setPermanentprovince(PermanentProvince);
		ldcustomeraddress6.setPermanentdistrict(PermanentDistrict);
		ldcustomeraddress6.setPermanentdetailedaddress(PermanentDetailedAddress);
		ldcustomeraddress6.setPermanentpostcode(PermanentPostCode);

		// Base64转码
		Ldcustomeraddress ldcustomeraddress7 = new Ldcustomeraddress();
		String PermanentAddressType1 = Base64DecoderS.decode(body.substring(3597, 3737));
		// String PermanentAddressType1 = body.substring(3597,3737);//char(140)
		String PermanentProvince1 = Base64DecoderS.decode(body.substring(3737, 3877));
		// String PermanentProvince1 = body.substring(3737,3877);//char(140)
		String PermanentCity1 = Base64DecoderS.decode(body.substring(3877, 4017));
		// String PermanentCity1 = body.substring(3877,4017);//char(140)
		String PermanentDistrict1 = Base64DecoderS.decode(body.substring(4017, 4157));
		// String PermanentDistrict1=body.substring(4017,4157);//char(140)
		String PermanentDetailedAddress1 = Base64DecoderS.decode(body.substring(4157, 4297));
		// String PermanentDetailedAddress1 =
		// body.substring(4157,4297);//char(140)
		ldcustomeraddress7.setPermanentaddresstype(PermanentAddressType1);
		ldcustomeraddress7.setPermanentcity(PermanentCity1);
		ldcustomeraddress7.setPermanentdetailedaddress(PermanentDetailedAddress1);
		ldcustomeraddress7.setPermanentprovince(PermanentProvince1);
		ldcustomeraddress7.setPermanentdistrict(PermanentDistrict1);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress7);

		/**
		 * 曾经居住地址信息
		 */
		Ldcustomeraddress ldcustomeraddress8 = new Ldcustomeraddress();
		String previouAddressType = body.substring(4297, 4332);// char(35)
		String previouProvince = body.substring(4332, 4367);// char(35)
		String previouCity = body.substring(4367, 4402);// char(35)
		String previouDistrict = body.substring(4402, 4437);// char(35)
		String previouDetailedAddress = body.substring(4437, 4472);// char(35)
		String previouPostCode = body.substring(4472, 4482);// char(10)
		ldcustomeraddress8.setPreviouaddresstype(previouAddressType);
		ldcustomeraddress8.setPreviouprovince(previouProvince);
		ldcustomeraddress8.setPrevioucity(previouCity);
		ldcustomeraddress8.setPrevioudistrict(previouDistrict);
		ldcustomeraddress8.setPrevioudetailedaddress(previouDetailedAddress);
		ldcustomeraddress8.setPrevioupostcode(previouPostCode);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress8);

		// Base64转码
		Ldcustomeraddress ldcustomeraddress9 = new Ldcustomeraddress();
		String previouAddressType1 = Base64DecoderS.decode(body.substring(4297, 4332));
		// String previouAddressType1 = body.substring(4297,4332);//char(140)
		String previouProvince1 = Base64DecoderS.decode(body.substring(4332, 4367));
		// String previouProvince1=body.substring(4332,4367);//char(140)
		String previouCity1 = Base64DecoderS.decode(body.substring(4367, 4402));
		// String previouCity1 = body.substring(4367,4402);//char(140)
		String previouDistrict1 = Base64DecoderS.decode(body.substring(4402, 4437));
		// String previouDistrict1 = body.substring(4402,4437);//char(140)
		String previouDetailedAddress1 = Base64DecoderS.decode(body.substring(4437, 4472));
		// String previouDetailedAddress1 =
		// body.substring(4437,4472);//char(140)
		String previoucompany1 = Base64DecoderS.decode(body.substring(4472, 4612));
		// String previoucompany1 = body.substring(4472,4612);//char(140)
		ldcustomeraddress9.setPreviouaddresstype(previouAddressType1);
		ldcustomeraddress9.setPreviouprovince(previouProvince1);
		ldcustomeraddress9.setPrevioucity(previouCity1);
		ldcustomeraddress9.setPrevioudistrict(previouDistrict1);
		ldcustomeraddress9.setPrevioudetailedaddress(previouDetailedAddress1);
		ldcustomeraddress9.setPrevioucompany(previoucompany1);
		ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress9);

		/**
		 * 工作地址
		 */
		// Work place
		// ===========================================================
		String CompanyAddress1 = body.substring(4612, 4647);// char(35)
		String CompanyAddress2 = body.substring(4647, 4682);// char(35)
		String CompanyAddress3 = body.substring(4682, 4717);// char(35)
		String CompanyAddress4 = body.substring(4717, 4752);// char(35)
		String CompanyAddress5 = body.substring(4752, 4787);// char(35)
		String PostCode = body.substring(4787, 4797);// char(10)

		// Base64转码
		String WorkAddressType = Base64DecoderS.decode(body.substring(4797, 4937));
		// String WorkAddressType = body.substring(4797,4937);//char(140)
		String WorkProvince = Base64DecoderS.decode(body.substring(4937, 5077));
		// String WorkProvince = body.substring(4937,5077);//char(140)
		String WorkCity = Base64DecoderS.decode(body.substring(5077, 5217));
		// String WorkCity = body.substring(5077,5217);//char(140)
		String WorkDistrict = Base64DecoderS.decode(body.substring(5217, 5357));
		// String WorkDistrict = body.substring(5217,5357);//char(140)
		String WorkDetailedAddress = Base64DecoderS.decode(body.substring(5357, 5497));
		// String WorkDetailedAddress = body.substring(5357,5497);//char(140)
		String Position = Base64DecoderS.decode(body.substring(5497, 5577));
		// String Position = body.substring(5497,5577);//char(80)

		String IndustryType = body.substring(5577, 5583);// char(6)
		String Occupation = body.substring(5583, 5585);// char(2)
		String EmailAddress = body.substring(5585, 5645);// char(60)
		String Income = body.substring(5645, 5646);// char(1)
		String FamilyIncome = body.substring(5646, 5647);// char(1)

		/**
		 * FATCA信息
		 */
		String MNCH = body.substring(5647, 5648);// char(1)多重国籍/国籍
		String CDN = body.substring(5648, 5650);// char(2)双重国籍国
		String CTN = body.substring(5650, 5652);// char(2)第三国籍国
		String CBP = body.substring(5652, 5654);// char(2)出生地国
		String PAI = body.substring(5654, 5655);// char(1)授权书指示符
		String HSBCBG = body.substring(5655, 5656);// char(1)汇丰银行集团
		String RM = body.substring(5656, 5659);// char(3)//客户关系经理
		String CTCC = body.substring(5659, 5661);// char(2)//客户税分类国家
		String CTCR = body.substring(5661, 5671);// char(10)//顾客税分类制度
		String FATCAnumbeof = body.substring(5671, 5676);// char(5)//FATCA条数
		String FATCAcontent = body.substring(5676, 6486);// char(810)//FATCA具体内容
		// 空48
		String TCR = body.substring(6534, 6536);// char(2)Tax Classification
												// Repeating 条数
		String TC = body.substring(6536, 7056);// char(520)Tax Classification
												// 具体内容
		// 空52
		/**
		 * CRS信息
		 */
		String STUS = body.substring(7108, 7110);// char(2)
		String IDDV = body.substring(7110, 7111);// char(1)
		String CRDT = body.substring(7111, 7119);// char(8)
		String DUDT = body.substring(7119, 7127);// char(8)
		String RQDT = body.substring(7127, 7135);// char(8)
		String RSTP = body.substring(7135, 7136);// char(1)
		String EMID = body.substring(7136, 7144);// char(8)
		String CMUR = body.substring(7144, 7152);// char(8)
		String SDEC = body.substring(7152, 7153);// char(1)
		String DEV1 = body.substring(7153, 7154);// char(1)
		String CTY1 = body.substring(7154, 7156);// char(2)
		String TIN1 = body.substring(7156, 7176);// char(20)
		String NTIN1 = body.substring(7176, 7177);// char(1)
		String RSN1 = body.substring(7177, 7207);// char(30)
		String DEV2 = body.substring(7207, 7208);// char(1)
		String CTY2 = body.substring(7208, 7210);// char(2)
		String TIN2 = body.substring(7210, 7230);// char(20)
		String NTIN2 = body.substring(7230, 7231);// char(1)
		String RSN2 = body.substring(7231, 7261);// char(30)
		String DEV3 = body.substring(7261, 7262);// char(1)
		String CTY3 = body.substring(7262, 7264);// char(2)
		String TIN3 = body.substring(7264, 7284);// char(20)
		String NTIN3 = body.substring(7284, 7285);// char(1)
		String RSN3 = body.substring(7285, 7315);// char(30)
		String DEV4 = body.substring(7315, 7316);// char(1)
		String CTY4 = body.substring(7316, 7318);// char(2)
		String TIN4 = body.substring(7318, 7338);// char(20)
		String NTIN4 = body.substring(7338, 7339);// char(1)
		String RSN4 = body.substring(7339, 7369);// char(30)
		String DEV5 = body.substring(7369, 7370);// char(1)
		String CTY5 = body.substring(7370, 7372);// char(2)
		String TIN5 = body.substring(7372, 7392);// char(20)
		String NTIN5 = body.substring(7392, 7393);// char(1)
		String RSN5 = body.substring(7393, 7423);// char(30)
		String FOAP = body.substring(7423, 7424);// char(1)
		String CAP = body.substring(7424, 7427);// char(3)
		String SFMN = body.substring(7427, 7467);// char(40)
		String SFSN = body.substring(7467, 7507);// char(40)
		String SBDT = body.substring(7507, 7515);// char(8)
		String ADIF = body.substring(7515, 7585);// char(70)
		String ACK = body.substring(7585, 7586);// char(1)
		String RMAT = body.substring(7586, 7587);// char(1)
		String RMNM = body.substring(7587, 7617);// char(30)
		String RMAD = body.substring(7617, 7625);// char(8)
		String RSID = body.substring(7625, 7627);// char(2)
		String MAID = body.substring(7627, 7629);// char(2)
		String BTPV = body.substring(7629, 7654);// char(25)
		String BTP2 = body.substring(7654, 7679);// char(25)
		String BTCT = body.substring(7679, 7704);// char(25)
		String BTC2 = body.substring(7704, 7729);// char(25)
		String RSPV = body.substring(7729, 7754);// char(25)
		String RSP2 = body.substring(7754, 7779);// char(25)
		String RSCT = body.substring(7779, 7804);// char(25)
		String RSC2 = body.substring(7804, 7829);// char(25)
		String MAPV = body.substring(7829, 7854);// char(25)
		String MAP2 = body.substring(7854, 7879);// char(25)
		String MACT = body.substring(7879, 7904);// char(25)

		/**
		 * 账户信息
		 */

		String Accountcount = body.substring(7904, 7906);// char(2)数量
		String Account = body.substring(7906, 11606);// char(3700)账户信息

		String AccountNumber = body.substring(11606, 11624);// char(18)账户信息
		String AccountCCY = body.substring(11624, 11627);// char(3)账户信息
		String AccountBalance = body.substring(11627, 11642);// char(15)账户信息
		String AccountStatus = body.substring(11642, 11643);// char(1)账户信息

		// StringReader sr = new StringReader(mOutStr);
		// InputSource is = new InputSource(sr);
		// Document doc = (new SAXBuilder()).build(is);
		//
		// Ldcustomer ldcustomer=new Ldcustomer();//存入数据库的实体类
		// Ldcustomeraddress ldcustomeraddress=new Ldcustomeraddress();
		// Ldcustomerid ldcustomerid=new Ldcustomerid();
		// Ldcustomeraccount ldcustomeraccount=new Ldcustomeraccount();
		// Map m=new HashMap();
		// Element root = doc.getRootElement();
		// Element head=root.getChild("Head");
		// String Flag= head.getChildText("Flag");
		// String Desc= head.getChildText("Desc");
		// System.out.println(Flag);
		//
		// Element Body1 = root.getChild("Body");
		// String FullName=Body1.getChildText("FullName");
		// String FormerName=Body1.getChildText("FormerName");
		// String IDType=Body1.getChildText("IDType");
		// String IDNumber=Body1.getChildText("IDNumber");
		// String IDExpiry=Body1.getChildText("IDExpiry");
		// String DateofBirth=Body1.getChildText("DateofBirth");
		// String Age=Body1.getChildText("Age");
		// String Gender=Body1.getChildText("Gender");
		//
		// Element Nationalities = Body1.getChild("Nationalities");
		// Element Nationality = Nationalities.getChild("Nationality");
		// String CensusRegister=Nationality.getChildText("CensusRegister");
		// String MaritalStatus=Nationality.getChildText("MaritalStatus");
		// String ContactPhoneNo=Nationality.getChildText("ContactPhoneNo");
		// String MobilePhoneNo=Nationality.getChildText("MobilePhoneNo");
		// String CallBack=Nationality.getChildText("CallBack");
		// //ldcustomer=ldcustomerDao.selectLdcustomer("123456");
		// ldcustomerDao.deleteLdcustomer("123456");
		// ldcustomer.setFullname(FullName);
		// ldcustomer.setFormername(FormerName);
		// ldcustomer.setIdtype(IDType);
		// ldcustomer.setIdnumber(IDNumber);
		// ldcustomer.setIdexpiry(IDExpiry);
		// ldcustomer.setAge(Age);
		// ldcustomer.setGender(Gender);
		// ldcustomer.setContactphoneno(ContactPhoneNo);
		// ldcustomer.setMaritalstatus(MaritalStatus);
		// ldcustomer.setMobilephoneno(MobilePhoneNo);
		// ldcustomer.setCallback(CallBack);
		// ldcustomer.setCensusregister(CensusRegister);
		// ldcustomer.setCifid("123456");
		// ldcustomer.setLdcustomerno(ldcustomerDao.selectldcustomerno());
		// ldcustomerDao.addLdcustomer(ldcustomer);
		// System.out.println("保存信息表成功");
		// //ldcustomerDao.updateLdcustomer(ldcustomer);
		//
		// Element Addresses = Body1.getChild("Addresses");
		// List list=Addresses.getChildren("Address");
		// ldcustomeraddressDao.deleteLdcustomeraddress("123456");
		// for (int i = 0; i < list.size(); i++) {
		// String
		// AddressType=((Element)list.get(i)).getChildText("AddressType");
		// if(AddressType.equals("16")){
		// System.out.println("居住地址信息:");
		// String Province=((Element)list.get(i)).getChildText("Province");
		// String City=((Element)list.get(i)).getChildText("City");
		// String District=((Element)list.get(i)).getChildText("District");
		// String
		// DetailedAddress=((Element)list.get(i)).getChildText("DetailedAddress");
		// String PostCode=((Element)list.get(i)).getChildText("PostCode");
		// ldcustomeraddress.setResidentialcity(City);
		// ldcustomeraddress.setResidentialdistrict(District);
		// ldcustomeraddress.setResidentialdetailedaddress(DetailedAddress);
		// ldcustomeraddress.setResidentialpostcode(PostCode);
		// }
		// if(AddressType.equals("22")){
		// System.out.println("联系地址信息:");
		// String Province=((Element)list.get(i)).getChildText("Province");
		// String City=((Element)list.get(i)).getChildText("City");
		// String District=((Element)list.get(i)).getChildText("District");
		// String
		// DetailedAddress=((Element)list.get(i)).getChildText("DetailedAddress");
		// String PostCode=((Element)list.get(i)).getChildText("PostCode");
		// System.out.println(Province+City+District+DetailedAddress+PostCode);
		// ldcustomeraddress.setContactprovince(Province);
		// ldcustomeraddress.setContactcity(City);
		// ldcustomeraddress.setContactdistrict(District);
		// ldcustomeraddress.setContactdetailedaddress(DetailedAddress);
		// ldcustomeraddress.setContactpostcode(PostCode);
		//
		// }
		// if(AddressType.equals("28")){
		// System.out.println("家庭地址:");
		// String Province=((Element)list.get(i)).getChildText("Province");
		// String City=((Element)list.get(i)).getChildText("City");
		// String District=((Element)list.get(i)).getChildText("District");
		// String
		// DetailedAddress=((Element)list.get(i)).getChildText("DetailedAddress");
		// String PostCode=((Element)list.get(i)).getChildText("PostCode");
		// ldcustomeraddress.setHomeprovince(Province);
		// ldcustomeraddress.setHomecity(City);
		// ldcustomeraddress.setHomepostcode(PostCode);
		// ldcustomeraddress.setHomedetailedaddress(DetailedAddress);
		// ldcustomeraddress.setHomedistrict(District);
		//
		// }
		// if(AddressType.equals("34")){
		// System.out.println("永久地址信息:");
		// String Province=((Element)list.get(i)).getChildText("Province");
		// String City=((Element)list.get(i)).getChildText("City");
		// String District=((Element)list.get(i)).getChildText("District");
		// String
		// DetailedAddress=((Element)list.get(i)).getChildText("DetailedAddress");
		// String PostCode=((Element)list.get(i)).getChildText("PostCode");
		// ldcustomeraddress.setPermanentcity(City);
		// ldcustomeraddress.setPermanentprovince(Province);
		// ldcustomeraddress.setPermanentdistrict(District);
		// ldcustomeraddress.setPermanentdetailedaddress(DetailedAddress);
		// ldcustomeraddress.setPermanentpostcode(PostCode);
		//
		// }
		// if(AddressType.equals("40")){
		// System.out.println("永久地址信息:");
		// String Province=((Element)list.get(i)).getChildText("Province");
		// String City=((Element)list.get(i)).getChildText("City");
		// String District=((Element)list.get(i)).getChildText("District");
		// String
		// DetailedAddress=((Element)list.get(i)).getChildText("DetailedAddress");
		// String PostCode=((Element)list.get(i)).getChildText("PostCode");
		// String Company=((Element)list.get(i)).getChildText("Company");
		// String
		// CompanyAddress=((Element)list.get(i)).getChildText("CompanyAddress");
		// String Position=((Element)list.get(i)).getChildText("Position");
		// String
		// IndustryType=((Element)list.get(i)).getChildText("IndustryType");
		// String Occupation=((Element)list.get(i)).getChildText("Occupation");
		// String
		// EmailAddress=((Element)list.get(i)).getChildText("EmailAddress");
		// String Income=((Element)list.get(i)).getChildText("Income");
		// String
		// FamilyIncome=((Element)list.get(i)).getChildText("FamilyIncome");
		// String
		// MaritalStatus1=((Element)list.get(i)).getChildText("MaritalStatus");
		// String Height=((Element)list.get(i)).getChildText("Height");
		// String Weight=((Element)list.get(i)).getChildText("Weight");
		// String
		// IncomeSource=((Element)list.get(i)).getChildText("IncomeSource");
		// String
		// WealthSource=((Element)list.get(i)).getChildText("WealthSource");
		// String
		// TelephoneSource=((Element)list.get(i)).getChildText("TelephoneSource");
		// String HealthCare=((Element)list.get(i)).getChildText("HealthCare");
		// ldcustomeraddress.setPreviouprovince(Province);
		// ldcustomeraddress.setPrevioucity(City);
		// ldcustomeraddress.setPrevioudistrict(District);
		// ldcustomeraddress.setPrevioudetailedaddress(DetailedAddress);
		// ldcustomeraddress.setPrevioupostcode(PostCode);
		// ldcustomeraddress.setPrevioucompany(Company);
		// ldcustomeraddress.setPrevioucompanyaddress(CompanyAddress);
		// ldcustomeraddress.setPreviouoccupation(Position);
		// ldcustomeraddress.setPreviouindustrytype(IndustryType);
		// ldcustomeraddress.setPreviouoccupation(Occupation);
		// ldcustomeraddress.setPreviouemailaddress(EmailAddress);
		// ldcustomeraddress.setPreviouincome(Income);
		// ldcustomeraddress.setPreviouemailaddress(MaritalStatus1);
		// ldcustomeraddress.setPreviouheight(Height);
		// ldcustomeraddress.setPreviouweight(Weight);
		// ldcustomeraddress.setPreviouincomesource(IncomeSource);
		// ldcustomeraddress.setPreviouwealthsource(WealthSource);
		// ldcustomeraddress.setPrevioutelephonesource(TelephoneSource);
		// ldcustomeraddress.setPreviouhealthcare(HealthCare);
		// ldcustomeraddress.setPrevioufamilyincome(FamilyIncome);
		// }

		// }
		// ldcustomeraddress.setCifid("123456");
		// ldcustomeraddress.setLdcustomerno(ldcustomeraddressDao.select());
		// ldcustomeraddressDao.addLdcustomeraddress(ldcustomeraddress);
		// Element Accounts = Body1.getChild("Accounts");
		// List ls=Accounts.getChildren("Account");
		// ldcustomeraccountDao.delectLdcustomeraccount("123456");
		// for(int i = 0; i < ls.size(); i++){
		// Element Account = (Element) ls.get(i);
		// String AccountNumber=Account.getChildText("AccountNumber");
		// String AccountCCY=Account.getChildText("AccountCCY");
		// String AccountBalance=Account.getChildText("AccountBalance");
		// String AccountStatus=Account.getChildText("AccountStatus");
		// String Bak1=Account.getChildText("Bak1");
		// String Bak2=Account.getChildText("Bak2");
		// String Bak3=Account.getChildText("Bak3");
		// String Bak4=Account.getChildText("Bak4");
		// String Bak5=Account.getChildText("Bak5");
		// String Bak6=Account.getChildText("Bak6");
		// String Bak7=Account.getChildText("Bak7");
		// String Bak8=Account.getChildText("Bak8");
		// String Bak9=Account.getChildText("Bak9");
		// String Bak10=Account.getChildText("Bak10");
		//
		// ldcustomeraccount.setCountno(UuidUtil.createUuid());
		// ldcustomeraccount.setLdcustomerno(ldcustomeraccountDao.seleteaccount());
		// ldcustomeraccount.setCifid("123456");
		// ldcustomeraccount.setAccountnumber(AccountNumber);
		// ldcustomeraccount.setAccountccy(AccountCCY);
		// ldcustomeraccount.setAccountbalance(AccountBalance);
		// ldcustomeraccount.setAccountstatus(AccountStatus);
		// ldcustomeraccount.setBak1(Bak1);
		// ldcustomeraccount.setBak1(Bak2);
		// ldcustomeraccount.setBak1(Bak3);
		// ldcustomeraccount.setBak1(Bak4);
		// ldcustomeraccount.setBak1(Bak5);
		// ldcustomeraccount.setBak1(Bak6);
		// ldcustomeraccount.setBak1(Bak7);
		// ldcustomeraccount.setBak1(Bak8);
		// ldcustomeraccount.setBak1(Bak9);
		// ldcustomeraccount.setBak1(Bak10);
		// ldcustomeraccountDao.addLdcustomeraccount(ldcustomeraccount);
		// }
		// m.put("ldcustomeraddress", ldcustomeraddress);
		// m.put("ldcustomer", ldcustomer);
		// m.put("ldcustomeraccount", ldcustomeraccount);
		// m.put("Flag", Flag);
		// m.put("Desc", Desc);

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
