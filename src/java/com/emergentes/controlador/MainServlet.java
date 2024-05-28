package com.emergentes.controlador;

import com.emergentes.modelo.Persona;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        Persona objper = new Persona();
        int id, pos;
        HttpSession ses = request.getSession();
        
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        if (lista == null) {
            lista = new ArrayList<>();
            ses.setAttribute("listaper", lista);
        }

        switch (op) {
            case "nuevo":
                // Enviar un objeto vacío para editar
                request.setAttribute("miobjper", objper);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "modificar":
                // Enviar un objeto a editar pero con contenido
                id = Integer.parseInt(request.getParameter("id"));
                // Averiguar la posición del elemento en la lista
                pos = buscarPorIndice(request, id);
                // Obtener el objeto
                objper = lista.get(pos);
                request.setAttribute("miobjper", objper);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar":
                // Eliminar el registro de la colección según id
                id = Integer.parseInt(request.getParameter("id"));
                // Averiguar la posición del elemento en la lista
                pos = buscarPorIndice(request, id);
                if (pos >= 0) {
                    lista.remove(pos);
                }
                request.setAttribute("listaper", lista);
                response.sendRedirect("index.jsp");
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            
        throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        Persona objper = new Persona();
        objper.setId(id);
        objper.setNombres(request.getParameter("nombres"));
        objper.setApellidos(request.getParameter("apellidos"));
        objper.setEdad(Integer.parseInt(request.getParameter("edad")));

        if (id == 0) {
            // Nuevo registro
            int idNuevo = obtenerId(request);
            objper.setId(idNuevo);
            lista.add(objper);
        } else {
            // Edición de registro
            int pos = buscarPorIndice(request, id);
            lista.set(pos, objper);
        }
        request.setAttribute("listaper", lista);
        response.sendRedirect("index.jsp");
    }

    public int buscarPorIndice(HttpServletRequest request, int id) {
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        int pos = -1;
        if (lista != null) {
            for (Persona ele:lista) {
                ++pos;
                if(ele.getId()==id){
                    break;
                }
                
            }
        }
        return pos;
    }

    public int obtenerId(HttpServletRequest request) {
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        // Buscar el último id
        int idn = 0;
        
        for (Persona ele : lista) {
                idn = ele.getId();
        }
        return idn + 1;
        
        
    }
}
