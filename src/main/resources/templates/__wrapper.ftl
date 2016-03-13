<#macro main title="main">
    <#setting number_format="0.#####">
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"My blog"}</title>
</head>
<body>
    <#nested/>
</div>
</body>
</html>
</#macro>

<#macro admin title="Blog admin site">
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="/webjars/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar {
            position: fixed;
            top: 51px;
            bottom: 0;
            left: 0;
            z-index: 1000;
            display: block;
            padding: 20px;
            overflow-x: hidden;
            overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
            background-color: #f5f5f5;
            border-right: 1px solid #eee;
        }

        .nav-sidebar {
            margin-right: -21px; /* 20px padding + 1px border */
            margin-bottom: 20px;
            margin-left: -20px;
        }

        .nav-sidebar > li > a {
            padding-right: 20px;
            padding-left: 20px;
        }

        .nav-sidebar > .active > a,
        .nav-sidebar > .active > a:hover,
        .nav-sidebar > .active > a:focus {
            color: #fff;
            background-color: #428bca;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-dark bg-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Project name</a>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="/admin/entry/">Entries</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <#nested/>
        </div>
    </div>
</div>
</div>
</body>
</html>
</#macro>