<%@ page import="object.*, java.util.*, java.text.*, gestion.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Etat Stock Financier</title>
    <style>
        /* CSS personnalisé imitant Bootstrap */
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: auto;
            padding: 20px;
        }

        h1, h2, h3 {
            color: #333;
            margin-bottom: 20px;
        }

        h1 {
            font-size: 2.5em;
            border-bottom: 2px solid #ccc;
            padding-bottom: 10px;
        }

        h2 {
            font-size: 2em;
            margin-top: 20px;
        }

        h3 {
            font-size: 1.5em;
            margin-top: 15px;
        }

        table {
            width: 100%;
            margin-bottom: 20px;
            border-collapse: collapse;
            background-color: #fff;
        }

        table th, table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }

        table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }

        .total {
            font-weight: bold;
            background-color: #f2f2f2;
        }

        .btn {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .text-right {
            text-align: right;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Etat des Stocks Financiers</h1>

        <h2>1. Stock Reel</h2>
        <table>
            <thead>
                <tr>
                    <th>ID Stock</th>
                    <th>ID Origine</th>
                    <th>ID Forme Usuelle</th>
                    <th>ID Block</th>
                    <th>Prix Vente</th>
                    <th>Prix Revient</th>
                    <th>Prix RevientTotal</th>
                    <th>Quantite</th>
                    <th>Prix Total</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Récupérer l'objet EtatStockFinancier depuis la requête
                    EtatStockFinancier etatStockFinancier = (EtatStockFinancier) request.getAttribute("etat_stock_financier");
                    
                    // Récupérer la liste des stocks réels
                    List<Stock> stockReel = etatStockFinancier.getStockReel();
                    if (stockReel != null) {
                        for (Stock stock : stockReel) {
                %>
                <tr>
                    <td><%= stock.getIdStock() %></td>
                    <td><%= stock.getIdOrigineBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getIdFormeUsuelle() %></td>
                    <td><%= stock.getBlock().getIdBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getPrixVente() %></td>
                    <td><%= stock.getPrixRevientStock() %></td>
                    <td><%= stock.getPrixRevientStockTotal() %></td>
                    <td><%= stock.getQuantite() %></td>
                    <td><%= stock.getPrixTotal() %></td>
                </tr>
                <% 
                        }
                    }
                %>
                <tr class="total">
                    <td colspan="4">Total Stock Reel</td>
                    <td><%= etatStockFinancier.getPrixTotalStockReel() %></td>
                </tr>
            </tbody>
        </table>

        <h3>Block Non Decoupes</h3>
        <table>
            <thead>
                <tr>
                    <th>ID Block</th>
                    <th>Longueur</th>
                    <th>Largeur</th>
                    <th>Hauteur</th>
                    <th>Prix de revient</th>
                    <th>Date d'insertion</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Récupérer la liste des blocs non découpés
                    List<Block> blocksNonDecoupes = etatStockFinancier.getBlockNonDecoupe();
                    if (blocksNonDecoupes != null) {
                        for (Block block : blocksNonDecoupes) {
                %>
                <tr>
                    <td><%= block.getIdBlock() %></td>
                    <td><%= block.getLongueur() %></td>
                    <td><%= block.getLargeur() %></td>
                    <td><%= block.getHauteur() %></td>
                    <td><%= block.getPrixRevient() %></td>
                    <td><%= block.getDateInsertion() %></td>
                </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>

        <h2>2. Stock Theorique Max Prix de Revient</h2>
        <table>
            <thead>
                <tr>
                    <th>ID Stock</th>
                    <th>ID Origine</th>
                    <th>ID Forme Usuelle</th>
                    <th>ID Block</th>
                    <th>Prix Vente</th>
                    <th>Prix Revient</th>
                    <th>Prix Revient Total</th>
                    <th>Quantite</th>
                    <th>Prix Total</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Récupérer la liste des stocks théoriques max prix de revient
                    List<Stock> stockMaxPrixRevient = etatStockFinancier.getStockTheoriqueMaxPrixRevient();
                    if (stockMaxPrixRevient != null) {
                        for (Stock stock : stockMaxPrixRevient) {
                %>
                <tr>
                    <td><%= stock.getIdStock() %></td>
                    <td><%= stock.getIdOrigineBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getIdFormeUsuelle() %></td>
                    <td><%= stock.getBlock().getIdBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getPrixVente() %></td>
                    <td><%= stock.getPrixRevientStock() %></td>
                    <td><%= stock.getPrixRevientStockTotal() %></td>
                    <td><%= stock.getQuantite() %></td>
                    <td><%= stock.getPrixTotal() %></td>
                </tr>
                <% 
                        }
                    }
                %>
                <tr class="total">
                    <td colspan="4">Total Stock Theorique Max Prix Revient</td>
                    <td><%= etatStockFinancier.getPrixTotalStockMaxPrixRevient() %></td>
                </tr>
            </tbody>
        </table>
        <h2>Total Montant Theorique Max Prix Revient + Stock Reel</h2>
        <h3><%= etatStockFinancier.getMontantTheoriqueMaxprix() %></h3>

        <h2>3. Stock Theorique (Eponge)</h2>
        <table>
            <thead>
                <tr>
                    <th>ID Stock</th>
                    <th>ID Origine</th>
                    <th>ID Forme Usuelle</th>
                    <th>ID Block</th>
                    <th>Prix Vente</th>
                    <th>Prix Revient</th>
                    <th>Prix Revient Total</th>
                    <th>Quantite</th>
                    <th>Prix Total</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Récupérer la liste des stocks théoriques éponge
                    List<Stock> stockEponge = etatStockFinancier.getStockTheoriqueEponge();
                    if (stockEponge != null) {
                        for (Stock stock : stockEponge) {
                %>
                <tr>
                    <td><%= stock.getIdStock() %></td>
                    <td><%= stock.getIdOrigineBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getIdFormeUsuelle() %></td>
                    <td><%= stock.getBlock().getIdBlock() %></td>
                    <td><%= stock.getFormeUsuelle().getPrixVente() %></td>
                    <td><%= stock.getPrixRevientStock() %></td>
                    <td><%= stock.getPrixRevientStockTotal() %></td>
                    <td><%= stock.getQuantite() %></td>
                    <td><%= stock.getPrixTotal() %></td>
                </tr>
                <% 
                        }
                    }
                %>
                <tr class="total">
                    <td colspan="4">Total Stock Theorique Eponge</td>
                    <td><%= etatStockFinancier.getPrixTotalStockTheoriqueEponge() %></td>
                </tr>
            </tbody>
        </table>

        <h2>Total Montant Theorique Eponge + Stock Reel</h2>
        <h3><%= etatStockFinancier.getMontantTheoriqueEponge() %></h3>
    </div>
    </div>

</body>
</html>
