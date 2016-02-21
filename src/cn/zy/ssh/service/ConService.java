package cn.zy.ssh.service;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ConService {
	
	private String host;
	private int port = 22;
	private String username;
	private Connection con;
	
	public ConService(String host, int port, String username) throws IOException{
		this.host = host;
		this.port = port;
		this.username = username; 
		
		this.con = new Connection(host, port);
		con.connect();
	}
	
	
	


	public Session getSession() throws IOException{
		
		return con.openSession();
	}


	public boolean login(String pass) throws IOException {
		
		return con.authenticateWithPassword(username, pass);
		
	}
	

}
