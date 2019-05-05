package server;

public class HealthResponse extends JSONResponse {
    private boolean healthy;

    public HealthResponse(long id, String type, boolean healthy) {
        super(id, type);
        this.healthy = healthy;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
