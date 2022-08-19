function gamaData() {
  fetch(`${URLapi}/gama/all`)
    .then((response) => response.json())
    .then(function (data) {
      innerGamaData(data);
      addInSelectGama(data, "Select-Gama");
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
  fetch(`${URLapi}/gama/save`, {
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
  let name = document.getElementById("GamaName").value;
  let description = document.getElementById("GamaDescription").value;

  let data = {
    idGama: idElemento,
    name: name,
    description: description,
  };

  console.log(data);

  fetch(`${URLapi}/gama/update`, {
    method: "PUT",
    body: JSON.stringify(data),
    headers: { "Content-Type": "application/json" },
  })
    .then((response) => {
      console.log(response);
      if (response.status == 201) {
        console.log("Se actualizó la gama");
      }
      gamaData();
    })
    .catch((error) => {
      console.log("Problema al actualizar la gama: " + error);
    });
}

function deleteGama(idGama) {
  fetch(`${URLapi}/gama/delete/` + idGama, {
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
