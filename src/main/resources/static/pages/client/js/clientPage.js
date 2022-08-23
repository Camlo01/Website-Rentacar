const URLOfApi = `http://localhost:8080/api/rentacar`;
/**
 * Run automatically
 */
reservationHistory();

function reservationHistory() {
  let key = {
    keyClient: localStorage.getItem("KeyClient"),
  };

  const history = new Promise(function (resolve, reject) {
    fetch(`${URLOfApi}/reservation/my-reservation-history`, {
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
                          <p>Fecha de inicio: ${reservation.startDate.substr(
                            0,
                            10
                          )}</p>
                          <p>Fechga de entrega: ${reservation.devolutionDate.substr(
                            0,
                            10
                          )}</p>
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
                                <button class="btn btn-danger" data-bs-toggle="modal" onclick="cancelReservation(${reservation.idReservation})">Confirmar</button>
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

function cancelReservation(idReservation) {
  let ReservationAndKeyclient = {
    reservation: {
      idReservation: idReservation,
    },
    keyClient: {
      keyClient: localStorage.getItem("KeyClient"),
    },
  };
  const cancelation = new Promise(function (resolve, reject) {
    fetch(`${URLOfApi}/reservation/cancel-reservation`, {
      method: "PUT",
      body: JSON.stringify(ReservationAndKeyclient),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then((response) => {
        console.log(response);
        resolve(response.status);
        response.json();
      })
      .catch((error) => reject(error));
  });

  const importBootstrap = `<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
  </symbol>
  <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
  </symbol>
  <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
  </symbol>
</svg>`;

  cancelation.then((status) => {
    if (status == 201) {
      let messageSuccessfully = `${importBootstrap}
    <div class="alert alert-success d-flex align-items-center" role="alert">
     <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
     <div>Reserva cancelada con éxito</div>
     </div>    
    `;

      document.getElementById("wherePrintResultCancelation").innerHTML =
        messageSuccessfully;
      reservationHistory();
    }
  });
  cancelation.catch(() => {
    let messageAlert = `${importBootstrap}
<div class="alert alert-danger d-flex align-items-center" role="alert">
  <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
  <div>Hubo un problema para cancelar la reservación</div>
</div>
`;

    document.getElementById("wherePrintResultCancelation").innerHTML =
      messageAlert;
  });
}
