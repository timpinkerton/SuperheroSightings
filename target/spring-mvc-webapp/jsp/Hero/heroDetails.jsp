<%-- 
    Document   : heroDetails
    Created on : Oct 20, 2018, 2:58:11 PM
    Author     : timpinkerton
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Superhero Sightings</title>

        <!--CDN for Bootstrap 4-->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Superhero Sightings</h1>
            <hr/>

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/displayHeroesVillains">Heroes/Villains</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displaySuperpowers">Superpowers</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displayLocations">Locations</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displayOrganizations">Organizations</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displaySightings">Sightings</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="main-content">

                <h2>Hero Details: </h2>

                <div>

                    <p>Name: <c:out value="${hero.heroName}"/></p>
                    <p>Description: <c:out value="${hero.description}"/></p>

                    <p><c:out value="${hero.heroName}"/> has these superpowers:</p>

                    <!--Display for each superpower in superpowerList-->
                    <ul>
                        <c:forEach var="currentSuperpower" items="${superpowerList}">
                            <a href="displaySuperpowerDetails?superpowerId=${currentSuperpower.superpowerId}">
                                <li><c:out value="${currentSuperpower.description}"/>
                            </a>
                            </li>
                        </c:forEach>
                    </ul>

                    <p><c:out value="${hero.heroName}"/> belongs to these organizations:</p>

                    <ul>
                        <c:forEach var="currentOrganization" items="${organizationList}">
                            <li><c:out value="${currentOrganization.organizationName}"/></li>
                            </c:forEach>
                    </ul>

                    <p> 
                        <a href="displayEditHeroForm?heroId=${hero.heroId}">
                            Edit
                        </a>
                        details for this Hero.
                    </p>
                </div>

            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>
