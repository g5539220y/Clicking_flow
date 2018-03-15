package cn.haihan520.mp_bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 * ��Դ��ĸ����ֶ�
 * @��Ȧ����
 *
 */

public class STbean implements Writable{
	private String IPaddress;      //IP��ַ 
	private String Time;           //����ʱ��
    private String Session;        //�Ự
    private String Url;            //���ʵ���Դ�б�
    private String Referal;        //��ע���ͺš��������
    public STbean() {}
    public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getIPaddress() {
		return IPaddress;
	}
	public void setIPaddress(String iPaddress) {
		IPaddress = iPaddress;
	}
	public String getSession() {
		return Session;
	}
	public void setSession(String session) {
		Session = session;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
  	Url = url;
	}
	public String getReferal() {
		return Referal;
	}
	public void setReferal(String referal) {
		Referal = referal;
	}
	
	
	@Override
	public String toString() {
		return  Time + "	" + Session + "	" + Url
				+ "	" + Referal;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		IPaddress=in.readUTF();	
		Time     =in.readUTF();			
		Session  =in.readUTF();
		Url      =in.readUTF();
		Referal  =in.readUTF();
		
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(IPaddress);
		out.writeUTF(Time);		
		out.writeUTF(Session);
		out.writeUTF(Url);
		out.writeUTF(Referal);
		
	}

  
}
