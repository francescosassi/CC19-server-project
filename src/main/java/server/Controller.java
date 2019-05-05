package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

	private static boolean isHealthy = true;
	private static List<String> memory = new ArrayList<>();
	private static final String characterEncoding = "UTF-8";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
	private Environment environment;

	@RequestMapping("/encrypt")
	public EncryptResponse encrypt(@RequestParam(value="text", defaultValue="Lorem ipsum") String text,
								   @RequestParam(value="keySize", defaultValue="512") int keySize)
			throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
		long startTime = System.currentTimeMillis();

    	// generate public and private keys
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keySize);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		PublicKey pubKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		byte[] encrypted = cipher.doFinal(text.getBytes());
		long endTime = System.currentTimeMillis();

		return new EncryptResponse(
				counter.incrementAndGet(),
			"RSA",
				text,
				keySize,
				encrypted,
				endTime - startTime
			);
	}


    @RequestMapping("/health")
    public HealthResponse health() {
    	if(isHealthy) {
    		return new HealthResponse(counter.incrementAndGet(),"healthCheck",true);
    	}else {
			throw new BadHealthException();
    	}
    }

	@ExceptionHandler(value = BadHealthException.class)
	private ResponseEntity<Object> exception(BadHealthException exception) {
		return new ResponseEntity<>(new HealthResponse(counter.incrementAndGet(),"healthCheck",false), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
    @RequestMapping("/health/flip")
    public JSONResponse flipHealth() {
    	isHealthy = !isHealthy;
    	return new JSONResponse(counter.incrementAndGet(), "HealthSwitched");
    }

    @RequestMapping("/ram")
    public JSONResponse ram(@RequestParam(value="number", defaultValue="10000000") int number) {
    	try {
	    	String size = new String(new char[number]);
	    	memory.add(size);
	    	return ramInfo();
    	} catch(OutOfMemoryError e) {
    		return new JSONResponse(counter.incrementAndGet(), "OutOfMemory Exception");
    	}
    }
    
    @RequestMapping("/ram/info")
    public RamResponse ramInfo() {
		return getRamInfo();
    }
    
    @RequestMapping("/ram/clear")
    public RamResponse ramClear() {
    	memory.clear();
    	return getRamInfo();
    }
    
    @RequestMapping("/write")
    public WriteResponse write(@RequestParam(value="filename", defaultValue="text") String filename, @RequestParam(value="lines", defaultValue="100000") long lines)
			throws FileNotFoundException, UnsupportedEncodingException {
		writeFile(filename, lines);
		return new WriteResponse(counter.incrementAndGet(),"writeFile", filename);
    }
    
    @RequestMapping("/read")
    public ReadResponse read(@RequestParam(value="filename", defaultValue="text") String filename)
			throws IOException {
    	return new ReadResponse(counter.incrementAndGet(),"readFile", filename, readFile(filename));
    }

	@RequestMapping("/details")
	private  JSONResponse details() throws UnknownHostException, SocketException {

		return new DetailsResponse(counter.incrementAndGet(), "serverInfo", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("local.server.port"));
	}
    
    private RamResponse getRamInfo() {
		Runtime runtime = Runtime.getRuntime();

		long freeMemory = runtime.freeMemory();
		long allocatedMemory = runtime.totalMemory();
		long maxMemory = runtime.maxMemory();
		long totalFreeMemory = (freeMemory + (maxMemory - allocatedMemory));

		return new RamResponse(
				counter.incrementAndGet(),
				"Ram",
				freeMemory / 1024,
				allocatedMemory / 1024,
				maxMemory / 1024,
				totalFreeMemory / 1024
				);
    }
    
    private void writeFile(String fileName, long lines)
			throws FileNotFoundException, UnsupportedEncodingException {
    	int i = 0;
    	PrintWriter writer;

		writer = new PrintWriter(fileName, characterEncoding);
		while( i < lines) {
			writer.println("Lorem Ipsum");
			i++;
		}
		writer.close();

    }
    
    private long readFile(String filename) throws IOException {
		String line = null;
		long i = 0;
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while((line = bufferedReader.readLine()) != null) {
			i++;
		}
		bufferedReader.close();
		return i;
    }

	
}
