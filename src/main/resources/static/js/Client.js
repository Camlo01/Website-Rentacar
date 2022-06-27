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
    myTable += `<td><button onclick='updateClient(${client.idClient})'> Actualizar</button></td>`;
    myTable += `<td><button onclick='deleteClient(${client.idClient})'> Borrar</button></td>`;
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
        clientData();
      }
    })
    .catch(function (error) {
      console.log("Problema al crear el cliente: " + error);
    });
}

function updateClient(idClient) {
  let email = document.getElementById("ClientEmail").value;
  let password = document.getElementById("ClientPassword").value;
  let name = document.getElementById("ClientName").value;
  let age = document.getElementById("ClientAge").value;

  function noOneNull() {
    if (
      email.length == 0 ||
      password.length == 0 ||
      name.length == 0 ||
      age.length == 0
    ) {
      return false;
    }
    return true;
  }

  let data = {
    idClient: idClient,
    email: email,
    password: password,
    name: name,
    age: age,
  };
  console.log(data);

  if (noOneNull()) {
    fetch("http://localhost:8080/api/Client/update", {
      method: "PUT",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then(function (response) {
        if (response.status == 201) {
          console.log("Se actualizó el cliente");
        }
        clientData();
      })
      .catch(function (error) {
        alert("error al actualizar el cliente " + error);
      });
  } else {
    alert("Hay un elementro vacio");
  }
}

function deleteClient(idClient) {
  fetch("http://localhost:8080/api/Client/delete/" + idClient, {
    method: "DELETE",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 500) {
        alert("No se pudo eliminar, verifica que no tengo un carro asociado");
      }
      if (response.status == 204) {
        clientData();
      }
    })
    .catch(function (error) {
      console.log("El error fue: " + error);
    });
}