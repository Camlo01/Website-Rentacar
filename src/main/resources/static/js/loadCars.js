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
  } else if (pageByDefect > 2) {
    pageByDefect = 2;
  }
  console.log(pageByDefect);
  loadVehiclesPageableSection();
}
function loadVehiclesPageableSection() {
  const size = 8;
  let page = pageByDefect;
  fetch(`${URLapi}/car/home_cars/size=${size}page=${page * size}`)
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
        if (isLogged()) {
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
  fetch(`${URLapi}/car/lastCarAdded`)
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
    <input id="startDate${id}" type="date" value="${today}" min="${today}">

    <label for="end">Entrega:</label>
    <input id="endDate${id}" type="date"  value="${today}" min="${today}">
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

  const reserveVehicle = new Promise(function (resolve, reject) {
    fetch(`${URLapi}/reservation/reserve-vehicle`, {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then((response) => {
        console.log(response);
        resolve(response.status);
      })
      .catch((err) => {
        console.log(err);
        reject("Hubo un problema al comunicar con el servidor");
      });
  });

  reserveVehicle.then((status) => {
    console.log(status);
    if (status == 405) {
      fetch(`${URLapi}/reservation/whenCanBeReserveThisVehicle`, {
        method: "POST",
        body: JSON.stringify(data),
        headers: { "Content-type": "application/json; charset=UTF-8" },
      })
        .then((response2) => response2.json())
        .then(function (data2) {
          let previousReservation = data2[0];
          let nextReservation = data2[1];

          let messageWarning = `
          <div class="alert alert-warning d-flex align-items-center" role="alert">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Warning:">
              <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div><strong>Hubo un problema!</strong> No puedes reservar este vehículo durante esta fecha</div>
          </div>
          `;
          document.getElementById("whereToPrintResultBeforeReserve").innerHTML =
            messageWarning;
        });
    } else if (status == 201) {
      let messageSuccess = `
      <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
  </symbol>
  <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
  </symbol>
  <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
  </symbol>
</svg>
      <div class="alert alert-success d-flex align-items-center" role="alert">
        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
        <div><strong>Felicidades!</strong> Su vehículo fue reservado exitosamente.</div>
      </div>
      `;
      document.getElementById("whereToPrintResultBeforeReserve").innerHTML =
        messageSuccess;
    } else {
    }
  });

  reserveVehicle.catch(() => {
    let messageDanger = `<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
      <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
    </symbol>
    <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
      <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
    </symbol>
    <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
      <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
    </symbol>
  </svg>
  
  <div class="alert alert-danger d-flex align-items-center" role="alert">
  <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
  <div><stronger>Ups!</stronger> Hubo un problema con el servidor, intenta más tarde o comunicate con el equipo de soporte
  </div>
</div> 

  `;
    document.getElementById("whereToPrintResultBeforeReserve").innerHTML =
      messageDanger;
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
