function clientData() {
  fetch("http://localhost:8080/api/Client/all")
    .then((response) => response.json())
    .then(function (data) {
      innerClientData(data);
      addInSelect(data, "table-message-select-client");
      addInSelect(data, "table-reser-select-client");
    })
    .catch((error) => console.log("Problema al traer datos client" + error));
}

function innerClientData(data) {
  let myTable = `<table>`;
  data.forEach((client) => {
    myTable += `<tr>`;
    myTable += `<td> ${client.name} </td>`;
    myTable += `<td> ${client.email} </td>`;
    myTable += `<td> ${client.password} </td>`;
    myTable += `<td><button onclick='updateCar(${client.idGama})'> Actualizar</button></td>`;
    myTable += `<td><button onclick='deleteCar(${client.idGama})'> Borrar</button></td>`;
    myTable += `</tr>`;
  });
  myTable += `</table>`;
  document.getElementById(`resultadoClient`).innerHTML = myTable;
}


function saveClient() {
  let email = document.getElementById("ClientEmail").value;
  let password = document.getElementById("ClientPassword").value;
  let name = document.getElementById("ClientName").value;
  let age = document.getElementById("ClientAge").value;

  let data = {
    email: email,
    password: password,
    name: name,
    age: age,
  };

  fetch("http://localhost:8080/api/Client/save", {
    method: "POST",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 201) {
        console.log("Se creó el usuario correctamente");
      }
    })
    .catch(function (error) {
      console.log("Problema al crear el cliente: " + error);
    });
}

function updateClient(idElemento) {
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

function deleteClient(idElemento) {
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
