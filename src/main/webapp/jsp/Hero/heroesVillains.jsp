<%-- 
    Document   : heroesVillains
    Created on : Oct 19, 2018, 12:32:30 PM
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
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displayHeroesVillains">Heroes/Villains</a>
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

            <div class="main-content row">

                <div class="left-content col-lg-6">
                    <div>
                        <h2>List of Heroes/Villians: </h2>
                    </div>

                    <table id="hero-list" class="table table-hover">
                        <tr>
                            <th width="70%">Hero Name</th>
                            
                            <!--Removing description from the view-->
                            <!--<th width="30%">Description</th>-->
                            <th width="10%"></th>
                            <th width="10%">Actions</th>
                            <th width="10%"></th>
                        </tr>
                        <c:forEach var="currentHero" items="${heroList}">
                            <tr>
                                <td>
                                    <c:out value="${currentHero.heroName}"/>
                                </td>
                                
                                <!--Removing description from the view-->
                                
<!--                                <td>
                                    <c:out value="${currentHero.description}"/>
                                </td>-->
                                    
                                <td>
                                    <a href="displayHeroDetails?heroId=${currentHero.heroId}">
                                        View
                                    </a>
                                </td>
                                <td>
                                    <a href="displayEditHeroForm?heroId=${currentHero.heroId}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteHero?heroId=${currentHero.heroId}">
                                        Delete
                                    </a>
                                </td>

                            </tr>

                        </c:forEach>
                    </table>

                </div>

                <div class="right-content  col-lg-6">
                    <div>
                        <h2>Add a new Hero/Villian:</h2>
                    </div>
                    <form role="form" method="POST" action="createHero">
                        <div class="form-group">
                            <label for="heroNameInput">Name</label>
                            <input type="text" class="form-control" name="heroNameInput" id="heroNameInput" aria-describedby="heroName" placeholder="Enter a name" required>
                        </div>
                        <div class="form-group">
                            <label for="descriptionInput">Description</label>
                            <input type="text" class="form-control" name="descriptionInput" id="descriptionInput" placeholder="Describe this hero/villain">
                        </div>
                        <button type="submit" class="btn btn-primary" value="Create Hero">Submit</button>
                    </form>

                </div>

            </div>

            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>