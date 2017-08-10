All local file referenced by the `href` attribute of the `<link>` tag should exist.

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

    <head>
        <link rel="stylesheet" href="css/style1.css">
        <link rel="stylesheet" href="css/style2.css">
        <!-- ... -->
    </head>

The first `<link>` tag does not trigger any ERROR, because the file `css/style1.css` exists.

The second `<link>` tag triggers an ERROR, because there is no file `css/style2.css`.

Remote files (for example starting with `https://`) are not checked by this rule.
