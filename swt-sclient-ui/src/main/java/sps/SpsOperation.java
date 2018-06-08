package sps;

import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SpsOperation {

	public ArrayList<String>  procedure = new ArrayList<String>();
	public ArrayList<String>  task = new ArrayList<String>(); 
	
	public String access = new String();
	
	private String spsUrl;
	private SpsClient owner;
	
	private int start = 0;
	private SAXReader reader = new SAXReader();
	
	
	public SpsOperation(String url, SpsClient owner) {
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
		        String a=null;
				byte[] data1 = new byte[in.available()];
				in.read(data1);
				//转成字符串
				a = DocumentHelper.parseText(new String(data1)).asXML();	
			if(conn.getResponseCode() ==200) {
				JOptionPane.showMessageDialog(owner, "连接成功");
				owner.setTextField("response:" + "/n" + a);//请求返回的数据
				start = 1;
			} 
			else {
				JOptionPane.showMessageDialog(owner, "连接失败");
			} 

			Document document = reader.read(new ByteArrayInputStream(a
				     .getBytes("utf-8")));
			Element root = document.getRootElement();
	        Element contents = root.element("contents");
	        Element spsContents = contents.element("SPSContents");
	        List<Element> offerings = spsContents.elements("offering");
	        
	        for (int i = 0; i < offerings.size(); i++) {
				Element offering = offerings.get(i);
				Element sensorOfferring = offering.element("SensorOffering");
				Element eleprocedure = sensorOfferring.element("procedure");
				procedure.add(eleprocedure.getText());	
				
			}
	        ((DescribeSensorPanel) owner.paramsPanel.getComponent(0)).freshItem(procedure);
	        ((SubmitPanel) owner.paramsPanel.getComponent(1)).freshItem(procedure);
	        ((GetAccessPanel) owner.paramsPanel.getComponent(3)).freshItem(procedure);
		}
		else {
			JOptionPane.showMessageDialog(owner, "已连接");
		}
	}
	
	public void describeSensor() throws DocumentException, IOException {
		
		if (start == 1) {
			String selectProc = (String) owner.describeSensorPanel.procedure.getSelectedItem();
			InputStream rInputStream = this.getClass().getResourceAsStream("DesSen.xml");

			Document document = reader.read(rInputStream);
			
			Element root = document.getRootElement();
			Element eleProc = root.element("procedure");
			eleProc.setText(selectProc);
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
	
	public void submit() throws DocumentException, IOException {
		
		if (start == 1) {
			String param = new String(owner.submitPanel.frequence.getSelectedItem() + ", " + owner.submitPanel.count.getSelectedItem() + ", test");
			InputStream rInputStream = this.getClass().getResourceAsStream("Submit.xml");
			Document document = reader.read(rInputStream);
			Element root = document.getRootElement();
			Element values = root.element("taskingParameters").element("ParameterData").element("values");
			values.setText(param);
			Element proc = root.element("procedure");
			proc.setText((String)owner.submitPanel.procedure.getSelectedItem());
			
			String request = document.asXML();
			
			URL url = new URL(spsUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);//设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			conn.connect();
			 
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		    out.writeBytes(request); //写入请求的字符串
		    out.flush();
		    out.close();
		        
		    InputStream in=conn.getInputStream();
			byte[] data1 = new byte[in.available()];
			in.read(data1);
			String response = DocumentHelper.parseText(new String(data1)).asXML();	
			
			if (conn.getResponseCode() == 200) {
				owner.setTextField("response:" + "/n" + response);//请求返回的数据
			}
			else {
				JOptionPane.showMessageDialog(owner, "未连接到SPS");
			}
			
			Document document2 = DocumentHelper.parseText(response);
			Element root2 = document2.getRootElement();
			Element res = root2.element("result");
			Element sr = res.element("StatusReport");
			Element tas = sr.element("task");			
			String s = tas.getText();
			task.add(s);
			((GetStatuePanel) owner.paramsPanel.getComponent(2)).freshItem(task);
		}
		
		else if (start == 0) {
			JOptionPane.showMessageDialog(owner, "未连接到SPS");
		}
	}
	
	public void getStatus() throws DocumentException, IOException {
		
		if (start == 1) {
			String selectTask = (String) owner.getStatuePanel.task.getSelectedItem();
			InputStream rInputStream = this.getClass().getResourceAsStream("GetSta.xml");
			Document document = reader.read(rInputStream);
			Element root = document.getRootElement();
			Element task = root.element("task");
			task.setText(selectTask);
			
			String request = document.asXML();
			System.out.print(request);
			URL url = new URL(spsUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);//设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			conn.connect();
			 
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		    out.writeBytes(request); //写入请求的字符串
		    out.flush();
		    out.close();
		        
		    InputStream in=conn.getInputStream();
			byte[] data1 = new byte[in.available()];
			in.read(data1);
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
	
	public void  getAccess() throws DocumentException, HeadlessException, IOException {
		
		if (start == 1) {
			String selectProc = (String) owner.getAccessPanel.procedure.getSelectedItem();
			InputStream rInputStream = this.getClass().getResourceAsStream("GetAccess.xml");
			Document document = reader.read(rInputStream);
			Element root = document.getRootElement();
			Element tar = root.element("target");
			Element proc = tar.element("procedure");
			
			proc.setText(selectProc);
			
			String request = document.asXML();
			URL url = new URL(spsUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);//设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			conn.connect();
			 
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		    out.writeBytes(request); //写入请求的字符串
		    out.flush();
		    out.close();
		        
		    InputStream in=conn.getInputStream();
			byte[] data1 = new byte[in.available()];
			in.read(data1);
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
