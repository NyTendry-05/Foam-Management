<%@ page import="object.*, java.util.*, java.text.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insertion</title>
    <style>
        /* CSS personnalisé pour imiter un style Bootstrap */
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }

        .container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }

        h1 {
            color: #007bff;
            font-size: 2.2em;
            margin-bottom: 20px;
        }

        h3 {
            color: #333;
            font-size: 1.5em;
            margin-top: 20px;
            text-align: left;
        }

        label {
            font-weight: bold;
            display: block;
            text-align: left;
            font-size: 1.1em;
            margin-top: 10px;
        }

        select, input[type="number"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1em;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>INSERER UNE TRANSFORMATION</h1>

        <form action="transformation" method="post">
            <h3>Selectionner un block</h3>
            <select name="id_block">
                <% 
                    List<Block> blocks = (List<Block>) request.getAttribute("blocks");
                    if (blocks != null) {
                        for (Block block : blocks) {
                %>
                    <option value="<%= block.getIdBlock() %>">
                        <%= block.getIdBlock() %> longueur= <%= block.getLongueur() %> / 
                        largeur= <%= block.getLargeur() %> /
                        hauteur= <%= block.getHauteur() %> 
                    </option>
                <% 
                        }
                    }
                %>
            </select>

            <h3>Formes Usuelles Produites</h3>
            <% 
                List<FormeUsuelle> formes = (List<FormeUsuelle>) request.getAttribute("forme_usuelles");
                if (formes != null) {
                    for (FormeUsuelle forme : formes) {
            %>
                <label for="quantite_<%= forme.getIdFormeUsuelle() %>">Quantite pour forme <%= forme.getNom() %> :</label>
                <input type="number" name="quantite[]" id="quantite_<%= forme.getIdFormeUsuelle() %>" step="1" min="0" value="0">
                <input type="hidden" name="id_forme_usuelle[]" value="<%= forme.getIdFormeUsuelle() %>">
            <% 
                    }
                }
            %>

            <h3>Dimensions bloc restant</h3>
            <label for="longueur">Longueur:</label>
            <input type="number" id="longueur" name="longueur" step="0.001" min="0" value="0">

            <label for="largeur">Largeur:</label>
            <input type="number" id="largeur" name="largeur" step="0.001" min="0" value="0">

            <label for="hauteur">Hauteur:</label>
            <input type="number" id="hauteur" name="hauteur" step="0.001" min="0" value="0">

            <input type="submit" value="Inserer">
        </form>
    </div>

</body>
</html>
