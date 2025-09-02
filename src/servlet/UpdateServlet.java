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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer tous les blocs et les ajouter aux attributs de la requête
            List<Block> blocks = Block.getAll();
            request.setAttribute("blocks", blocks);
            
            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("updateblock.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer l'ID du bloc et le nouveau prix de revient à partir de la requête
            String idBlock = request.getParameter("idBlock");
            double prixRevientNew = Double.parseDouble(request.getParameter("prixRevient"));
            
            // Appeler la fonction updatePrixRevientRecursif pour mettre à jour le prix du bloc et de ses blocs fils
            Block.updatePrixRevientRecursif(idBlock, prixRevientNew);
            
            // Rediriger vers une page de succès après la mise à jour
            List<Block> blocks = Block.getAll();
            request.setAttribute("blocks", blocks);
            
            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("updateblock.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();

            // Envoyer une réponse avec un message d'exception sous forme de script JavaScript
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Erreur: " + exception.getMessage() + "');");
            out.println("window.location.href = 'update';");  // Rediriger vers la page de mise à jour après l'alerte
            out.println("</script>");
        }
    }
}
