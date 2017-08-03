All local file referenced by the `href` attribute of the `<a>` tag should exist.

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
        <p>Link to a <a href="page1.html#abc">page with anchor</a>.</p>
        <p>Link to a <a href="page1.html#xyz">page with wrong anchor</a>.</p>
        <p>Link to an <a href="page2.html">other page</a>.</p>
        <!-- ... -->
    <body>

The first `<a>` tag will...