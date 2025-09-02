package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.Block;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/block")
public class BlockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer tous les blocs et les ajouter aux attributs de la requête
            List<Block> blocks = Block.getAll();
            request.setAttribute("blocks", blocks);
            
            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("insertionblock.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            double longueur = Double.parseDouble(request.getParameter("longueur"));
            double largeur = Double.parseDouble(request.getParameter("largeur"));
            double hauteur = Double.parseDouble(request.getParameter("hauteur"));
            double prixRevient = Double.parseDouble(request.getParameter("prix_revient"));

            Block.insertNouveauBlock(longueur, largeur, hauteur, prixRevient);
            List<Block> blocks = Block.getAll();
            request.setAttribute("blocks", blocks);
            
            // Rediriger vers la page JSP sans exception
            request.getRequestDispatcher("insertionblock.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();

            // Envoyer une réponse avec un message d'exception sous forme de script JavaScript
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Erreur: " + exception.getMessage() + "');");
            out.println("window.location.href = 'block';");  // Redirection vers la page d'insertion après l'alerte
            out.println("</script>");
        }
    }
}
