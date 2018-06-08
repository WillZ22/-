package sos;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import sps.DescribeSensorPanel;
import sps.GetAccessPanel;
import sps.SubmitPanel;

public class SosOperation {

	public ArrayList<String>  procedures = new ArrayList<String>();
	public String access = new String();
	
	private String spsUrl;
	private SosClient owner;
	
	private int start = 0;
	private SAXReader reader = new SAXReader();
	
	
	public SosOperation(String url, SosClient owner) {
		this.spsUrl = url;
		this.owner = owner;
	}
	
	public void getCapbilities() throws IOException, DocumentException {
		
		if (start == 0) {
			InputStream input = this.getClass().getResourceAsStream("GetCap.xml");
		    byte[] data;
		    
			data = new byte[input.available()];
	        input.read(data);
	        //把XML的数据转成字符串
	        String str=new String(data);
	        byte[] bb=str.getBytes();
	        //请求地址
			URL url = new URL(spsUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			//conn.setConnectTimeout(5 * 1000);//设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			//conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
			conn.connect();
			 
			DataOutputStream out = new DataOutputStream(conn
		                .getOutputStream());
		        out.writeBytes(str); //写入请求的字符串
		        out.flush();
		        out.close();
		        
		        InputStream in=conn.getInputStream();
		        
				byte[] data1 = new byte[in.available()];
				in.read(data1);
				//转成字符串
				String response = DocumentHelper.parseText(new String(data1)).asXML();	
			if(conn.getResponseCode() ==200) {
				JOptionPane.showMessageDialog(owner, "连接成功");
				owner.setTextField("response:" + "/n" + response);//请求返回的数据
				start = 1;
			} 
			else {
				JOptionPane.showMessageDialog(owner, "连接失败");
			} 

			Document document = reader.read(new ByteArrayInputStream(response
				     .getBytes("utf-8")));
			Element root = document.getRootElement();
			List<Element> offerings = root.element("contents").element("Contents").elements("offering");
						
			for (int i = 0; i < offerings.size(); i++) {
				Element offering = offerings.get(i);
				Element ObservationOffering = offering.element("ObservationOffering");
				Element procedure = ObservationOffering.element("procedure");
				procedures.add(procedure.getText());	
			}
			
	        ((GetObeservationPanel) owner.paramsPanel.getComponent(0)).freshItem(procedures);
		}
		else {
			JOptionPane.showMessageDialog(owner, "已连接");
		}
	}	
		
	public void getObservation () throws DocumentException, IOException {
		if (start == 1) {
			String selectProc = (String) owner.getObeservationPanel.procedure.getSelectedItem();
			InputStream rInputStream = this.getClass().getResourceAsStream("GetObs.xml");

			Document document = reader.read(rInputStream);
			
			Element root = document.getRootElement();
			Element procedure = root.element("procedure");
			procedure.setText(selectProc);
			String request = document.asXML();
			
			URL url = new URL(spsUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);//设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			//conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
			conn.connect();
			 
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		    out.writeBytes(request); //写入请求的字符串
		    out.flush();
		    out.close();
		        
		    InputStream in=conn.getInputStream();
			byte[] data1 = new byte[in.available()];
			in.read(data1);
			//转成字符串
			String response = DocumentHelper.parseText(new String(data1)).asXML();
			
			if (conn.getResponseCode() == 200) {
				owner.setTextField("response:" + "/n" + response);//请求返回的数据
			}
			else {
				JOptionPane.showMessageDialog(owner, "未连接到SPS");
			}
		}
		else if (start == 0) {
			JOptionPane.showMessageDialog(owner, "未连接到SPS");
		}
	}
	
	
}
