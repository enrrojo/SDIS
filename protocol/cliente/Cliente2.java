package protocol.cliente;

import protocol.common.Primitiva;
import protocol.common.MensajeProtocolo;
import protocol.common.MalMensajeProtocoloException;

public class Cliente2 {
  
  static java.io.ObjectInputStream ois = null;
  static java.io.ObjectOutputStream oos = null;

  public static void main(String[] args) throws java.io.IOException {
    String host = "LocalHost";

    java.net.Socket sock = new java.net.Socket(host, 2000);
    try {
      // Creación de los canales de serialización de objetos      
      oos = new java.io.ObjectOutputStream(sock.getOutputStream());
      ois = new java.io.ObjectInputStream(sock.getInputStream());

      // Sin teclado, probemos las primitivas por programa
      System.out.println("Pulsa <Enter> para comenzar"); System.in.read();
      // INICIO Escenario 1
      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.HELLO, "Pedro"));

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.PUSH, "010", "abc"));

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.PULL_NOWAIT, "112"));

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.PULL_NOWAIT, "010"));

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.PULL_WAIT, "010"));

      // a estas alguras algún cliente externo debería insertar un mensaje en la cola
      // FIN Esceniario 1
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
  /* Prueba una interacción de escritura y lectura con el servidor */
  static void pruebaPeticionRespuesta(MensajeProtocolo mp) throws java.io.IOException,
          MalMensajeProtocoloException, ClassNotFoundException {
    System.out.println("> "+mp);
    oos.writeObject(mp);
    System.out.println("< "+(MensajeProtocolo) ois.readObject());
    System.out.println("Pulsa <Enter>"); System.in.read();
  }
}