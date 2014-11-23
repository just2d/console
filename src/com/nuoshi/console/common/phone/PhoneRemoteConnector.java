package com.nuoshi.console.common.phone;

import java.io.StringReader;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class PhoneRemoteConnector {
	/*
	 * WebService 通讯接口
	 */

	private OMElement createClient(String methodName,
			Map<String, String> paramName) throws Exception {
		Options options = new Options();
		options.setTo(PhoneConstants.TARGET_EPR);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		options.setProperty(HTTPConstants.CHUNKED, "false");
		options.setAction("http://tempuri.org/" + methodName);
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		sender.addHeader(getHead(PhoneConstants.LOGIN_USER, PhoneConstants.LOGIN_PWD));
		OMElement ome = sender.sendReceive(getBody(methodName, paramName));
		OMElement result = ome.cloneOMElement();
		sender.cleanupTransport();
		sender.cleanup();

		return result;
	}

	private OMElement getHead(String user, String password) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
		OMElement method = fac.createOMElement("Header", omNs);
		OMElement login = fac.createOMElement("LoginName", omNs);
		login.setText(user);
		method.addChild(login);
		OMElement pwd = fac.createOMElement("Pwd", omNs);
		pwd.setText(password);
		method.addChild(pwd);

		return method;
	}

	private OMElement getBody(String methodName, Map<String, String> paramName) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
		OMElement method = fac.createOMElement(methodName, omNs);
		if (paramName != null)
			for (String name : paramName.keySet()) {
				OMElement nmethod = fac.createOMElement(name, omNs);
				nmethod.setText(paramName.get(name));
				method.addChild(nmethod);
			}

		return method;
	}

	/**
	 * 调用API
	 * 
	 * @param api
	 * @param param
	 * @return
	 */
	public String callAPI(String api, Map<String, String> param) {
		try {
			OMElement result = createClient(api, param);
			if (result != null) {
				OMElement childElement = result
						.getFirstChildWithName(new QName("http://tempuri.org/",
								"exceptionInfo"));
				if (childElement != null
						&& !childElement.getText().equalsIgnoreCase("")) {
					System.out.println("!ERROR: " + childElement.getText());
					return null;
				} else {
					childElement = result.getFirstChildWithName(new QName(
							"http://tempuri.org/", api + "Result"));
					if (childElement != null) {
						System.out.println("SUCC: " + childElement.getText());
						return childElement.getText();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析吉亚返回的XML
	 * 
	 * @param xmlstring
	 * @param hasRoot
	 * @return
	 */
	public Element parseXml(String xmlstring, boolean hasRoot) {
		String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		Element root = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			if (hasRoot) {
				xmlstring = strTitle + xmlstring;
			} else {
				xmlstring = strTitle + "<root>" + xmlstring + "</root>";
			}

			Document document = builder.build(new InputSource(new StringReader(
					xmlstring)));
			root = document.getRootElement();
			/*
			 * 产品环境上按非XML返回时，可能需要这段 List<Element> lst = root.getChildren();
			 * if(lst.size() > 0) { root = lst.get(0); lst = root.getChildren();
			 * if(lst.size() > 1) { root = lst.get(1); lst = root.getChildren();
			 * if(lst.size() > 0) root = (Element) root.getChildren().get(0); }
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
}
