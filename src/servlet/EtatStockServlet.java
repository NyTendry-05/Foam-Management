package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestion.EtatStockFinancier;


@WebServlet("/etatstock")
public class EtatStockServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EtatStockFinancier etatStockFinancier=new EtatStockFinancier();
            request.setAttribute("etat_stock_financier", etatStockFinancier);


            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("etatstockfinancier.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
