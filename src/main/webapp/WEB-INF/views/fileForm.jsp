<%--
  Created by IntelliJ IDEA.
  User: Babor
  Date: 5/29/2020
  Time: 5:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>File - Upload</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <form id="fileForm" action="<c:url value='/file'/>" method="post" enctype="multipart/form-data" class="form-horizontal">
    <fieldset>
        <!-- Form Name -->
        <legend>Form Name</legend>

        <!-- File Button -->
        <div class="form-group">
            <label class=" control-label" for="mypic">File</label>
            <div class="">
                <input id="mypic" name="mypic" class="input-file" type="file" multiple="multiple">
            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <div class="">
                <button class="btn btn-primary" type="submit">Submit</button>
            </div>
        </div>

    </fieldset>
    </form>
    <img src="<c:url value='/'/>static/img/Screenshot_2.png" />
</div>


<script src="https://code.jquery.com/jquery-3.3.0.min.js"></script>
</body>
</html>
