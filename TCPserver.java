package Tarea1Chambi;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class TCPserver {
	
	public static DataInputStream in;
	public static DataOutputStream out;
	public static String archName = "C:/Users/Lenovo/Documents/TareaLab/TareaGit/database.dat";
	public static Socket cli=null;
	private static Cliente bb;
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		crear();
		ServerSocket serv = null;
		try {
			serv = new ServerSocket(9876);
			System.out.println("****Servidor Iniciado****");
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			while(true) {
				cli = serv.accept();
				System.out.println("--Cliente conectado--");
				in = new DataInputStream(cli.getInputStream());
				out = new DataOutputStream(cli.getOutputStream());
				Menu();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void Menu() {
		
		String menu = "\tMENU\n1. Guardar\n2. Verificar\n3. Salir";
		try {
			out.writeUTF(menu);
			String resp =in.readUTF();
			int d = Integer.parseInt(resp);
			atencion(d);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
	}
	public static void atencion(int n) {
		try {
			switch (n) {
			case 1: //GUARDAR
				try {
					String name = in.readUTF();
					String id = in.readUTF();
					int puerto = cli.getPort();
					Calendar c1 = new GregorianCalendar();
					String fe = Integer.toString(c1.get(Calendar.DATE))+"/"+Integer.toString(c1.get(Calendar.MONTH)+1)
								+ "/"+Integer.toString(c1.get(Calendar.YEAR));
					String Hora = Integer.toString(c1.get(Calendar.HOUR_OF_DAY))+" : "+Integer.toString(c1.get(Calendar.MINUTE));
					Cliente nuevo = new Cliente(name, id, fe, Hora, puerto);
					agregar(nuevo);
					out.writeUTF("Su registro se realizo con éxito...");
					Menu();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2: //VERIFICAR
				try {
					String ide=in.readUTF();
					String mensaje = verifica(ide);
					out.writeUTF(mensaje);
					Menu();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:	//SALIR
				System.out.println("Cliente desconecado...");
				break;
			default:
				listar();
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void agregar(Cliente p) {
		
		ObjectOutputStream archEs = null;
		try{
            Cliente vec[] = archVector();
            archEs = new ObjectOutputStream(new FileOutputStream(archName));	
            for (int i = 0; i < vec.length; i++) archEs.writeObject(vec[i]);
            archEs.writeObject(p);
                    
            archEs.close();
		}catch(Exception e){
			System.out.print("\n FIN ADICIONA");
		}
	}
	@SuppressWarnings("finally")
	public static int nroArch() throws IOException {
		int c=0;
		ObjectInputStream archEs=null;
		try {
			archEs = new ObjectInputStream(new FileInputStream(archName));
			while(true){
              bb = new Cliente();
              bb = (Cliente)archEs.readObject();	
              c++;
           } 
	
		} catch (Exception e) {
			//System.out.print("\n TERMINO DE CONTAR    " + e.getMessage());
		}finally{
			archEs.close();
            return c;
		}
	}
	@SuppressWarnings("finally")
	public static Cliente[] archVector()throws ClassNotFoundException, IOException {
		Cliente vec[] = new Cliente[nroArch()];
		int i=0;
        ObjectInputStream archEs=null;
        try {
        	archEs = new ObjectInputStream(new FileInputStream(archName));
        	while(true){
                 bb = new Cliente();
                 bb = (Cliente)archEs.readObject();
                 //est.mostrar();
                 vec[i] = bb; 
                 i++;
            }
        } catch (Exception e) {
        	//System.out.print("\n FIN ARCHITOVEC    " + e.getMessage());
        }finally{
        	archEs.close();
        	return vec;
        }
	}
	public static void crear()throws ClassNotFoundException, IOException{
        try{
            File arch = new File(archName);
            if(!arch.exists()){
                // Si no exsite el archivo
                ObjectOutputStream archEs = new ObjectOutputStream(new FileOutputStream(archName));
                archEs.close();
            }
        }
        catch(Exception e){
            System.out.println("Error en la creacion del archivo");
        }	
	}

    @SuppressWarnings("finally")
	public static String verifica(String iden)throws ClassNotFoundException, IOException{
	ObjectInputStream archEs=null;
	String res="Usted no Se encuentra en la base de datos...";
		try {
			archEs = new ObjectInputStream(new FileInputStream(archName));
			while(true){
				bb = new Cliente();
				bb = (Cliente)archEs.readObject();
				if(iden.equals(bb.getCarnet())) {
					res = "Nombre: "+bb.getNombre()+"\nCI: "+bb.getCarnet()+"\nFecha de Registro: "+
							bb.getFecha()+" a horas: "+bb.getHora()+"\nPuerto: "+bb.getPuerto();
				}
			}
		} catch (Exception e) {
			System.out.print("." );
		}finally{
			archEs.close();
			return res;
		}
    }

    public static void listar()throws ClassNotFoundException, IOException{
	ObjectInputStream archEs=null;
		try {
			archEs = new ObjectInputStream(new FileInputStream(archName));
			while(true){
				bb = new Cliente();
				bb = (Cliente)archEs.readObject();
				System.out.println("----------------------------------------------------");
				System.out.println("Nombre: "+ bb.getNombre()+"\nCI: "+bb.getCarnet()
				+"\nFecha: "+bb.getFecha()+" hora: "+bb.getHora()+"\nPuerto: "+bb.getPuerto());
			}
	
		} catch (Exception e) {
			System.out.print("\n FIN LISTADO    " + e.getMessage());
		}finally{
			archEs.close();
		}
    }
}

class Cliente implements Serializable {
	private String nombre;
	private String carnet;
	private int puerto;
	private String fecha;
	private String hora;
	public Cliente(String name, String id, String fe, String h, int p) {
		nombre=name;
		carnet=id;
		fecha=fe;
		hora=h;
		puerto=p;
	}
	public Cliente() {	
	}
	public String getNombre(){
		return this.nombre;
	}
	public String getCarnet() {
		return this.carnet;
	}
	public String getFecha() {
		return this.fecha;
	}
	public String getHora() {
		return this.hora;
	}
	public int getPuerto() {
		return this.puerto;
	}
}