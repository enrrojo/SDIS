package protocol.cliente;

import protocol.common.Primitiva;
import protocol.common.MensajeProtocolo;
import protocol.common.MalMensajeProtocoloException;

public class Hello {
  final private int PUERTO = 2000;
  static java.io.ObjectInputStream ois = null;
  static java.io.ObjectOutputStream oos = null;

  public static void main(String[] args) throws java.io.IOException {

    String mensaje = "pepa";

    java.net.Socket sock = new java.net.Socket("LocalHost", 2000);
    try {
      oos = new java.io.ObjectOutputStream(sock.getOutputStream());
      ois = new java.io.ObjectInputStream(sock.getInputStream());

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.HELLO, mensaje));

    } catch (java.io.EOFException e) {
      System.err.println("Cliente: Fin de conexión.");
    } catch (java.io.IOException e) {
      System.err.println("Cliente: Error de apertura o E/S sobre objetos: "+e);
    } catch (MalMensajeProtocoloException e) {
      System.err.println("Cliente: Error mensaje Protocolo: "+e);
    } catch (Exception e) {
      System.err.println("Cliente: Excepción. Cerrando Sockets: "+e);
    } finally {
      ois.close();
      oos.close();
      sock.close();
    }
  }

  static void pruebaPeticionRespuesta(MensajeProtocolo mp) throws java.io.IOException,
	      MalMensajeProtocoloException, ClassNotFoundException {
    System.out.println("> "+mp);
    oos.writeObject(mp);
    System.out.println("< "+(MensajeProtocolo) ois.readObject());
  }
}