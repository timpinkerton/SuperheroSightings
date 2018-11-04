<%-- 
    Document   : editLocationForm
    Created on : Oct 21, 2018, 4:16:29 PM
    Author     : timpinkerton
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

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
                    <p>Current Location information:</p>

                    <p>Location Name: <c:out value="${location.locationName}"/></p>
                    <p>Location Description: <c:out value="${location.description}"/></p>
                    <p>Street: <c:out value="${location.street}"/></p>
                    <p>City: <c:out value="${location.city}"/></p>
                    <p>State: <c:out value="${location.state}"/></p>
                    <p>Zip: <c:out value="${location.zip}"/></p>
                    <p>Country: <c:out value="${location.country}"/></p>
                    <p>Latitude: <c:out value="${location.latitude}"/></p>
                    <p>Longitude <c:out value="${location.longitude}"/></p>

                </div>

                <div class="right-content col-lg-6">
                    <h2>Edit Location</h2>

                    <sf:form class="form-horizontal" role="form" modelAttribute="location"
                             action="editLocation" method="POST">

                        <div class="form-group">
                            <label for="add-location-name" class="col-md-8 control-label">Location Name:</label>
                            <sf:input type="text" class="form-control" id="add-location"
                                      path="locationName" placeholder="locationName"/>
                            <sf:errors path="locationName" cssclass="error"></sf:errors>
                            </div>


                            <div class="form-group">
                                <label for="add-description" class="col-md-8 control-label">Location Description:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-description"
                                          path="description" placeholder="Location Description"/>
                                <sf:errors path="description" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-steet" class="col-md-8 control-label">Street:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-street"
                                          path="street" placeholder="Street"/>
                                <sf:errors path="street" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-city" class="col-md-8 control-label">City:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-city"
                                          path="city" placeholder="City"/>
                                <sf:errors path="city" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-state" class="col-md-8 control-label">State:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-state"
                                          path="state" placeholder="State"/>
                                <sf:errors path="state" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-zip" class="col-md-8 control-label">Zip:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-zip"
                                          path="zip" placeholder="Zip"/>
                                <sf:errors path="zip" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-country" class="col-md-8 control-label">County:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-country"
                                          path="country" placeholder="Country"/>
                                <sf:errors path="country" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-latitude" class="col-md-8 control-label">Latitude:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-latitude"
                                          path="latitude" placeholder="Latitude"/>
                                <sf:errors path="latitude" cssclass="error"></sf:errors>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="add-longitude" class="col-md-8 control-label">Longitude:</label>
                                <div class="col-md-12">
                                <sf:input type="text" class="form-control" id="add-description"
                                          path="longitude" placeholder="longitude"/>
                                <sf:errors path="longitude" cssclass="error"></sf:errors>
                                <sf:hidden path="locationId"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button type="submit" class="btn btn-primary" value="Update Location">Update</button>
                            </div>
                        </div>
                    </sf:form>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>