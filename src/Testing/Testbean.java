package Testing;

public class Testbean {
     int id;
     String name;
	@Override
	public String toString() {
		return "Testbean [id=" + id + ", name=" + name + "]";
	}
	public int getId() {
		return id;
	}
	public Testbean() {

	}
	public Testbean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
