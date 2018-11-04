<%-- 
    Document   : sightings
    Created on : Oct 21, 2018, 8:51:40 PM
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
        <!-- Bootstrap core CSS -->
        <!--<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">-->     

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
                        <li class="nav-item">
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
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/displaySightings">Sightings</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="main-content row">

                 <div class="left-content col-lg-6">
                    <div>
                        <h2>List of Sightings: </h2>
                    </div>

                    <table id="hero-list" class="table table-hover">
                        <tr>
                            <th width="40%">Sighting</th>
                            <th width="20%"></th>
                            <th width="20%">Actions</th>
                            <th width="20%"></th>
                        </tr>
                        <c:forEach var="currentSighting" items="${sightingList}">
                            <tr>
                                <td>
                                    <c:out value="${currentSighting.sightingDate}"/>
                                </td>

                                <td>
                                    <a href="displaySightingDetails?sightingId=${currentSighting.sightingId}">
                                        View
                                    </a>
                                </td>
                                <td>
                                    <a href="displayEditSightingForm?sightingId=${currentSighting.sightingId}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteSighting?sightingId=${currentSighting.sightingId}">
                                        Delete
                                    </a>
                                </td>

                            </tr>

                        </c:forEach>
                    </table>

                </div>

                <div class="right-content  col-lg-6">
                    <div>
                        <h2>Add a new Sighting:</h2>
                    </div>
                    <form role="form" method="POST" action="createSighting">
                        <div class="form-group">
                            <label for="sightingDateInput">Date:</label>
                            <input type="datetime-local" class="form-control" name="sightingDateInput" id="sightingDateInput" aria-describedby="sightingDateInput" placeholder="Enter a date" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="locationInput">Select the location for this sighting:</label>
                            <select class="form-control" name="selectedLocation" id="locationList" id="locationInput" required>
                                <c:forEach var="currentLocation" items="${locationList}">
                                    <option value="${currentLocation.locationId}"><c:out value="${currentLocation.locationName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="heroesInOrganization">Select Heroes at this Sighting:</label>
                            <select multiple class="form-control" name="selectedHeroes" id="heroesInOrganization" required>
                                <c:forEach var="currentHero" items="${heroList}">
                                <option value="${currentHero.heroId}"><c:out value="${currentHero.heroName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary" value="Create Sighting">Submit</button>
                    </form>

                </div>

            </div>

            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>