function loadAllCars() {
  fetch("http://localhost:8080/api/Car/all")
    .then((response) => response.json())
    .then((data) => {
      let allCards;
      data.forEach((car) => {
        try {
          allCards += ` <article class="vehicle-card">`;
          allCards += `  <div class="s-img-vehicle">IMAGEN DEL VEHÍCULO</div>`;
          allCards += `  <p class="vehicle--card-title"><b>Nombre:</b>${car.name}</p>`;
          allCards += `  <p class="vehicle--card-brand"><b>Marca:</b>${car.brand}</p>`;
          allCards += `  <p class="vehicle--card-year"><b>Año:</b>${car.year}</p>`;
          allCards += `  <p class="vehicle--card-description"><b>Descripción:</b>${car.description}</p>`;
          allCards += `</article>`;
        } catch {}
      });

      document.getElementById("whereToLoadCars").innerHTML = allCards;
    });
}
