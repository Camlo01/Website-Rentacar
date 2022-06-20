function gamaData() {
  fetch("http://localhost:8080/api/Gama/all")
    .then((response) => response.json())
    .then(function (data) {
      pintarRespuestaGama(data);
      addInSelect(data, "Select-Gama");
    })
    .catch((error) => console.log("Problema al mostrar la gama: " + error));
}

function pintarRespuestaGama(respuesta) {
  let myTable = "<table>";
  for (i = 0; i < respuesta.length; i++) {
    myTable += "<tr>";
    myTable += "<td>" + respuesta[i].name + "</td>";
    myTable += "<td>" + respuesta[i].description + "</td>";
    myTable +=
      "<td> <button onclick=' actualizarGama(" +
      respuesta[i].idGama +
      ")'>Actualizar</button>";
    myTable +=
      "<td> <button onclick='borrarGama(" +
      respuesta[i].idGama +
      ")'>Borrar</button>";
    myTable += "</tr>";
  }
  myTable += "</table>";
  $("#resultadoGama").html(myTable);
}

function saveGama() {
  let name = document.getElementById("GamaName").value;
  let description = " " + document.getElementById("GamaDescription").value;
  let data = {
    name: name,
    description: description,
  };
  fetch("http://localhost:8080/api/Gama/save", {
    method: "POST",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 201) {
        console.log("Se creó la gama correctamente");
      }
    })
    .catch(function (error) {
      console.log("Problema al guardar la gama: " + error);
    });
}

function updateGama(idElemento) {
  if (
    $("#GamaName").val().length == 0 ||
    $("#GamaDescription").val().length == 0
  ) {
    alert("Todos los campos son obligatorios");
  } else {
    let myData = {
      idGama: idElemento,
      name: $("#GamaName").val(),
      description: $("#GamaDescription").val(),
    };
    console.log(myData);
    let dataToSend = JSON.stringify(myData);
    $.ajax({
      url: "http://localhost:8080/api/Gama/update",
      type: "PUT",
      data: dataToSend,
      contentType: "application/JSON",
      datatype: "JSON",
      success: function (respuesta) {
        $("#GamaName").val("");
        $("#GamaDescription").val("");
        autoInicioGama();
        alert("se ha Actualizado correctamente la gama");
      },
    });
  }
}

function deleteGama(idElemento) {
  let myData = {
    id: idElemento,
  };
  let dataToSend = JSON.stringify(myData);
  $.ajax({
    url: "http://localhost:8080/api/Gama/" + idElemento,
    type: "DELETE",
    data: dataToSend,
    contentType: "application/JSON",
    datatype: "JSON",
    success: function (respuesta) {
      $("#resultadoGama").empty();
      autoInicioGama();
      alert("Se ha Eliminado.");
    },
  });
}
