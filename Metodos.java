import java.util.Queue;
import java.util.Scanner;

public class Metodos {
    private int siguienteTurno = 1;

    public Queue<ObjVenta> IngresarCliente(Queue<ObjVenta> cola, Metodos m, Validaciones v, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            ObjVenta o = new ObjVenta();
            System.out.println("Ingrese su numero de Cedula: ");
            o.setCedula(v.ValidarString(sc));
            System.out.println("Ingrese su nombre: ");
            o.setNombre(v.ValidarString(sc));
            System.out.println("Ingrese su edad");
            o.setEdad(v.ValidarEntero(sc));
            o.setNumTurno(m.ValidarTurno());
            o.setTipoTramite(m.TipoTramite(sc, v));
            System.out.println(
                    "Si tiene alguna informacion adicional que desea agregar, por favor ingrese la informacion, de lo contrario escriba 'ninguna':");
            o.setInformacionAdicional(v.ValidarString(sc));
            o.setEstado(1);
            cola.offer(o);

            System.out.println("Cliente registrado correctamente.");
            System.out.println("Su turno es: " + o.getNumTurno());
            System.out.println("Desea Agregar mas solicitudes 1) si , 2) no ");
            int opt = v.ValidarEntero(sc);
            while (opt < 1 || opt > 2) {
                System.out.println("Opción no válida. Seleccione 1 o 2.");
                opt = v.ValidarEntero(sc);
            }
            if (opt == 2) {
                System.out.println("Vuelve Pronto");
                continuar = false;
            }
        }
        return cola;
    }

    public int ValidarTurno() {
        int turno = siguienteTurno;
        siguienteTurno++;
        return turno;
    }

    public String TipoTramite(Scanner sc, Validaciones v) {
        String mensaje = "";
        System.out.println("Gracias por preferir a nacho lee 3, por favor elija su tramite");
        System.out.println("1) Cambios");
        System.out.println("2) Garantias");
        System.out.println("3) Devoluciones");
        int opt = v.ValidarEntero(sc);
        while (opt < 1 || opt > 3) {
            System.out.println("Opcion no valida eliga un valor entre 1 y 3");
            opt = v.ValidarEntero(sc);
        }
        switch (opt) {
            case 1:
                mensaje = "Cambios";
                break;
            case 2:
                mensaje = "Garantias";
                break;
            case 3:
                mensaje = "Devoluciones";
                break;
        }
        return mensaje;
    }

    public String ObtenerNombreEstado(int estado) {
        switch (estado) {
            case 1:
                return "Pendiente";
            case 2:
                return "Llamado";
            case 3:
                return "Atendido";
            case 4:
                return "Cancelado";
            default:
                return "Estado desconocido";
        }
    }

    public String MostrarClientesEsperando(Queue<ObjVenta> cola) {
        boolean hayPendientes = false;

        for (ObjVenta o : cola) {
            if (o.getEstado() == 1) {
                hayPendientes = true;
                System.out.println("-----------------------------");
                System.out.println("Turno: " + o.getNumTurno());
                System.out.println("Cedula: " + o.getCedula());
                System.out.println("Nombre: " + o.getNombre());
                System.out.println("Edad: " + o.getEdad());
                System.out.println("Tipo de trámite: " + o.getTipoTramite());
                System.out.println("Información adicional: " + o.getInformacionAdicional());
                System.out.println("Estado: " + ObtenerNombreEstado(o.getEstado()));

            }
        }
        if (!hayPendientes) {
            return "No hay clientes esperando.";
        }
        return "Clientes que están esperando mostrados correctamente.";
    }

    public String LlamarSiguiente(Queue<ObjVenta> cola) {
        ObjVenta siguiente = null;
        for (ObjVenta o : cola) {
            if (o.getEstado() == 1) {
                siguiente = o;
                break;
            }
        }
        if (siguiente == null) {
            return "No hay clientes pendientes para llamar.";
        }
        siguiente.setEstado(2);

        System.out.println("-----------------------------");
        System.out.println("Siguiente cliente:");
        System.out.println("Turno: " + siguiente.getNumTurno());
        System.out.println("Cedula: " + siguiente.getCedula());
        System.out.println("Nombre: " + siguiente.getNombre());
        System.out.println("Edad: " + siguiente.getEdad());
        System.out.println("Tipo de trámite: " + siguiente.getTipoTramite());
        System.out.println("Información adicional: " + siguiente.getInformacionAdicional());

        return "Cliente llamado correctamente.";
    }

    public String Atender(Queue<ObjVenta> cola) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }
        ObjVenta llamado = null;
        for (ObjVenta o : cola) {
            if (o.getEstado() == 2) {
                llamado = o;
                break;
            }
        }
        if (llamado == null) {
            return "No hay clientes llamados para atender, intente primero llamando al siguiente cliente.";
        }
        llamado.setEstado(3);

        System.out.println("-----------------------------");
        System.out.println("Cliente atendido con éxito:");
        System.out.println("Turno: " + llamado.getNumTurno());
        System.out.println("Cedula: " + llamado.getCedula());
        System.out.println("Nombre: " + llamado.getNombre());
        System.out.println("Edad: " + llamado.getEdad());
        System.out.println("Trámite realizado: " + llamado.getTipoTramite());
        System.out.println("Información adicional: " + llamado.getInformacionAdicional());

        return "Cliente marcado como atendido correctamente.";
    }

    public String CancelarTurno(Queue<ObjVenta> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }
        System.out.println("Ingrese el número de turno que desea cancelar:");
        int turno = v.ValidarEntero(sc);
        ObjVenta encontrado = null;
        for (ObjVenta o : cola) {
            if (o.getNumTurno() == turno) {
                encontrado = o;
                break;
            }
        }
        if (encontrado == null) {
            return "No existe ningún registro con el turno número: " + turno;
        }
        if (encontrado.getEstado() == 3) {
            return "Operación rechazada: El turno " + turno + " ya fue atendido y no se puede cancelar.";
        }
        if (encontrado.getEstado() == 4) {
            return "El turno " + turno + " ya se encuentra cancelado.";
        }
        encontrado.setEstado(4); // 4 = Cancelado

        System.out.println("-----------------------------");
        System.out.println("Turno cancelado:");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Cedula: " + encontrado.getCedula());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Edad: " + encontrado.getEdad());
        System.out.println("Tipo de trámite: " + encontrado.getTipoTramite());
        System.out.println("Información adicional: " + encontrado.getInformacionAdicional());
        System.out.println("Estado: " + ObtenerNombreEstado(encontrado.getEstado()));

        return "El turno se canceló correctamente.";
    }

    public String ActualizarInformacionAdicional(Queue<ObjVenta> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }
        System.out.println("Ingrese el número de turno que desea actualizar:");
        int turno = v.ValidarEntero(sc);
        ObjVenta encontrado = null;
        for (ObjVenta o : cola) {
            if (o.getNumTurno() == turno) {
                encontrado = o;
                break;
            }
        }
        if (encontrado == null) {
            return "No existe ningún registro con el turno número: " + turno;
        }
        if (encontrado.getEstado() == 3) {
            return "Operación rechazada: El turno " + turno + " ya fue atendido y no se puede cancelar.";
        }
        if (encontrado.getEstado() == 4) {
            return "El turno " + turno + " ya se encuentra cancelado.";
        }
        System.out.println("Ingrese la nueva información adicional (tenga presente la anterior se elimina):");
        encontrado.setInformacionAdicional(v.ValidarString(sc));

        System.out.println("-----------------------------");
        System.out.println("Información adicional actualizada:");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Cedula: " + encontrado.getCedula());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Edad: " + encontrado.getEdad());
        System.out.println("Tipo de trámite: " + encontrado.getTipoTramite());
        System.out.println("Información adicional: " + encontrado.getInformacionAdicional());
        System.out.println("Estado: " + ObtenerNombreEstado(encontrado.getEstado()));

        return "La información adicional se actualizó correctamente.";
    }

    public String ActualizarTipoTramite(Queue<ObjVenta> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }
        System.out.println("Ingrese el número de turno que desea actualizar:");
        int turno = v.ValidarEntero(sc);
        ObjVenta encontrado = null;
        for (ObjVenta o : cola) {
            if (o.getNumTurno() == turno) {
                encontrado = o;
                break;
            }
        }
        if (encontrado == null) {
            return "No existe ningún registro con el turno número: " + turno;
        }
        if (encontrado.getEstado() == 3) {
            return "Operación rechazada: El turno " + turno + " ya fue atendido y no se puede cancelar.";
        }
        if (encontrado.getEstado() == 4) {
            return "El turno " + turno + " ya se encuentra cancelado.";
        }
        System.out.println("Ingrese el nuevo tipo de trámite:");
        encontrado.setTipoTramite(TipoTramite(sc, v));

        System.out.println("-----------------------------");
        System.out.println("Tipo de trámite actualizado:");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Cedula: " + encontrado.getCedula());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Edad: " + encontrado.getEdad());
        System.out.println("Tipo de trámite: " + encontrado.getTipoTramite());
        System.out.println("Información adicional: " + encontrado.getInformacionAdicional());
        System.out.println("Estado: " + ObtenerNombreEstado(encontrado.getEstado()));

        return "El tipo de trámite se actualizó correctamente.";
    }

    public String ConsultarPersonasEsperando(Queue<ObjVenta> cola) {
        if (cola.isEmpty()) {
            return "No hay clientes en el sistema.";
        }
        int contador = 0;
        for (ObjVenta o : cola) {
            if (o.getEstado() == 1) {
                contador++;
            }
        }
        System.out.println("-----------------------------");
        System.out.println("Personas en sala de espera: " + contador);

        return "Conteo de clientes en espera realizado correctamente.";
    }
}