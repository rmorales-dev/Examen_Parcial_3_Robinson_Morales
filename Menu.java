import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Validaciones v = new Validaciones();
        Metodos m = new Metodos();
        Queue<ObjVenta> cola = new LinkedList<>();
        boolean continuar = true;
        while (continuar) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Bienvenidos al servicio PostVenta Nacho lee 3");
            System.out.println("Que desea realizar");
            System.out.println("1) Registrar una solicitud ");
            System.out.println("2) Consultar los clientes que estan esperando ");
            System.out.println("3) Llamar al siguiente cliente ");
            System.out.println("4) Marcar un cliente como atendido");
            System.out.println("5) Cancelar un turno");
            System.out.println("6) Consultar numero de personas están esperando");
            System.out.println("7) Actualizar informacion adicional ");
            System.out.println("8) Actualizar tipo de trámite de un cliente");
            System.out.println("9) Salir");
            int opt = v.ValidarEntero(sc);
            System.out.println("------------------------------------------------------------------------");
            switch (opt) {
                case 1:
                    m.IngresarCliente(cola, m, v, sc);
                    break;
                case 2:
                    System.out.println(m.MostrarClientesEsperando(cola));
                    break;
                case 3:
                    System.out.println(m.LlamarSiguiente(cola));
                    break;
                case 4:
                    System.out.println(m.Atender(cola));
                    break;
                case 5:
                    System.out.println(m.CancelarTurno(cola, sc, v));
                    break;
                case 6:
                    System.out.println(m.ConsultarPersonasEsperando(cola));
                    break;
                case 7:
                    System.out.println(m.ActualizarInformacionAdicional(cola, sc, v));
                    break;

                case 8:
                    System.out.println(m.ActualizarTipoTramite(cola, sc, v));
                    break;
                case 9:
                    System.out.println("Gracias por utilizar el sistema de PostVenta Nacho lee 3");
                    System.out.println("Esperemos Robinson Morales saque un 5 en este parcial :D");
                    continuar = false;
                    break;
                default:
                    System.out.println("esta opcion no existe intente una opcion valida");
                    break;
            }
        }
    }
}