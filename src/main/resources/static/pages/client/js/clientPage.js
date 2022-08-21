const urlOfApi = `http://localhost:8080/api/rentacar`;
/**
 * Run automatically
 */
reservationHistory();

function reservationHistory() {
  let key = {
    keyClient: localStorage.getItem("KeyClient"),
  };

  const history = new Promise(function (resolve, reject) {
    fetch(`${urlOfApi}/reservation/my-reservation-history`, {
      method: "POST",
      body: JSON.stringify(key),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then((response) => response.json())
      .then(function (reservations) {
        let table = `<table class="table">
                      <thead>
                         <tr>
                         <th scope="col">Código</th>
                         <th scope="col">estatus</th>
                         <th scope="col">Fecha inicio</th>
                         <th scope="col">fecha entrega</th>
                         <th scope="col">vehículo</th>
                         <th scope="col">¿Qué deseas hacer?</th>
                         </tr>
                      </thead>
                    <tbody>`;

        reservations.forEach((reservation) => {
          table += ` <tr>
                       <th scope="row">${reservation.code}</th>
                       <td>${handleStatus(reservation.reservationStatus)}</td>
                       <td>${reservation.startDate.substr(0, 10)}</td>
                       <td>${reservation.devolutionDate.substr(0, 10)}</td>
                       <td>${reservation.car.name}</td>
                     

                       <!-- Botón a modal con detalles y botón para cancelar -->
                       <td><a class="btn btn-primary" data-bs-toggle="modal" href="#modalDetalles${
                         reservation.idReservation
                       }" role="button">Detalles</a><td>
                       
                       
                       <!-- Model detalles con botón para confirmar cancelación -->                    
                       <div class="modal fade" id="modalDetalles${
                         reservation.idReservation
                       }" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
                       <div class="modal-dialog modal-dialog-centered">
                         <div class="modal-content">
                           <div class="modal-header">
                             <h5 class="modal-title" id="exampleModalToggleLabel">Reservación con código ${
                               reservation.code
                             }</h5>
                             <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                           </div>
                           <div class="modal-body"><p>Reservaste el vehículo: ${
                             reservation.car.name
                           }</p>
                          <p>Fecha de inicio: ${reservation.startDate.substr(0, 10)}</p>
                          <p>Fechga de entrega: ${
                            reservation.devolutionDate.substr(0, 10)
                          }</p>
                          <p>Reservaste el vehículo: ${reservation.car.name}</p>
                           </div>`;
          if (reservation.reservationStatus == "ACTIVE") {
            table += ` <div class="modal-footer">
                            <!-- Buttón to confirm cancelation -->                    
                            <button class="btn btn-danger" data-bs-target="#confirmCancelReservation${reservation.idReservation}" data-bs-toggle="modal">Cancelar reservación</button>                 
                            </div>
                            </div>
                          </div>
                        </div>

                        <!-- Modal with button to confirm cancelation -->                     
                        <div class="modal fade" id="confirmCancelReservation${reservation.idReservation}" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
                          <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalToggleLabel2">Cancelando Reservación</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                              </div>
                              <div class="modal-body">
                              ¿Seguro que deseas cancelar esta reservación? ${reservation.code}
                              </div>
                        <!-- Button to cancel reservation confirmed -->                    
                              <div class="modal-footer">
                                <button class="btn btn-danger" data-bs-toggle="modal" onclick="cancelReservation()">Confirmar</button>
                              </div>
                            </div>
                          </div>
                        </div>
 `;
          } else {
            table += `</div>div></div></div>`;
          }
        });

        table += `</tbody></table>`;

        document.getElementById("wherePrintReservations").innerHTML = table;
      })
      .catch((err) => {
        console.log(err);
        reject("Problema");
      });
  });
}

function handleStatus(status) {
  let statusColumn = `<div style="font-weight: bold;`;

  switch (status) {
    case "REQUESTED":
      statusColumn += `color:blue`;

      break;
    case "ACTIVE":
      statusColumn += `color:green;`;

      break;
    case "CANCELLED":
      statusColumn += `color:#c80101`;

      break;
    case "POSTPONED":
      statusColumn += `color:#0064ff`;

      break;
    case "DENIED":
      statusColumn += `color:red`;

      break;
    case "COMPLETED":
  }

  return (statusColumn += `">${status}</div>`);
}

function cancelReservation() {
  alert("La reservación fue cancelada con éxito");
}
