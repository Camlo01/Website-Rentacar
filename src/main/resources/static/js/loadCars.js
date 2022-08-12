/**
 * Metodo que trae todos los carros y son insertados en la sección de vehiculos
 */

let pageByDefect = 0;
var today = todaysDate();

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
                            car.gama.name
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
    <input id="startDate${id}" type="date" value="${today}">

    <label for="end">Entrega:</label>
    <input id="endDate${id}" type="date"  value="${today}" >
    <hr>
    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="reservateCar(${id},startDate${id},endDate${id})">RESERVAR</button>
          </div>`;
}

function reservateCar(id, start, end) {
  let date1awd = new Date(start.value);
  let date2awd = new Date(end.value);

  time_difference = differenceBetweenDates(date1awd, date2awd);

  console.log(time_difference);

  let data = {
    startDate: start.value,
    devolutionDate: end.value,
    car_id: id,
    client_id: localStorage.getItem("idClient"),
  };
  fetch(`${URL}/reservation/reserve-vehicle`, {
    method: "POST",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  }).then((response) => {
    if (response.status == 405) {
      alert("No disponible, reserva para otra fecha u otro vehículo");
    } else if (response.status == 201) {
      alert("Tu vehículo fue reservado exitosamente");
    } else {
      console.log(response);
      alert("Ocurrió un error");
    }
  });
}

function todaysDate() {
  let todayRaw = new Date();
  todayRaw.toISOString().split("T")[0];
  let year = todayRaw.getFullYear();
  let month = parseInt(todayRaw.getMonth() + 1);
  let day = parseInt(todayRaw.getDate());
  if (month < 10) {
    return (today = `${year}-0${month}-${day}`);
  }
  return (today = `${year}-${month}-${day}`);
}

function differenceBetweenDates(date1, date2) {
  const date1utc = Date.UTC(
    date1.getFullYear(),
    date1.getMonth(),
    date1.getDate()
  );
  const date2utc = Date.UTC(
    date2.getFullYear(),
    date2.getMonth(),
    date2.getDate()
  );
  day = 1000 * 60 * 60 * 24;
  return (date2utc - date1utc) / day;
}
