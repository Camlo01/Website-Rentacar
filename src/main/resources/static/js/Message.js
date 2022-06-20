function messageData() {
  fetch("http://localhost:8080/api/Message/all")
    .then((response) => response.json())
    .then(function (data) {
      pintarRespuestaMessage(data);
    })
    .catch((error) => console.log("Problema al mostrar messages: " + error));
}

function pintarRespuestaMessage(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].messageText + "</td>";
    myTable += "<td>" + respuesta[i].car + "</td>";
    myTable += "<td>" + respuesta[i].client + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarMessage(" +
      respuesta[i].idMessage +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='deleteMessage(" +
      respuesta[i].idMessage +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoMessage").html(myTable);
}

function saveMessage() {
  let var2 = {
    messageText: $("#messageText").val(),
    idCliente: { idCliente: +$("#table-message-select-client").val() },
    idCar: { idCar: +$("#table-message-select-car").val() },
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

function updateMessage(idElemento) {
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

function deleteMessege(idElemento) {
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
