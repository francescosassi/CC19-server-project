package server;

public class RamResponse extends JSONResponse {
    long freeMemory;
    long allocatedMemory;
    long maxMemory;
    long totalFreeMemory;

    public RamResponse(long id, String type, long freeMemory, long allocatedMemory, long maxMemory, long totalFreeMemory) {
        super(id, type);
        this.freeMemory = freeMemory;
        this.allocatedMemory = allocatedMemory;
        this.maxMemory = maxMemory;
        this.totalFreeMemory = totalFreeMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getAllocatedMemory() {
        return allocatedMemory;
    }

    public void setAllocatedMemory(long allocatedMemory) {
        this.allocatedMemory = allocatedMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public long getTotalFreeMemory() {
        return totalFreeMemory;
    }

    public void setTotalFreeMemory(long totalFreeMemory) {
        this.totalFreeMemory = totalFreeMemory;
    }
}
