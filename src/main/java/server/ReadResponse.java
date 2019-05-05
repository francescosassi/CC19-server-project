package server;

public class ReadResponse extends JSONResponse{
    private String filename;
    private long nLine;

    public ReadResponse(long id, String type, String filename, long nLine) {
        super(id, type);
        this.filename = filename;
        this.nLine = nLine;
    }

    public long getnLine() {
        return nLine;
    }

    public void setnLine(long nLine) {
        this.nLine = nLine;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
