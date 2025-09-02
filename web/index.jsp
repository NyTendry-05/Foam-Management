<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
    <style>
        /* CSS personnalisé imitant Bootstrap */
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            text-align: center;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #007bff;
            font-size: 2.5em;
            margin-bottom: 30px;
        }

        a {
            display: block;
            text-decoration: none;
            color: white;
            background-color: gray;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
            font-size: 1.2em;
            width: 250px;
            margin-left: auto;
            margin-right: auto;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #0056b3;
        }

        /* Responsivité pour les petits écrans */
        @media (max-width: 600px) {
            a {
                width: 200px;
                font-size: 1.1em;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>WELCOME TO FOAM & CO.</h1>
        <a href="block">INSERER UN BLOCK</a>
        <a href="transformation">TRANSFORMER UN BLOCK</a>
        <a href="etatstock">ETAT STOCK FINANCIER</a>
        <a href="update">update block</a>
    </div>

</body>
</html>
