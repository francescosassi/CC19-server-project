package server;

public class DetailsResponse extends JSONResponse{
    private String ipAddress;
    private String portNumber;

    public DetailsResponse(long id, String type, String ipAddress, String portNumber) {
        super(id, type);
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

}
