package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestion.Reste;
import object.Block;
import object.FormeUsuelle;

@WebServlet("/transformation")
public class TransformationServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer tous les blocs et les ajouter aux attributs de la requête
            List<Block> blocks = Block.getBlocksNotDecoupe();
            request.setAttribute("blocks", blocks);

            List<FormeUsuelle> formeUsuelles = FormeUsuelle.getAll();
            request.setAttribute("forme_usuelles", formeUsuelles);

            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("transformationblock.jsp").forward(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données du formulaire
            String idBlock = request.getParameter("id_block");
            String[] idFormeUsuelle = request.getParameterValues("id_forme_usuelle[]");
            String[] quantiteStrings = request.getParameterValues("quantite[]");
            double[] quantite = new double[quantiteStrings.length];

            // Convertir les quantités en tableau de doubles
            for (int i = 0; i < quantiteStrings.length; i++) {
                quantite[i] = Double.parseDouble(quantiteStrings[i]);
            }

            // Récupérer les dimensions du bloc restant
            double longueur = Double.parseDouble(request.getParameter("longueur"));
            double largeur = Double.parseDouble(request.getParameter("largeur"));
            double hauteur = Double.parseDouble(request.getParameter("hauteur"));

            // Appeler la méthode transformer de la classe Reste
            Reste.transformer(idBlock, idFormeUsuelle, quantite, longueur, largeur, hauteur);

            // Redirection vers la page de confirmation ou d'affichage du résultat
            List<Block> blocks = Block.getBlocksNotDecoupe();
            request.setAttribute("blocks", blocks);

            List<FormeUsuelle> formeUsuelles = FormeUsuelle.getAll();
            request.setAttribute("forme_usuelles", formeUsuelles);

            // Redirection vers la page JSP pour afficher les blocs
            request.getRequestDispatcher("transformationblock.jsp").forward(request, response);

        } catch (Exception exception) {
            exception.printStackTrace();

            // Envoyer une réponse avec un message d'exception sous forme de script JavaScript
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Erreur: " + exception.getMessage() + "');");
            out.println("window.location.href = 'transformation';");  // Redirection vers la page d'insertion après l'alerte
            out.println("</script>");
        }
    }
}
