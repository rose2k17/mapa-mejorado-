package com.envios.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envios.dao.ArticuloDAO;
import com.envios.model.Articulo;

/**
 * Servlet implementation class AdminArticulo
 */
@WebServlet("/adminArticulo")
public class AdminArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArticuloDAO articuloDAO;
	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		try {

			articuloDAO = new ArticuloDAO(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminArticulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola Servlet..");
		String action = request.getParameter("action");
		System.out.println(action);
		try {
			switch (action) {
			case "index":
				index(request, response);
				break;
			case "nuevo":
				System.out.println("entro en nuevo");
				nuevo(request, response);
				break;
			case "register":
				System.out.println("entro en register");
				registrar(request, response);
				break;
			case "mostrar":
				mostrar(request, response);
				break;
			case "buscar":
				buscar(request, response);
				break;
			case "showedit":
				showEditar(request, response);
				break;
			case "editar":
				editar(request, response);
				break;
			case "eliminar":
				eliminar(request, response);
				break;
			case "map":
				map(request, response);
				break;
			case "tabla":
				mostrarTabla(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException | ParseException e) {
			e.getStackTrace();
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/buscar.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola Servlet..");
		doGet(request, response);
	}

	private void mostrarTabla(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// Var: origen y destino
		String origen = request.getParameter("origen");
		String destino = request.getParameter("destino");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/mostrar.jsp");
		try {
			List<Articulo> listaArticulos = articuloDAO.listarArticulosOD(origen, destino);
			request.setAttribute("lista", listaArticulos);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}

	private void index(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// mostrar(request, response);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	private void map(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// mostrar(request, response);
		RequestDispatcher dispatcher = request.getRequestDispatcher("maps.jsp");
		dispatcher.forward(request, response);
	}

	private void registrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ParseException {
		/**
		 * @params origen y destino convertido en utf-8
		 * fecha: cambiando formato de string a fecha
		 */
		String origen = new String(request.getParameter("origen").getBytes("ISO-8859-1"),"UTF-8"); 
		String destino = new String(request.getParameter("destino").getBytes("ISO-8859-1"),"UTF-8"); 
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date fecha = new java.util.Date();
		fecha=formatter.parse(request.getParameter("fecha"));
		
		//Metiendo los parametros a articulo
		Articulo articulo = new Articulo(1, origen,
				destino, request.getParameter("paquete"), fecha,
				request.getParameter("remitente"), request.getParameter("transportista"),
				Double.parseDouble(request.getParameter("precio")));
		System.out.println("" + origen + " " +
				destino + " " +request.getParameter("paquete") + " " + fecha + " " +
				request.getParameter("remitente") + " " + request.getParameter("transportista") + " " +
				Double.parseDouble(request.getParameter("precio")));
		articuloDAO.insertar(articulo);
		System.out.println("prueba2");
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	private Date parse(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista/register.jsp");
		dispatcher.forward(request, response);
	}

	private void mostrar(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista/mostrar.jsp");
		List<Articulo> listaArticulos = articuloDAO.listarArticulos();
		request.setAttribute("lista", listaArticulos);
		dispatcher.forward(request, response);
	}

	private void showEditar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		Articulo articulo = articuloDAO.obtenerPorId(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("articulo", articulo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/editar.jsp");
		dispatcher.forward(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Articulo articulo = new Articulo(Integer.parseInt(request.getParameter("id")), request.getParameter("origen"),
				request.getParameter("destino"), request.getParameter("paquete"), parse(request.getParameter("fecha")),
				request.getParameter("remitente"), request.getParameter("transportista"),
				Double.parseDouble(request.getParameter("precio")));
		articuloDAO.actualizar(articulo);
		index(request, response);
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Articulo articulo = articuloDAO.obtenerPorId(Integer.parseInt(request.getParameter("id")));
		articuloDAO.eliminar(articulo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}
}