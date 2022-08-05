/**
 * Metodo que trae todos los carros y son insertados en la sección de vehiculos
 */

let pageByDefect = 0;

function selectPageVehicle(whatDo) {
  if (whatDo == "INCREMENT") {
    pageByDefect++;
  } else if (whatDo == "DECREMENT") {
    pageByDefect--;
  }
  if (pageByDefect < 0) {
    pageByDefect = 0;
  }
  console.log(pageByDefect);
  loadVehiclesPageableSection();
}
function loadVehiclesPageableSection() {
  let isLog = isLogged();

  const size = 8;
  let page = pageByDefect;
  fetch(`${URL}/car/home_cars/size=${size}page=${page * size}`)
    .then((response) => response.json())
    .then((data) => {
      let allCards = `<hr>`;
      data.forEach((car) => {
        allCards += `<article class="vehicle-card">`;
        allCards += `  <div class="s-img-vehicle">IMAGEN DEL VEHÍCULO</div>`;
        allCards += `  <p class="vehicle--card-title"><b>Nombre: </b>${car.name}</p>`;
        allCards += `  <p class="vehicle--card-brand"><b>Marca: </b>${car.brand}</p>`;
        allCards += `  <p class="vehicle--card-year"><b>Año: </b>${car.year}</p>`;
        allCards += `  <p class="vehicle--card-description"><b>Descripción:</b>${car.description}</p>`;
        // allCards += `<button onclick="reservateVehicle(${car.idCar})">Reservar!</button>`;
        if (isLog) {
          allCards += `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalToReservateCar${
            car.idCar
          }">
                         Reservar!
                      </button>

                    <div class="modal fade" id="modalToReservateCar${
                      car.idCar
                    }" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Modal </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                          ${reserveThisVehicle(
                            car.idCar,
                            car.name,
                            car.brand,
                            car.year,
                            car.description,
                            car.gama
                          )}
                        
                          </div>
                          <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                          <button type="button" class="btn btn-primary">Reservar</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    `;
        }

        allCards += `</article>`;
      });
      document.getElementById("whereToLoadCars").innerHTML = allCards;
    });
}

function lastCarAddedBookable() {
  fetch(`${URL}/car/lastCarAdded`)
    .then((response) => response.json())
    .then((car) => {
      let card = `<article class="vehicle-card card-lastCarAdded">`;
      card += ` <div class="main-lastCarAdded--card"><p>ÚLTIMO CARRO AGREGADO </p><div>`;
      card += `  <div class="s-img-vehicle">IMAGEN DEL VEHÍCULO</div>`;
      card += `  <p class="vehicle--card-title"><b>Nombre: </b>${car.name}</p>`;
      card += `  <p class="vehicle--card-brand"><b>Marca: </b>${car.brand}</p>`;
      card += `  <p class="vehicle--card-year"><b>Año: </b>${car.year}</p>`;
      card += `  <p class="vehicle--card-description"><b>Descripción:</b>${car.description}</p>`;
      card += `</article>`;
      document.getElementById("whereToLoadCarAdded").innerHTML = card;
    });
}

function reservateVehicle(idCar) {
  console.log(idCar);

  reservateVehicle;
}

function isLogged() {
  if (localStorage.getItem("logged") == "si") {
    return true;
  }
  return false;
}

function reserveThisVehicle(id, name, brand, year, description, gama) {
  return `<div>
    <h2>Reservando...</h2>
    <h3>Estás reservando este vehículo</h3>

    <p>Nombre: ${name}</p>
    <p>Marca: ${brand}</p>
    <p>Año: ${year}</p>
    <p>Gama: ${gama}</p>
    <p>Descripción: ${description}</p>
    <label for="start">Inicio:</label>
    <input id="startDate${id}" type="date" max="2022-12-31">

    <label for="end">Entrega:</label>
    <input id="endDate${id}" type="date" max="2022-08-31">
    <hr>
    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="reservateCar(${id},startDate${id},endDate${id})">RESERVAR</button>

    

          </div>`;
}

function reservateCar(id, start, end) {
  let data = {
    startDate: start.value,
    devolutionDate: end.value,
    car_id: id,
    client_id: localStorage.getItem("idClient"),
  };
  fetch(`${URL}/reservation/reservate`, {
    method: "POST",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  });
}
