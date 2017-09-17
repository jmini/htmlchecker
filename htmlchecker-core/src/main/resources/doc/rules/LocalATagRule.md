All local files referenced by the `href` attribute of the `<a>` tag should exist.

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
        <p>Link to a <a href="page1.html">page</a>.</p>
        <p>Link to a <a href="page1.html#abc">page with anchor</a>.</p>
        <p>Link to an <a href="page2.html">other page</a>.</p>
        <p>Link to a <a href="page1.html#xyz">page with wrong anchor</a>.</p>
        <!-- ... -->
    <body>


The first `<a>` tag does not trigger any ERROR, because the file `page1.html` exists.
If `page1.html` contains the anchor `abc` then the link `page1.html#abc` is also valid, and no ERROR is created for the second `<a>` tag. 

The third `<a>` tag triggers an ERROR, because there is no file `page2.html`.
If `page1.html` does not contain the anchor `xyz`, the fourth `<a>` tag triggers an ERROR.

Remote files (for example starting with `https://`) are not checked by this rule.
