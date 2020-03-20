package hibernate.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import hibernate.test.dao.PersonaDao;
import hibernate.test.dao.VentasDao;
import hibernate.test.dto.PersonaEntity;

public class TestHibernate {

	public static void main(String[] args) throws SQLException, ParseException {

		// MENU DEL ABM

		System.out.println("APP PERSONAS Y VENTAS ABM)");
		System.out.println("=========================");

		PersonaDao perDao = new PersonaDao();
		VentasDao venDao = new VentasDao();

		Scanner scan = new Scanner(System.in);

		int opcion = 0;

		opcion = mostrarMenu(scan);

		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta(perDao, scan);
				break;
			case 2:
				modificacion(perDao, scan);
				break;
			case 3:
				eliminar(perDao, scan);
				break;
			case 4:
				listar(perDao, scan);
				break;
			case 5:
				busqueda(perDao, scan);
				break;
			case 6:
				ventas(venDao, perDao, scan);
				break;

			default:
				break;
			}
		}

		opcion = mostrarMenu(scan);

	}

	private static int mostrarMenu(Scanner scan) {

		System.out.println(
				"Menu opciones: 1- Alta |2- Modificacion |3- Eliminar |4- Lista de Usuarios |5- Busqueda |6- Venta |0- Salir");

		int opcion = scan.nextInt();
		return opcion;
	}

	// CREACION DE METODOS ABM

	private static void alta(PersonaDao perDao, Scanner sc) {

		PersonaEntity per = new PersonaEntity();
		Scanner scan = new Scanner(System.in);

		System.out.println("INGRESE NOMBRE DE USUARIO");
		String nombre = scan.next();
		per.setNombre(nombre);

		System.out.println("INGRESE FECHA DE NACIMIENTO DEL USUARIO YYYY-MM-DD");
		String fechadenacimiento = scan.next();
		per.setFechaDeNacimiento(fechadenacimiento);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

		int edad = 0;

		try {
			Date birthdateparsed = sdf.parse(fechadenacimiento);
			edad = calcularEdad(birthdateparsed);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		per.setEdad(edad);
		System.out.println("Edad:" + edad);

		PersonaDao per1 = new PersonaDao();
		per1.insertPersona(per);

		System.out.println("EL USUARIO " + nombre + " FUE AGREDADO CON EXITO");

		mostrarMenu(scan);

	}

	private static int calcularEdad(Date birthdateparsed) {

		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(birthdateparsed);

		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);

		int dif = anioActual - anioNacim;

		if (mesActual < mesNacim) {
			dif = dif - 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif = dif - 1;
			}
		}

		return dif;
	}

	private static void modificacion(PersonaDao perDao, Scanner scan) {

		PersonaDao permodif = new PersonaDao();

		System.out.println("INGRESE ID DEL USUARIO A MODIFICAR");
		int personaId = scan.nextInt();

		PersonaEntity per = permodif.getPersona(personaId);

		if (per == null) {
			System.out.println("EL DNI INGRESADO NO CORRESPONDE A UN USUARIO RESGISTRDO");
		} else {

			System.out.println("INGRESE EL NUMERO DE COLUMNA DEL DATO DEL USUARIO A MODIFICAR");
			System.out.println("1. NOMBRE");
			System.out.println("2. FECHA DE NACIMIENTO");
			System.out.println("3. VOLVER AL MENU PRINCIPAL");

			int opcion = 0;
			opcion = scan.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("INGRESE EL NUEVO NOMBRE DEL USUARIO");
				String newName = scan.next();
				per.setNombre(newName);
				permodif.updatePersona(per);
				System.out.println("EL NOMBRE DEL USUARIO HA SIDO MODIFICADO A: " + newName);
				opcion = mostrarMenu(scan);
				break;
			case 2:
				System.out.println("INGRESE LA NUEVA FECHA DE NACIMIENTO DEL USUARIO");
				String newBirthdate = scan.next();
				per.setFechaDeNacimiento(newBirthdate);
				SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
				int newage = 0;
				try {
					Date newBirthdateparsed = sfd.parse(newBirthdate);
					newage = calcularEdad(newBirthdateparsed);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				per.setEdad(newage);

				PersonaDao permodiffinal = new PersonaDao();
				permodiffinal.updatePersona(per);
				System.out.println("LA FECHA DE NACIMINETO HA SIDO MODIFICADA");
				mostrarMenu(scan);
				break;
			default:
				break;

			}
		}
	}

	private static void eliminar(PersonaDao perDao, Scanner scan) throws SQLException {

		PersonaDao pereliminada = new PersonaDao();

		System.out.println("INGRESE DNI DEL USUARIO A ELIMINAR");
		int idToDelete = scan.nextInt();
		PersonaEntity per = pereliminada.getPersona(idToDelete);

		if (per != null) {
			pereliminada.deletePersona(per);
			System.out.println("EL USUARIO HA SIDO ELIMINADO EXITOSAMENTE");
		} else {
			System.out.println("EL DNI INGRESADO NO ESTA EN EL REGISTRO");
			System.out.println(" ");
			System.out.println("INGRESE 1. PARA INGRESAR NUEVAMENTE EL DNI A ELIMINAR");
			System.out.println("        2. VOLVER AL MENU PRINCIPAL");
			int opcion = 0;

			opcion = scan.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("INGRESE DNI DEL USUARIO A ELIMINAR");
				int idToDelete2 = scan.nextInt();
				PersonaEntity per2 = pereliminada.getPersona(idToDelete2);

				if (per2 != null) {

					pereliminada.deletePersona(per2);
					System.out.println("EL USUARIO HA SIDO ELIMINADO EXITOSAMENTE");

				} else {
					System.out.println("EL DNI INGRESADO NO ESTA EN EL REGISTRO");

					mostrarMenu(scan);
				}
				break;
			case 2:
				mostrarMenu(scan);
				break;
			default:
				break;
			}
		}
	}

	private static void listar(PersonaDao perDao, Scanner scan) {

		System.out.println("LISTA DE USUARIOS INGRESADOS EN SISTEMA");
		System.out.println(" NOMBRE | FECHA DE NACIMINETO | EDAD ");
		PersonaDao per1 = new PersonaDao();
		per1.getAll();

		mostrarMenu(scan);

	}

	private static void busqueda(PersonaDao perDao, Scanner scan) {

		PersonaDao perToSearch = new PersonaDao();
		System.out.println("INGRESE EL DNI DEL USUARIO A BUSCAR");
		int personaId = scan.nextInt();
		PersonaEntity per = perToSearch.getPersona(personaId);

		if (per != null) {
			System.out.println(" NOMBRE | FECHA DE NACIMINETO | EDAD ");
			System.out.println(per.getNombre() + "   " + per.getFechadenacimiento() + "   " + per.getEdad());
		} else {
			System.out.println("EL DNI INGRESADO NO ESTA EN EL REGISTRO");
		}

		mostrarMenu(scan);
	}

	private static void ventas(VentasDao venDao, PersonaDao perDao, Scanner scan) {

		System.out.println("REGISTRO DE VENTA REALIZADA");
		System.out.println("INGRESE EL ID DEL CLIENTE");

		PersonaDao perToSearch = new PersonaDao();
		int personaId = scan.nextInt();
		PersonaEntity per = perToSearch.getPersona(personaId);

		if (per != null) {

			VentasDao registroDeVentas = new VentasDao();

			System.out.println(" NOMBRE | FECHA DE NACIMINETO | EDAD ");
			System.out.println(per.getNombre() + "   " + per.getFechadenacimiento() + "   " + per.getEdad());
			System.out.println("INGRESE EL MONTO DE LA VENTA");
			float importe = scan.nextFloat();
			Date fechaDeVenta = new Date();
			String fechaString = DateUtil.pasarDateAString(DateUtil.PATTERN_D2_M2_Y4, fechaDeVenta);
			registroDeVentas.insertVenta(importe);
			System.out.println(
					per.getNombre() + " ha realizado una compra por:" + registroDeVentas.setImporte(importe) + " ARS");

		} else {
			System.out.println("EL DNI INGRESADO NO ESTA EN EL REGISTRO");
		}

		mostrarMenu(scan);
	}

}
