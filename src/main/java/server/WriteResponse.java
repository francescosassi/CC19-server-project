package server;

public class WriteResponse extends JSONResponse{
    private String filename;

    public WriteResponse(long id, String type, String filename) {
        super(id, type);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
