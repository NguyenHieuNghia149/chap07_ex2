<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
<h1>Downloads</h1>
<h2>Joe Rut Genuine Wood Grained Finish</h2>
<table>
    <tr>
        <th>Song Title</th>
        <th>Audio Format</th>
    </tr>
    <tr>
        <td>You Are a Star</td>
        <td>
            <a href="/musicStore/sound/${productCode}/star.mp3">MP3</a>
        </td>
    </tr>
    <tr>
        <td>Don't Make No Difference</td>
        <td>
            <a href="/musicStore/sound/${productCode}/no_difference.mp3">MP3</a>
        </td>
    </tr>
</table>
<button>
    <a href="download?action=viewAlbums"> View list of Albums</a>
</button>

<button>
    <a href="?action=viewCookies"> View all Cookies</a>
</button>
</body>
</html>
