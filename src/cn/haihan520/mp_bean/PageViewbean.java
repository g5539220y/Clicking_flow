package cn.haihan520.mp_bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class PageViewbean implements Writable{
     private String Session;
     private String Time;
     private int Step;
     private String View_time;
     private String url;
     private String user;
     public PageViewbean() {} 

	public PageViewbean(String session, String time, int step, String view_time, String url, String user) 
	{
		this.Session = session;
		this.Time = time;
		this.Step = step;
		this.View_time = view_time;
		this.url = url;
		this.user = user;
	}

	public String getSession() {
		return Session;
	}

	public void setSession(String session) {
		Session = session;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public int getStep() {
		return Step;
	}

	public void setStep(int step) {
		Step = step;
	}

	public String getView_time() {
		return View_time;
	}

	public void setView_time(String view_time) {
		View_time = view_time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return Session + "	" + user+"	" + Time + "	" + Step + "	" + View_time
				+ "	" + url;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		  Session=in.readUTF();
		     Time=in.readUTF();
		     Step=in.readInt();
		View_time=in.readUTF();
		      url=in.readUTF();
		     user=in.readUTF();
			}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(Session);
		out.writeUTF(Time);
		out.writeInt(Step);
		out.writeUTF(View_time);
		out.writeUTF(url);
		out.writeUTF(user);
		
	}    
}