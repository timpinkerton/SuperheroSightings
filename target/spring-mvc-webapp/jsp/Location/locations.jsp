<%-- 
    Document   : Locations
    Created on : Oct 21, 2018, 2:28:05 PM
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
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displayHeroesVillains">Heroes/Villains</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="${pageContext.request.contextPath}/displaySuperpowers">Superpowers</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/displayLocations">Locations</a>
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
                        <h2>List of Locations: </h2>
                    </div>

                    <table id="hero-list" class="table table-hover">
                        <tr>
                            <th width="30%">Location</th>
                            <th width="20%"></th>
                            <th width="20%">Actions</th>
                            <th width="20%"></th>
                        </tr>
                        <c:forEach var="currentLocation" items="${locationList}">
                            <tr>
                                <td>
                                    <c:out value="${currentLocation.locationName}"/>
                                </td>

                                <td>
                                    <a href="displayLocationDetails?locationId=${currentLocation.locationId}">
                                        View
                                    </a>
                                </td>
                                <td>
                                    <a href="displayEditLocationForm?locationId=${currentLocation.locationId}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteLocation?locationId=${currentLocation.locationId}">
                                        Delete
                                    </a>
                                </td>

                            </tr>

                        </c:forEach>
                    </table>

                </div>

                <div class="right-content  col-lg-6">
                    <div>
                        <h2>Add a new Location:</h2>
                    </div>
                    <form role="form" method="POST" action="createLocation">
                        
                        <div class="form-group">
                            <label for="locationNameInput">Name</label>
                            <input type="text" class="form-control" name="locationNameInput" 
                                   id="locationNameInput" aria-describedby="locationName" 
                                   placeholder="Enter the name of the location" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="descriptionInput">Description</label>
                            <input type="text" class="form-control" name="descriptionInput" 
                                   id="descriptionInput" placeholder="Describe this location">
                        </div>
                        
                        <div class="form-group">
                            <label for="streetInput">Street</label>
                            <input type="text" class="form-control" name="streetInput" 
                                   id="streetInput" placeholder="Enter a Street Address">
                        </div>
                        
                        <div class="form-group">
                            <label for="cityInput">City</label>
                            <input type="text" class="form-control" name="cityInput" 
                                   id="cityInput" placeholder="Enter a City">
                        </div>
                        
                        <div class="form-group">
                            <label for="stateInput">State</label>
                            <input type="text" class="form-control" name="stateInput" 
                                   id="stateInput" placeholder="Enter a State">
                        </div>
                        
                        <div class="form-group">
                            <label for="zipInput">Zip</label>
                            <input type="text" class="form-control" name="zipInput" 
                                   id="zipInput" placeholder="Enter a Zip Code">
                        </div>
                        
                        <div class="form-group">
                            <label for="countryInput">Country</label>
                            <input type="text" class="form-control" name="countryInput" 
                                   id="countryInput" placeholder="Enter a Country Name">
                        </div>
                        
                        <div class="form-group">
                            <label for="latitudeInput">Latitude</label>
                            <input type="text" class="form-control" name="latitudeInput" 
                                   id="latitudeInput" placeholder="Enter the Latitude">
                        </div>
                        
                        <div class="form-group">
                            <label for="longitudeInput">Longitude</label>
                            <input type="text" class="form-control" name="longitudeInput" 
                                   id="longitudeInput" placeholder="Enter the Longitude">
                        </div>
                        
                        <button type="submit" class="btn btn-primary" value="Create Location">Submit</button>
                    </form>

                </div>

            </div>

            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>
