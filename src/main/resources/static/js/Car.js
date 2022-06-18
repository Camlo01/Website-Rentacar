// import { addInSelect } from "./utils";

function carData() {
  fetch("http://localhost:8080/api/Car/all")
    .then((response) => response.json())
    .then(function (data) {
      pintarRespuestaCar(data);
      addInSelect(data, "Select-Car");
    })
    .catch((error) => console.log("Problema al traer dator Car: " + error));
}

function pintarRespuestaCar(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].name + "</td>";
    myTable += "<td>" + respuesta[i].brand + "</td>";
    myTable += "<td>" + respuesta[i].year + "</td>";
    myTable += "<td>" + respuesta[i].description + "</td>";
    myTable += "<td>" + respuesta[i].gama + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarCar(" +
      respuesta[i].idCar +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='borrarCar(" +
      respuesta[i].idCar +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoCar").html(myTable);
}

function guardarCar() {
  let var2 = {
    name: $("#CarName").val(),
    brand: $("#CarBrand").val(),
    year: $("#CarYear").val(),
    description: $("#CarDescription").val(),
    idGama: { idGama: +$("#Select-Gama").val() },
  };
  console.log(var2);
  $.ajax({
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "JSON",
    data: JSON.stringify(var2),

    url: "http://localhost:8080/api/Car/save",

    success: function (respuesta) {
      console.log(respuesta);
      console.log("Se guardo correctamente el vehículo");
      alert("Se guardo correctamente el vehículo");
      window.location.reload();
    },

    error: function (_jqXHR, _textStatus, _errorThrown) {
      window.location.reload();
      alert("No se guardo correctamente el vehículo");
    },
  });
}

function actualizarCar(idElemento) {
  let myData = {
    idCar: idElemento,
    name: $("#CarName").val(),
    brand: $("#CarBrand").val(),
    year: $("#CarYear").val(),
    description: $("#CarDescription").val(),
    idGama: { idGama: +$("#Select-Gama").val() },
  };
  console.log(myData);
  let dataToSend = JSON.stringify(myData);

  $.ajax({
    url: "http://localhost:8080//api/Car/update",
    type: "PUT",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",

    success: function (_respuesta) {
      $("#CarName").val("");
      $("#CarBrand").val("");
      $("#CarYear").val("");
      $("#CarDescription").val("");
      $("#Select-Gama").val("");
      autoInicioCar();
      alert("Se ha actualizado correctamente el vehículo");
    },
  });
}

function borrarCar(idElemento) {
  let myData = {
    id: idElemento,
  };
  let dataToSend = JSON.stringify(myData);

  $.ajax({
    url: "http://localhost:8080/api/Car/" + idElemento,
    type: "DELETE",
    data: dataToSend,
    contentType: "application/JSON",
    dataType: "JSON",
    success: function (_respuesta) {
      $("#resultadoCar").empty();
      autoInicioCar();
      alert("Se ha borrado correctamente el vehículo");
    },
  });
}

function addInSelect(data, selectName) {
  let selector = document.getElementById(selectName);
  for (let i = 0; i < data.length; i++) {
    selector.options[i] = new Option(data[i].name);
  }
}
