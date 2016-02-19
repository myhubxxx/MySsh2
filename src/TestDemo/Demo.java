package TestDemo;
import java.io.IOException;
import java.io.InputStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class Demo {
	
	public static void main(String[] args) throws IOException {
		
		// create the connection
		Connection con = new Connection("192.168.88.110", 22 ); 
		con.connect();
		boolean flag = false;
		try { // try start
			if( (flag = con.authenticateWithPassword("xiaoyun", "root")) ){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} //  end try
		
		Session session = null;
		
		try { // start try
			if((session = con.openSession()) == null){
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}//  end try
		
		session.execCommand("ls");
		
		InputStream in = new StreamGobbler( session.getStdout() ); 
		String strIn = getStrStream(in, "utf-8");
		InputStream in2 = new StreamGobbler( session.getStderr() );
		String strIn2 = getStrStream(in2, "utf-8");
		
		System.out.println(strIn);
		System.out.println(strIn2);
		
		
	}
	
	public static String getStrStream(InputStream in, String charset) throws IOException{
		StringBuffer sb = new StringBuffer(); 
		byte[] bytes = new byte[1024];
		while( in.read(bytes) != -1){
			sb.append( new String(bytes, charset));
		}
		
		return sb.toString();
	}
	
	
	
	

}
