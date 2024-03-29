function getReportStatus() {
  fetch(`${URL}/reservation/report-status`)
    .then((response) => response.json())
    .then(function (data) {
      console.log(data);
      pintarRespuesta(data);
    })
    .catch((error) => console.log("Problema al mostrar el status: " + error));
}

function getReportClients() {
  fetch(`${URL}/reservation/report-clients`)
    .then((response) => response.json())
    .then(function (data) {
      console.log(data);
      pintarRespuestaClientes(data);
    })
    .catch((error) => console.log("Errors: " + error));
}

function getReportDates() {
  fetch(`${URL}/reservation/report-dates/`)
    .then((response) => response.json())
    .then(function (data) {
      console.log(data);
      pintarRespuestaClientes(data);
    })
    .catch((error) => console.log("Errors: " + error));
}

function pintarRespuestaClientes(respuesta) {
  let myTable = "<table>";
  myTable += "<tr>";

  for (i = 0; i < respuesta.length; i++) {
    myTable += "<th>total</th>";
    myTable += "<td>" + respuesta[i].total + "</td>";
    myTable += "<td>" + respuesta[i].client.name + "</td>";
    myTable += "<td>" + respuesta[i].client.email + "</td>";
    myTable += "<td>" + respuesta[i].client.age + "</td>";
    myTable +=
      "<td><a href= 'javascript:pintarRespuestaClientesReservaciones(" +
      respuesta[i].client.reservations +
      ");'>Ver reservaciones</a></td>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoClientes").html(myTable);
}

function pintarRespuestaClientesReservaciones(respuesta) {
  console.log(respuesta);
  let myTable = "<table>";
  myTable += "<tr>";

  for (i = 0; i < respuesta.length; i++) {
    myTable += "<th>total</th>";
    myTable += "<td>" + respuesta[i].startDate + "</td>";

    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoClientes").html(myTable);
}

function pintarRespuestaDate(respuesta) {
  let myTable = "<table>";
  myTable += "<tr>";

  for (i = 0; i < respuesta.length; i++) {
    myTable += "<th>total</th>";
    myTable += "<td>" + respuesta[i].devolutionDate + "</td>";
    myTable += "<td>" + respuesta[i].startDate + "</td>";
    myTable += "<td>" + respuesta[i].status + "</td>";

    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoDate").html(myTable);
}

function pintarRespuesta(respuesta) {
  let myTable = "<table>";
  myTable += "<tr>";
  myTable += "<th>completadas</th>";
  myTable += "<td>" + respuesta.completed + "</td>";
  myTable += "<th>canceladas</th>";
  myTable += "<td>" + respuesta.cancelled + "</td>";
  myTable += "</tr>";
  myTable += "</table>";
  $("#resultadoStatus").html(myTable);
}
