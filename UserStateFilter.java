/**
 * @Title: UserStateFilter.java
 * @Package com.sinosoft.eservice.api.filter
 * @Description: TODO(用一句话描述该文件做什么)
 * @author cheng
 * @date 2017年5月2日 下午12:21:10
 * @version V1.0
 */
package com.sinosoft.eservice.api.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.eservice.api.bean.MessageBean;
import com.sinosoft.eservice.api.bean.UserLoginInfoBean;
import com.sinosoft.service.business.db.dao.SYSUserDao;
import com.sinosoft.service.business.utils.JsonUtil;

/**
 * @ClassName UserStateFilter
 * @Description: 用户登录状态过虑器
 * @author cheng
 * @date 2017年5月2日 下午12:21:10
 *
 */
public class UserStateFilter implements Filter
{
	/**日志*/
	private static final Logger LOG = LogManager.getLogger(UserStateFilter.class);
	
	/** 过虑器初始化信息 */
	protected FilterConfig config;
	
	/** 过虑器初始化信息配置节点名 */
	private static final String EXCEPT_PATTERNS = "_except_urlpattern";
	
	/**用户登录信息*/
	public static final String USER_LOGIN_INFO = "userLoginInfo";
	
	/**用户数据接口*/
	@Autowired
	private SYSUserDao sysUserDao;
	

	/* (非 Javadoc)
	 * <p>Title: destroy</p>
	 * <p>Description: </p>
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
		LOG.info("用户登录状态过虑器销毁");

	}

	/* (非 Javadoc)
	 * <p>Title: doFilter</p>
	 * <p>Description: </p>
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{

		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;  
		
		HttpServletResponse resp = (HttpServletResponse)response;  
		String uri = req.getRequestURI().toString();
		
		// 得到过虑器配置初始化信息
		String exceptUrlPattern = this.config.getInitParameter(EXCEPT_PATTERNS);
		
		UrlExpProcessor urlExpProcessor = null;
		
		if ((exceptUrlPattern != null) && (!exceptUrlPattern.equals("")))
		{
			urlExpProcessor = new UrlExpProcessor(exceptUrlPattern + "%%" + "\\.css$|\\.js$|\\.jpg$|\\.gif$|\\.png$|\\.gzjs$|\\.gzcss");
		}
		else
		{
			urlExpProcessor = new UrlExpProcessor("\\.css$|\\.js$|\\.jpg$|\\.gif$|\\.png$|\\.gzjs$|\\.gzcss");
		}
		if (urlExpProcessor.match(uri))
		{
			LOG.debug("urlExprocessor matching");
			chain.doFilter(request, response);
			return;
		}
		
		
		String currentUrl = req.getServletPath();
		
		String suri=req.getRequestURI();
		String before=suri.substring(suri.lastIndexOf("/")+1);
		
		
		StringBuffer requestURL = req.getRequestURL();
		String RequestURL = requestURL.toString();
		
		
		if (currentUrl.toLowerCase().endsWith("index.jsp") || currentUrl.toLowerCase().endsWith("checklogin.do")||before.equals("showdialog.jsp"))
		{
			 
			chain.doFilter(request, response);
			return;
		}		
		
////		if(RequestURL.equals("http://localhost:8080/new_eservice_business/application/newTestingCenter/newCont/jsp/partNewContEnter.jsp")){
////			String username = request.getParameter("F_TOKEN"); 
////			System.out.println("第一次打印 Token "+username);
////			String base64 = decode(username);
////			System.out.println("解码后的Token "+base64);
////			
////			String ipos = request.getParameter("F_IPOSDATA");
////			System.out.println("解码后的IPOS "+decode(ipos));
//			System.out.println("sss");
//			chain.doFilter(request, response);
//			return;
//		}
//		System.out.println(RequestURL+"************");
//		if(RequestURL.equals("http://192.168.8.104:8080/new_eservice_business/application/newTestingCenter/newCont/jsp/partNewContEnter.jsp")){

		if(RequestURL.equals("http://ybtappuat.cn.hsbc:20118/ybt_uat/application/newTestingCenter/newCont/jsp/partNewContEnter.jsp")
				|| 
		   RequestURL.equals("http://ybtappuat.cn.hsbc:20118/ybt_sit/application/newTestingCenter/newCont/jsp/partNewContEnter.jsp")
				){
			System.out.println(RequestURL+"************");
			String username = request.getParameter("F_TOKEN"); 
			System.out.println("第一次打印 Token "+username);
			String base64Token = decode(username);
			System.out.println("解码后的Token "+base64Token);
			if(base64Token.equals("YBT1234567")){
				String ipos = request.getParameter("F_IPOSDATA");
				System.out.println("解码后的IPOS "+decode(ipos));
				Map<String, String> map = parseXml(decode(ipos));
//				Map<String, String> map = parseXml("");
				UserLoginInfoBean tUserLoginInfo = new UserLoginInfoBean();
				tUserLoginInfo.setEmplID(map.get("StafId"));
				req.getSession().setAttribute(UserStateFilter.USER_LOGIN_INFO, tUserLoginInfo);
				String CustRecId = map.get("CustRecId");
				String ProdCde = map.get("ProdCde");
				String GoalId = map.get("GoalId");
				String contextPath = req.getContextPath();
				System.out.println(contextPath);
				String url = "/application/newTestingCenter/newCont/jsp/newContApply.jsp?"+
						"grpcontno="+CustRecId+"&riskCode="+ProdCde+"&GoalId="+GoalId;
				request.getRequestDispatcher(url).forward(request,response);
				
				return;
			}else{
				LOG.warn("Token验证失败!");
				request.getRequestDispatcher("/index.jsp").forward(request,response);
				PrintWriter out = response.getWriter();
				out.print("<script type='text/javascript'>top.location='/new_eservice_business/index.jsp';</script>"); 
				return;
			}
			
			
		}  
		
		//判断请求地址是否是直接输入的
		String referer = req.getHeader("referer");
		if(StringUtils.isBlank(referer))
		{
			LOG.error("不能直接通过浏览器地址访问");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			out.print("<script type='text/javascript'>top.location='/new_eservice_business/index.jsp';</script>"); 
			return;
		}
	
		
		//验证结果
		boolean checkRes = false;

		//获得session
		HttpSession session =  req.getSession();
		if (session == null)
		{
			
			checkRes = false;
		}
		else
		{
			//获得session中的用户信息
			UserLoginInfoBean userLoginInfoBean = (UserLoginInfoBean)session.getAttribute(USER_LOGIN_INFO);
			
			if(userLoginInfoBean != null)
			{
				//获得用户id
				String userId = userLoginInfoBean.getEmplID();
				
				//查询用户状态:
				//SYSUserVo tSYSUserVo = sysUserDao.selectByPrimaryKey(userId);
				
				String userState = userLoginInfoBean.getStatus();
				//若用户状态不为"1",则返回登录页，否则继续请求
				/*if (!"1".equals(userState))
				{
					session.invalidate();
					checkRes = false;					
				}
				else 
				{*/  
					checkRes = true; 
				//} 
			}
			else
			{
				session.invalidate();
				checkRes = false;
			}
			
		}
		//验证通过
		if(checkRes)
		{
			chain.doFilter(request, response);
		}
		else
		{
			String tRequested = req.getHeader("x-requested-with");
			
			//判断是否是ajax请求
			if(StringUtils.isNoneBlank(tRequested) && "XMLHttpRequest".equalsIgnoreCase(tRequested))
			{
				//ajax请求
					
				MessageBean message = new MessageBean();
				message.setSuccess(false);
				message.setMsg("超时，请重新登录");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(JsonUtil.vo2JsonString(message));
				out.close();
			}
			else
			{
				
				PrintWriter out = response.getWriter();
				
				out.print("<script type='text/javascript'>top.location='/new_eservice_business/index.jsp';</script>"); 
				//普通请求跳转
				//req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
			
		}

	}

	/* (非 Javadoc)
	 * <p>Title: init</p>
	 * <p>Description: </p>
	 * @param arg0
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		this.config = arg0;

	}

	
	
	/**
	 * base64解码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	 @SuppressWarnings("restriction")
	public static String decode(String str) throws UnsupportedEncodingException{ 
//		 System.out.println(str);
		   byte[] bt = null;  
		   String res = null;
		   try {    
		       @SuppressWarnings("restriction")
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
		       bt = decoder.decodeBuffer(str);    
		       res = new String(bt);
		   } catch (IOException e) {    
		       e.printStackTrace();    
		   }    
		   return res; 
	} 
	 
	 
	 
	 public Map<String,String> parseXml(String str){
		 SAXReader sax = null;
//			FileReader reader = null;
//			BufferedReader buff = null;
//			String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Proposal><AppDt>2017-10-24</AppDt><PlcyEffDt>2017-10-24</PlcyEffDt><CountryCode>CN</CountryCode><Chanl>10</Chanl><InputSysLang>en_US</InputSysLang><PlcyOwnrList><PlcyOwnr><PerslDetl><PrsnName><EnglSrnm>ZGC</EnglSrnm><EnglFrstNm>FNCN</EnglFrstNm><ChinNm>张一</ChinNm><ChinNmCde/><Salut>MR</Salut></PrsnName><IdDoc><TypeCde>I</TypeCde><Num>440601197003190026</Num><PlcOfIssCde/></IdDoc><GendrCde>M</GendrCde><DtOfBirth>1970-03-19</DtOfBirth><Addr><AddrLine1/><AddrLine2/><AddrLine3/><AddrLine4/><AddrLine5/><PostCde/><CtryCde/></Addr><Contact><HomePhoNum/><OfcePhoNum/><MoblPhoNum/><EmailAddr/></Contact></PerslDetl><IdDoc2><TypeCde>I</TypeCde><Num>440601197003190026</Num></IdDoc2><RaceCde/><NatlCde>CN</NatlCde><CtryOfResCde>XX</CtryOfResCde><MarStatCde>M</MarStatCde><Addr2ndLang><AddrLine1>CN HSBC 200-007995 ADDRESS P1</AddrLine1><AddrLine2/><AddrLine3/><AddrLine4/><AddrLine5/><PostCde>510000</PostCde><CtryCde/></Addr2ndLang><Addr2><AddrLine1/><AddrLine2/><AddrLine3/><AddrLine4/><AddrLine5/><PostCde/><CtryCde/></Addr2><Work><OcptCde>02</OcptCde><Posn/></Work><WldCheckInd>false</WldCheckInd><CustTypeCde>B</CustTypeCde><AlsoPlcyOwnrInd>false</AlsoPlcyOwnrInd><CustRecId>200007995</CustRecId><NafInfo><InsAmt><Crncy/><Amt>0</Amt></InsAmt><Incm><Crncy/><Amt>0</Amt></Incm><Expn><Crncy/><Amt>0</Amt></Expn><Aset><Crncy/><Amt>0</Amt></Aset><Liab><Crncy/><Amt>0</Amt></Liab></NafInfo></PlcyOwnr></PlcyOwnrList><LifeInsList><LifeIns/></LifeInsList><Billing><InitPymt><Dda><AvailAcctList><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995410</AcctNum><AcctType>SAV</AcctType><Crncy>AUD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995411</AcctNum><AcctType>SAV</AcctType><Crncy>HKD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995407</AcctNum><AcctType>SAV</AcctType><Crncy>EUR</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995408</AcctNum><AcctType>SAV</AcctType><Crncy>GBP</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995406</AcctNum><AcctType>SAV</AcctType><Crncy>USD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995427</AcctNum><AcctType>SAV</AcctType><Crncy>USD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995409</AcctNum><AcctType>SAV</AcctType><Crncy>JPY</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>820013118050</AcctNum><AcctType>CIF</AcctType><Crncy>CNY</Crncy></AvailAcct></AvailAcctList><DdaAcct/></Dda></InitPymt><SubqPymt><Dda><AvailAcctList><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995410</AcctNum><AcctType>SAV</AcctType><Crncy>AUD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995411</AcctNum><AcctType>SAV</AcctType><Crncy>HKD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995407</AcctNum><AcctType>SAV</AcctType><Crncy>EUR</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995408</AcctNum><AcctType>SAV</AcctType><Crncy>GBP</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995406</AcctNum><AcctType>SAV</AcctType><Crncy>USD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995427</AcctNum><AcctType>SAV</AcctType><Crncy>USD</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>200007995409</AcctNum><AcctType>SAV</AcctType><Crncy>JPY</Crncy></AvailAcct><AvailAcct><BankCde>006000</BankCde><AcctNum>820013118050</AcctNum><AcctType>CIF</AcctType><Crncy>CNY</Crncy></AvailAcct></AvailAcctList><DdaAcct/></Dda></SubqPymt></Billing><GroupMember>MELI</GroupMember><GoalKey><ArrangementIdentifierFinancialPlanning>30150</ArrangementIdentifierFinancialPlanning><GoalSequenceNumber>3695</GoalSequenceNumber></GoalKey><ProdCde>@MY</ProdCde><PlanDetl><HfiProdCde>@MY</HfiProdCde><SumIns><Crncy>CNY</Crncy></SumIns></PlanDetl><PlcyDetl/><InvstDetl><InvstRiskPrfl><RiskInd1>false</RiskInd1></InvstRiskPrfl></InvstDetl><OfceUse><AgtCmssnList><AgtCmssn><BrnchCde/><StafNum>43367024</StafNum></AgtCmssn></AgtCmssnList><StafId>43367024</StafId><StafCity/><BeEntity/></OfceUse></Proposal>";
			ByteArrayInputStream in= null;
			Map<String,String> map = null;
//			String lineData = null;
//			str = "";
			try {
				sax = new SAXReader();
//				reader = new FileReader("D:/gsb.xml");
//				buff = new BufferedReader(reader);
//				while((lineData = buff.readLine()) != null){
//					str += lineData.trim();
//				}
//				System.out.println(str);
				in = new ByteArrayInputStream(str.getBytes("UTF-8"));
				Document doc = sax.read(in);
				Element root = doc.getRootElement();
				Element PlcyOwnrList = root.element("PlcyOwnrList");
				Element PlcyOwnr = PlcyOwnrList.element("PlcyOwnr");
				String CustRecId = PlcyOwnr.elementText("CustRecId");
				map = new HashMap<String, String>();
				map.put("CustRecId", CustRecId);
				Element GoalKey = root.element("GoalKey");
				String GoalId = GoalKey.elementText("ArrangementIdentifierFinancialPlanning");
				map.put("GoalId", GoalId);
				Element OfceUse = root.element("OfceUse");
				String StafId = OfceUse.elementText("StafId");
				map.put("StafId", StafId);
//				System.out.println(StafId);
				String ProdCde = root.elementText("ProdCde");
				map.put("ProdCde", ProdCde);
				return map;
//				System.out.println(ProdCde);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
//				if(buff != null){
//					try {
//						buff.close();
//					} catch (IOException e) {
//						buff = null;
//					}
//				}
//				if(reader != null){
//					try {
//						reader.close();
//					} catch (IOException e) {
//						reader = null;
//					}
//				}
				
			}
		return null;
		 
	 }

	 
	 public static void main(String[] args) throws UnsupportedEncodingException {
		 String decode = decode("5rWL6K+V");
		 System.out.println(decode);
	}
}
