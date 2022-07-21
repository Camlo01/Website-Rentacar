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
  const size = 8;
  let page = pageByDefect;
  fetch(
    `http://localhost:8080/api/Car/home_cars/size=${size}page=${page * size}`
  )
    .then((response) => response.json())
    .then((data) => {
      // Se inicializa con una tarjeta de prueba
      // En caso de eliminar la tarjeta de muestra, colocar un nav vacío para que no inserte undefined
      let allCards = `
      <article class="vehicle-card">
          <div class="s-img-vehicle">xxxxxxxxxxxxxxxxxxxxxxxxxxxx</div>
          <p class="vehicle--card-title"><b>Nombre: </b>CARD PRUEBA</p>
          <p class="vehicle--card-brand"><b>Marca: </b>MARCA PRUEBA</p>
          <p class="vehicle--card-year"><b>Año: </b>AÑO PRUEBA</p>
          <p class="vehicle--card-description"><b>Descripción:</b>DESCRIPCIÓN PRUEBA</p>
      </article>`;
      data.forEach((car) => {
        allCards += `<article class="vehicle-card">`;
        allCards += `  <div class="s-img-vehicle">IMAGEN DEL VEHÍCULO</div>`;
        allCards += `  <p class="vehicle--card-title"><b>Nombre: </b>${car.name}</p>`;
        allCards += `  <p class="vehicle--card-brand"><b>Marca: </b>${car.brand}</p>`;
        allCards += `  <p class="vehicle--card-year"><b>Año: </b>${car.year}</p>`;
        allCards += `  <p class="vehicle--card-description"><b>Descripción:</b>${car.description}</p>`;
        allCards += `<button onclick="reservateVehicle(${car.idCar})">Reservar!</button>`;
        // allCards += " <button onclick='"+reservateVehicle()+"' >Reservar!</button>;
        allCards += `</article>`;
      });
      document.getElementById("whereToLoadCars").innerHTML = allCards;
    });
}

function lastCarAddedBookable() {
  fetch("http://localhost:8080/api/Car/lastCarAdded")
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
  console.log(idCar)

  reservateVehicle
}
