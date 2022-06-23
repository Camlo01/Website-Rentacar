function gamaData() {
  fetch("http://localhost:8080/api/Gama/all")
    .then((response) => response.json())
    .then(function (data) {
      innerGamaData(data);
      addInSelect(data, "Select-Gama");
    })
    .catch((error) => console.log("Problema al mostrar la gama: " + error));
}

function innerGamaData(data) {
  let myTable = `<table>`;
  data.forEach((gama) => {
    myTable += `<tr>`;
    myTable += `<td> ${gama.name} </td>`;
    myTable += `<td> ${gama.description} </td>`;
    myTable += `<td><button onclick="updateGama(${gama.idGama})">Actualizar</button></td>`;
    myTable += `<td><button onclick="deleteGama(${gama.idGama})">Borrar</button></td>`;
    myTable += `</tr>`;
  });
  myTable += `</table>`;
  document.getElementById(`resultadoGama`).innerHTML = myTable;
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
        gamaData();
      }
    })
    .catch(function (error) {
      console.log("Problema al guardar la gama: " + error);
    });
}

function updateGama(idElemento) {
  if (
    document.getElementById("GamaName").value.length == 0 ||
    document.getElementById("GamaDescription").value.length == 0
  ) {
    alert("hay campos vacíos");
  } else {
    let name = document.getElementById("GamaName").value;
    let description = document.getElementById("GamaDescription").value;

    let data = {
      idGama: idElemento,
      name: name,
      description: description,
    };

    fetch("http://localhost:8080/api/Gama/update", {
      method: "PUT",
      body: JSON.stringify(data),
      headers: { "Content-Type": "application/json" },
    })
      .then(function (response) {
        console.log(response);
        if (response.status == 201) {
          console.log("Se actualizó la gama");
          gamaData();
        }
      })
      .catch(function (error) {
        console.log("Problema al actualizar la gama: " + error);
      });
  }
}

function deleteGama(idGama) {
  fetch("http://localhost:8080/api/Gama/delete/" + idGama, {
    method: "DELETE",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 500) {
        alert("No pueder eliminar una gama que está asociada a un carro!");
      }
      if (response.status == 204) {
        gamaData();
      }
    })
    .catch(function (error) {
      console.log("El error fue: " + error);
    });
}
