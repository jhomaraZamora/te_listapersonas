<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Persona"%>

<%-- 
    Document   : index
    Created on : 28 may. de 2024, 09:38:06
    Author     : JHOMARA
--%>

<%
    if (session.getAttribute("listaper") == null) {
        ArrayList<Persona> lisaux = new ArrayList<Persona>();
        session.setAttribute("listaper", lisaux);
    }
    ArrayList<Persona> lista = (ArrayList<Persona>) session.getAttribute("listaper");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de registros</title>
    </head>
    <body>
        <h1>Listado de registros</h1>
        <a href="MainServlet?op=nuevo">Nuevo registro</a>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Edad</th>
                <th></th>
                <th></th>
            </tr>
            <%
                if (lista != null) {
                    for (Persona item : lista) {
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getNombres() %></td>
                <td><%= item.getApellidos() %></td>
                <td><%= item.getEdad() %></td>
                <td>
                    <a href="MainServlet?op=modificar&id=<%= item.getId() %>">Editar</a>
                </td>
                <td>
                    <a href="MainServlet?op=eliminar&id=<%= item.getId() %>"
                       onclick="return confirm('¿Está seguro de eliminar?');">Eliminar</a>
                </td> 
            </tr>
            <%
                    }
                }
            %>
        </table>
    </body>
</html>
