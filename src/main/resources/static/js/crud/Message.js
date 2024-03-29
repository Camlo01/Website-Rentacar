function messageData() {
  fetch(`${URLapi}/message/all`)
    .then((response) => response.json())
    .then(function (data) {
      innerMessageData(data);
    })
    .catch((error) => console.log("Problema al mostrar messages: " + error));
}

function innerMessageData(data) {
  let myTable = `<table>`;
  data.forEach((message) => {
    myTable += `<tr>`;
    try {
      myTable += `<td> CARRO: ${message.car.name} </td>`;
      myTable += `<td> CLIENTE: ${message.client.name} </td>`;
    } catch {}
    myTable += `<td> COMENTARIO: ${message.messageText} </td>`;
    myTable += `<td><button onclick="updateMessage(${message.idMessage})"> Actualizar</button></td>`;
    myTable += `<td><button onclick="deleteMessage(${message.idMessage})"> Borrar</button></td>`;
    myTable += `</tr>`;
  });
  myTable += `</table>`;

  document.getElementById(`resultadoMessage`).innerHTML = myTable;
}

function saveMessage() {
  let messageText = document.getElementById("messageText").value;
  let carro = document.getElementById("table-message-select-car").value;
  let cliente = document.getElementById("table-message-select-client").value;

  let idCar = parseInt(getIdOfSelect(carro));
  let idClient = parseInt(getIdOfSelect(cliente));

  fetch(`${URLapi}/car/` + idCar, {
    method: "GET",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then((response) => response.json())
    .then((car) => {
      let carGetted = car;
      fetch(`${URLapi}/client/` + idClient, {
        method: "GET",
        headers: { "Content-type": "application/json; charset=UTF-8" },
      })
        .then((response) => response.json())
        .then((client) => {
          let clientGetted = client;

          let data = {
            messageText: messageText,
            car: carGetted,
            client: clientGetted,
          };
          fetch(`${URLapi}/message/save/`, {
            method: "POST",
            body: JSON.stringify(data),
            headers: { "Content-type": "application/json; charset=UTF-8" },
          })
            .then(function (response) {
              console.log(response);
              if (response.status == 201) {
                console.log("Se creó el mensaje correctamente");
                messageData();
              }
            })
            .catch(function (error) {
              console.log("Problema al crear el mensaje: " + error);
            });
        });
    });
}

function updateMessage(idMessage) {
  let messageText = document.getElementById("messageText").value;
  let data = {
    idMessage: idMessage,
    messageText: messageText,
  };

  fetch(`${URLapi}/message/update`, {
    method: "PUT",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then((response) => {
      console.log(response);
      messageData();
    })
    .catch((error) => {
      console.log(error);
    });
}

function deleteMessage(idMessage) {
  fetch(`${URLapi}/message/delete/` + idMessage, {
    method: "DELETE",
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then((response) => {
      if (response.status == 204) {
        messageData();
      }
    })
    .catch((error) => {
      console.log(error);
    });
}
