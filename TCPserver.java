package Tarea1Chambi;
import java.io.*;
import java.net.*;
public class TCPserver {

	public static void main(String[] args) {
		ServerSocket serv = null;
		DataInputStream in;
		DataOutputStream out;
		Socket Cliente_socket=null;
		String menu = "\tMENU\n1. Guardar\n2. Verificar\n3. Salir";
		try {
			serv = new ServerSocket(9876);
			System.out.println("****Servidor Iniciado****");
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			while(true) {
				Cliente_socket = serv.accept();
				System.out.println("--Cliente conectado--");
				in = new DataInputStream(Cliente_socket.getInputStream());
				out = new DataOutputStream(Cliente_socket.getOutputStream());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
