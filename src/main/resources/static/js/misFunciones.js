
function autoInicioCar() {
  console.log("se esta ejecutando tabla Car");
  $.ajax({
    url: "http://localhost:8080/api/Car/all",
    type: "GET",
    datatype: "JSON",
    success: function (respuesta) {
      console.log(respuesta);
      pintarRespuestaCar(respuesta);
      let $select = $("#Select-Car");
      $.each(respuesta, function (id, name) {
        $select.append(
          "<option value=" + name.id + ">" + name.name + "</option>"
        );
        console.log("select " + name.id);
      });
    },
  });
}

function pintarRespuestaCar(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].name + "</td>";
    myTable += "<td>" + respuesta[i].brand + "</td>";
    myTable += "<td>" + respuesta[i].year + "</td>";
    myTable += "<td>" + respuesta[i].description + "</td>";
    myTable += "<td>" + respuesta[i].NameGama + "</td>";
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

/////CLIENT//////

function autoInicioClient() {
  console.log("se esta ejecutando tabla Cliente");
  $.ajax({
    url: "http://localhost:8080/api/Client/all",
    type: "GET",
    datatype: "JSON",
    success: function (respuesta) {
      console.log(respuesta);
      pintarRespuestaClient(respuesta);
      let $select = $("#Select-Client");
      $.each(respuesta, function (_id, name) {
        $select.append(
          "<option value=" + name.id + ">" + name.name + "</option>"
        );
        console.log("select " + name.id);
      });
    },
  });
}

function pintarRespuestaClient(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].email + "</td>";
    myTable += "<td>" + respuesta[i].password + "</td>";
    myTable += "<td>" + respuesta[i].name + "</td>";
    myTable += "<td>" + respuesta[i].age + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarClient(" +
      respuesta[i].idClient +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='borrarClient(" +
      respuesta[i].idClient +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoClient").html(myTable);
}

function guardarClient() {
  let var2 = {
    email: $("#ClientEmail").val(),
    password: $("#ClientPassword").val(),
    name: $("#ClientName").val(),
    age: $("#ClientAge").val(),
  };

  $.ajax({
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "JSON",
    data: JSON.stringify(var2),

    url: "http://localhost:8080/api/Client/save",

    success: function (_respuesta) {
      console.log(_respuesta);
      console.log("Se guardo correctamente la Client");
      alert("Se guardo correctamente la Client");
      window.location.reload();
    },

    error: function (_jqXHR, _textStatus, _errorThrown) {
      window.location.reload();
      alert("No se guardo correctamente el Client");
    },
  });
}

/**
 *
 *
 * CRUD faltante
 */

function actualizarClient(idElemento) {
  let myData = {
    idClient: idElemento,
    email: $("#ClientEmail").val(),
    password: $("#ClientPassword").val(),
    name: $("#ClientName").val(),
    age: $("#ClientAge").val(),
  };
  console.log(myData);
  let dataToSend = JSON.stringify(myData);
  $.ajax({
    url: "http://localhost:8080/api/Client/update",
    type: "PUT",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",
    success: function (_respuesta) {
      $("#ClientEmail").val("");
      $("#ClientPassword").val("");
      $("#ClientName").val("");
      $("#ClientAge").val("");
      autoInicioClient();
      alert("Se ha actualizado correctamente el client");
    },
  });
}

function borrarClient(idElemento) {
  let myData = {
    id: idElemento,
  };
  let dataToSend = JSON.stringify(myData);
  $.ajax({
    url: "http://localhost:8080/api/Client/" + idElemento,
    type: "DELETE",
    data: dataToSend,
    contentType: "application/JSON",
    dataType: "JSON",
    success: function (respuesta) {
      $("#resultadoClient").empty();
      autoInicioClient();
      alert("Se ha borrado correctamente el client");
    },
  });
}

/////RESERVATION//////

function autoInicioReservation() {
  console.log("se esta ejecutando tabla Reservation");
  $.ajax({
    url: "http://localhost:8080/api/Reservation/all",
    type: "GET",
    datatype: "JSON",
    success: function (respuesta) {
      console.log(respuesta);
      pintarRespuestaReservation(respuesta);
      let $select = $("#Select-Reservation");
      $.each(respuesta, function (_id, name) {
        $select.append(
          "<option value=" + name.id + ">" + name.name + "</option>"
        );
        console.log("select " + name.id);
      });
    },
  });
}

function pintarRespuestaReservation(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].startDate + "</td>";
    myTable += "<td>" + respuesta[i].devolutionDate + "</td>";
    myTable += "<td>" + respuesta[i].status + "</td>";
    myTable += "<td>" + respuesta[i].client + "</td>";
    myTable += "<td>" + respuesta[i].car + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarReservation(" +
      respuesta[i].idReservation +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='borrarReservation(" +
      respuesta[i].idReservation +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoReservation").html(myTable);
}

function guardarReservation() {
  let var2 = {
    startDate: $("#startDate").val(),
    devolutionDate: $("#devolutionDate").val(),
    status: $("#status").val(),
    car: { idCar: +$("#Select-Car").val() },
    client: { idClient: +$("#Select-Client").val() },
  };

  $.ajax({
    type: "POST",
    contentType: "application/json",
    datatype: "JSON",
    data: JSON.stringify(var2),

    url: "http://localhost:8080/api/Reservation/save",

    success: function (respuesta) {
      console.log(respuesta);
      console.log("Se guardo correctamente la reservación");
      alert("Se ha guardado Correctamente la reservación!");
      window.location.reload();
    },
    error: function (_jqXHR, _textStatus, _errorThrown) {
      window.location.reload();
      alert("No se guardo Correctamente la reservación!");
    },
  });
}

function actualizarReservation(idElemento) {
  let myData = {
    idReservation: idElemento,

    startDate: $("#startDate").val(),
    devolutionDate: $("#devolutionDate").val(),
    status: $("#status").val(),
    car: { idCar: +$("#Select-Car").val() },
    client: { idClient: +$("#Select-Client").val() },
  };
  console.log(myData);
  let dataToSend = JSON.stringify(myData);

  $.ajax({
    url: "http://localhost:8080/api/Reservation/update",
    type: "PUT",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",

    success: function (_respuesta) {
      $("#startDate").val("");
      $("#devolutionDate").val("");
      $("#status").val("");
      $("#Select-Car").val(),
        $("#Select-Client").val(),
        autoInicioReservation();
      alert("Se ha actualizado correctamente la reservación");
    },
  });
}

function borrarReservation(idElemento) {
  let myData = {
    id: idElemento,
  };
  let dataToSend = JSON.stringify(myData);

  $.ajax({
    url: "http://localhost:8080/api/Reservation/" + idElemento,
    type: "DELETE",
    data: dataToSend,
    contentType: "application/JSON",
    dataType: "JSON",
    success: function (response) {
      console.log(response);
      $("#resultadoReservation").empty();
      autoInicioReservation();
      alert("se ha Eliminado Correctamente la Reservación!");
    },
  });
}

/////MESSAGES//////

function autoInicioMessage() {
  console.log("se esta ejecutando tabla Message");
  $.ajax({
    url: "http://localhost:8080/api/Message/all",
    type: "GET",
    datatype: "JSON",
    success: function (respuesta) {
      console.log(respuesta);
      pintarRespuestaMessage(respuesta);
      let $select = $("#Select-Message");
      $.each(respuesta, function (_id, name) {
        $select.append(
          "<option value=" + name.id + ">" + name.name + "</option>"
        );
        console.log("select " + name.id);
      });
    },
  });
}

function pintarRespuestaMessage(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].messageText + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarMessage(" +
      respuesta[i].idMessage +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='borrarMessage(" +
      respuesta[i].idMessage +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoMessage").html(myTable);
}

function guardarMessage() {
  let var2 = {
    messageText: $("#messageText").val(),
    idCliente: { idCliente: +$("#Select-Cliente").val() },
    idCar: { idCar: +$("#Select-Car").val() },
  };

  $.ajax({
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "JSON",
    data: JSON.stringify(var2),

    url: "http://localhost:8080/api/Message/save",

    success: function (respuesta) {
      console.log(respuesta);
      console.log("Se guardo correctamente el mensaje");
      alert("Se guardo correctamente el mensaje");
      window.location.reload();
    },

    error: function (_jqXHR, _textStatus, _errorThrown) {
      window.location.reload();
      alert("No se guardo correctamente el mensaje");
    },
  });
}

function actualizarMessage(idElemento) {
  let myData = {
    idMessage: idElemento,
    messageText: $("#messagetext").val(),
  };
  console.log(myData);
  let dataToSend = JSON.stringify(myData);
  $.ajax({
    url: "http://localhost:8080/api/Message/update",
    type: "PUT",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",
    success: function (_respuesta) {
      $("#messageText").val("");
      autoInicioMessage();
      alert("se ha Actualizado correctamente el Mensaje");
    },
  });
}

function borrarMessage(idElemento) {
  let myData = {
    idMessage: idElemento,
  };
  let dataToSend = JSON.stringify(myData);

  $.ajax({
    url: "http://localhost:8080/api/Message/" + idElemento,
    type: "DELETE",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",
    success: function (_respuesta) {
      $("#resultadoMessage").empty();
      autoInicioMessage();
      alert("Se ha Eliminado el mensaje");
    },
  });
}
