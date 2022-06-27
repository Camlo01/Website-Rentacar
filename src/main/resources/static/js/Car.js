function carData() {
  fetch("http://localhost:8080/api/Car/all")
    .then((response) => response.json())
    .then(function (data) {
      innerCarData(data);
      addInSelect(data, "table-message-select-car");
      addInSelect(data, "table-reser-select-car");
    })
    .catch((error) => console.log("Problema al traer dator Car: " + error));
}

function innerCarData(data) {
  let myTable = `<table>`;
  data.forEach((car) => {
    myTable += `<tr>`;
    myTable += `<td> ${car.name} </td>`;
    myTable += `<td> ${car.brand} </td>`;
    myTable += `<td> ${car.year} </td>`;
    myTable += `<td> ${car.description} </td>`;
    myTable += `<td> ${car.gama.name} </td>`;
    // myTable += `<td> ${car.message.forEach.messageText} </td>`;
    myTable += `<td><button onclick='updateCar(${car.idGama})'> Actualizar</button></td>`;
    myTable += `<td><button onclick='deleteCar(${car.idGama})'> Borrar</button></td>`;
    myTable += `</tr>`;
  });
  myTable += `</table>`;
  document.getElementById(`resultadoCar`).innerHTML = myTable;
}

function saveCar() {
  let name = document.getElementById("CarName").value;
  let brand = document.getElementById("CarBrand").value;
  let year = document.getElementById("CarYear").value;
  let description = document.getElementById("CarDescription").value;
  let idGama = { idGama: document.getElementById("Select-Gama").value };

  let data = {
    name: name,
    brand: brand,
    year: year,
    description: description,
    idGama: idGama,
  };
  fetch("http://localhost:8080/api/Car/save", {
    method: "POST",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 201) {
        console.log("Se creó el carro correctamente");
      }
    })
    .catch(function (error) {
      console.log("Problema al guardar el carro: " + error);
    });
}

function updateCar(idCar) {
  let name = document.getElementById("CarName").value;
  let brand = document.getElementById("CarBrand").value;
  let year = document.getElementById("CarYear").value;
  let description = document.getElementById("CarDescription").value;
  let data = {
    idCar: idCar,
    name: name,
    brand: brand,
    year: year,
    description: description,
  };

  fetch("http://localhost:8080/api/Car/update", {
    method: "PUT",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 201) {
        console.log("Se actualizó el carro");
      }
      carData();
    })
    .catch(function (error) {
      alert("error al actualizar el carro " + error);
    });
}

function updateCarDeprecated(idElemento) {
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

function deteleCar(idElemento) {
  console.log(idElemento);
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
