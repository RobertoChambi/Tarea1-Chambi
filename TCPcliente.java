package Tarea1Chambi;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
public class TCPcliente {
	
	public static DataInputStream in;
	public static DataOutputStream out;
	public static String menu;
	public static Scanner lee;
	public static Socket ser=null;
	public static void main(String[] args) {
		//Socket s = null;
		String Nombre_servidor = "localhost";
		lee = new Scanner(System.in);
		try {
			ser = new Socket(Nombre_servidor, 9876);
			in = new DataInputStream(ser.getInputStream());
			out = new DataOutputStream(ser.getOutputStream());
			Menu();			
		}catch (Exception e) {
			System.err.println("Error : "+ e);		
		}
	}
	
	public static void Menu() {
		try {
			String mensaje = in.readUTF();
			System.out.println(mensaje);
			String res = lee.nextLine();
			out.writeUTF(res);
			int d = Integer.parseInt(res);
			atencion(d);
		} catch (Exception e) {
			System.err.println("Error : "+ e);		
		}
	}
	public static void atencion(int op) {
		try {
			switch (op) {
			case 1:	//GUARDAR
				System.out.println("Intro nombre");
				String nombre = lee.nextLine();
				System.out.println("Intro CI.");
				String ci = lee.nextLine();
				out.writeUTF(nombre);
				out.writeUTF(ci);
				String respues = in.readUTF();
				System.out.println(respues);
				Menu();
				break;
			case 2:	//VERIFICAR
				System.out.println("Intro Nro. de CI: ");
				String cc = lee.nextLine();
				out.writeUTF(cc);
				String rr = in.readUTF();
				System.out.println(rr);
				Menu();
				break;
			case 3:	//SALIR
				System.out.println("Fin de la conexión...");
				ser.close();
				break;
			default:
				ser.close();
				break;
			}
			
		}catch (Exception e) {
			System.err.println("Error : "+ e);		
		}
	}
}
