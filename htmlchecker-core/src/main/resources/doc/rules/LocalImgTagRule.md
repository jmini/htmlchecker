All local file referenced by the `src` attribute of the `<img>` tag should exist.

With this small site structure:

    site
    |   index.html
    |   page1.html
    |
    +---css
    |       style1.css
    |
    +---img
    |       pict1.png
    |
    \---js
            script1.js

If `index.html` contains this content:

    <body>
        <!-- ... -->
        <p>First image: <img src="img/pict1.png" />.</p>
        <p>Second image: <img src="img/pict2.png" />.</p>
        <!-- ... -->
    </body>

The first `<img>` tag will...