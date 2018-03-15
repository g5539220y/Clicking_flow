 package cn.haihan520.mp_bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class VisitPagebean implements Writable{
    private String Session;
    private String Start_Time;
    private String End_time;
    private String Start_page;
    private String End_page;
    private String Pages_num;
    private String IPaddress;
    private String step;
      
	public VisitPagebean() {}
	 
	public VisitPagebean(String session, String start_Time, String end_time, String start_page, String end_page,
			String pages_num, String iPaddress, String step) {
		this.Session = session;
		this.Start_Time = start_Time;
		this.End_time = end_time;
		this.Start_page = start_page;
		this.End_page = end_page;
		this.Pages_num = pages_num;
		this.IPaddress = iPaddress;
		this.step = step;
	}

	public String getSession() {
		return Session;
	}

	public void setSession(String session) {
		Session = session;
	}

	public String getStart_Time() {
		return Start_Time;
	}

	public void setStart_Time(String start_Time) {
		Start_Time = start_Time;
	}

	public String getEnd_time() {
		return End_time;
	}

	public void setEnd_time(String end_time) {
		End_time = end_time;
	}

	public String getStart_page() {
		return Start_page;
	}

	public void setStart_page(String start_page) {
		Start_page = start_page;
	}

	public String getEnd_page() {
		return End_page;
	}

	public void setEnd_page(String end_page) {
		End_page = end_page;
	}

	public String getPages_num() {
		return Pages_num;
	}

	public void setPages_num(String pages_num) {
		Pages_num = pages_num;
	}

	public String getIPaddress() {
		return IPaddress;
	}

	public void setIPaddress(String iPaddress) {
		IPaddress = iPaddress;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return Session+"	"+Start_Time + "	" + End_time+ "	" + Start_page + "	" + End_page + "	" + Pages_num + "	"
				+ IPaddress + "	" + step;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
	    this.Session= in.readUTF();
	    this.Start_Time= in.readUTF();
	    this.End_time= in.readUTF();
	    this.Start_page= in.readUTF();
	    this.End_page= in.readUTF();
	    this.Pages_num= in.readUTF();
	    this.IPaddress= in.readUTF();
	    this.step= in.readUTF();
		
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(Session);
		out.writeUTF(Start_Time);
		out.writeUTF(End_time);
		out.writeUTF(Start_page);
		out.writeUTF(End_page);
		out.writeUTF(Pages_num);
		out.writeUTF(IPaddress);
		out.writeUTF(step);		
	}
    
}
