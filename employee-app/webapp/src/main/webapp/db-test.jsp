<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%
    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/EmployeeDS");

    Connection conn = null;
    try {
        conn = ds.getConnection();
        System.out.println("++++ DB connect successfully+++++++");

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EmployeeDS");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.flush();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    finally {
        if (conn != null) {
            conn.close();
        }
    }
%>

<h1>Hallo!</h1>