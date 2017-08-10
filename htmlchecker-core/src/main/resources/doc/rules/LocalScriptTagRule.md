All local file referenced by the `src` attribute of the `<script>` tag should exist.

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
        <!-- ... -->
        <script type="text/javascript" src="js/script1.js"></script>
        <script src="js/script2.js"></script>
        <!-- ... -->
    </head>

The first `<script>` tag does not trigger any ERROR, because the file `js/script1.js` exists.

The second `<script>` tag triggers an ERROR, because there is no file `js/script2.js`.

Remote files (for example starting with `https://`) are not checked by this rule.
