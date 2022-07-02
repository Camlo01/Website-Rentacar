function carData() {
  fetch("http://localhost:8080/api/Car/all")
    .then((response) => response.json())
    .then(function (data) {
      innerCarData(data);
      addInSelectCar(data, "table-message-select-car");
      addInSelectCar(data, "table-reser-select-car");
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
    try {
      myTable += `<td> ${car.gama.name} </td>`;
      car.messages.forEach((mensaje) => {
        myTable += `<td> COMENTARIO: ${mensaje.messageText}</td>`;
      });
    } catch (error) {}
    myTable += `<td><button onclick="updateCar(${car.idCar})"> Actualizar</button></td>`;
    myTable += `<td><button onclick="deleteCar(${car.idCar})"> Borrar</button></td>`;
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
  let gama = document.getElementById("Select-Gama").value;
  let idGama = parseInt(getIdOfSelect(gama));

  fetch("http://localhost:8080/api/Gama/" + idGama, {
    method: "GET",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then((response) => response.json())
    .then(function (gama) {
      let data = {
        name: name,
        brand: brand,
        year: year,
        description: description,
        gama: gama,
      };

      console.log(data);
      fetch("http://localhost:8080/api/Car/save", {
        method: "POST",
        body: JSON.stringify(data),
        headers: { "Content-type": "application/json; charset=UTF-8" },
      })
        .then(function (response) {
          if (response.status == 201) {
            console.log("Se creó el carro correctamente");
          }
          carData();
        })
        .catch(function (error) {
          console.log("Problema al guardar el carro: " + error);
        });
    });
}

function updateCar(idCar) {
  let name = document.getElementById("CarName").value;
  let brand = document.getElementById("CarBrand").value;
  let year = document.getElementById("CarYear").value;
  let description = document.getElementById("CarDescription").value;

  function noOneNull() {
    if (
      name.length == 0 ||
      brand.length == 0 ||
      year.length == 0 ||
      description.length == 0
    ) {
      return false;
    }
    return true;
  }

  let data = {
    idCar: idCar,
    name: name,
    brand: brand,
    year: year,
    description: description,
  };

  if (noOneNull()) {
    fetch("http://localhost:8080/api/Car/update", {
      method: "PUT",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then(function (response) {
        console.log(response);
        if (response.status == 201) {
          console.log("Se actualizó el carro");
          carData();
        }
      })
      .catch(function (error) {
        alert("error al actualizar el carro " + error);
      });
  } else {
    alert("Hay campos vacíos!");
  }
}

function deleteCar(idCar) {
  fetch("http://localhost:8080/api/Car/delete/" + idCar, {
    method: "DELETE",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.status == 500) {
        alert("No puedes eliminar un carro asociado");
      }
      carData();
    })
    .catch(function (error) {
      console.log("error al eliminar el carro: " + error);
    });
}
