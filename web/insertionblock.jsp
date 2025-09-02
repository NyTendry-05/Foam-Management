<%@ page import="object.*, java.util.*, java.text.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insertion</title>
    <style>
        /* CSS personnalise pour un style epure et bien aligne en haut */
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
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }

        h1 {
            color: #007bff;
            font-size: 2.2em;
            margin-bottom: 20px;
        }

        h2 {
            color: #333;
            font-size: 1.5em;
            margin-top: 30px;
        }

        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
            text-align: left;
            font-size: 1.1em;
        }

        input[type="number"], input[type="submit"] {
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
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>INSERER UN BLOCK</h1>
        <form action="block" method="post">
            <label for="longueur">Longueur:</label>
            <input type="number" id="longueur" name="longueur" step="0.001" min="0" value="0">

            <label for="largeur">Largeur:</label>
            <input type="number" id="largeur" name="largeur" step="0.001" min="0" value="0">

            <label for="hauteur">Hauteur:</label>
            <input type="number" id="hauteur" name="hauteur" step="0.001" min="0" value="0">

            <label for="prix_revient">Prix de revient:</label>
            <input type="number" id="prix_revient" name="prix_revient" step="0.001" min="0" value="0">

            <input type="submit" value="Inserer">
        </form>

        <h2>Liste des Blocks</h2>
        <table>
            <thead>
                <tr>
                    <th>ID Block</th>
                    <th>ID Mere</th>
                    <th>ID Origine</th>
                    <th>Longueur</th>
                    <th>Largeur</th>
                    <th>Hauteur</th>
                    <th>Prix de revient</th>
                    <th>Date d'insertion</th>
                    <th>Date decoupe</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Block> blocks = (List<Block>) request.getAttribute("blocks");
                    if (blocks != null) {
                        for (Block block : blocks) {
                %>
                    <tr>
                        <td><%= block.getIdBlock() %></td>
                        <td><%= block.getIdMere() %></td>
                        <td><%= block.getIdOrigine() %></td>
                        <td><%= block.getLongueur() %></td>
                        <td><%= block.getLargeur() %></td>
                        <td><%= block.getHauteur() %></td>
                        <td><%= block.getPrixRevient() %></td>
                        <td><%= block.getDateInsertion() %></td>
                        <td><%= block.getDateDecoupe() %></td>
                    </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
    </div>

</body>
</html>
