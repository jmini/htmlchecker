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

The first `<script>` tag will...