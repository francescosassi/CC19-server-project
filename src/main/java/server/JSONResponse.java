package server;

public class JSONResponse {
	private final long id;
	private final String type;
	
	public JSONResponse(long id, String type){
		this.id = id;
		this.type = type;
	}
	
	
	public long getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}	
}
