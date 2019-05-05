package server;

public class EncryptResponse extends JSONResponse {
    public String input;
    public byte[] encrypted;
    public int keySize;
    public long time;

    public EncryptResponse(long id, String type, String input,int keySize, byte[] encrypted, long time) {
        super(id, type);
        this.input = input;
        this.keySize = keySize;
        this.encrypted = encrypted;
        this.time = time;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public byte[] getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(byte[] encrypted) {
        this.encrypted = encrypted;
    }
}
