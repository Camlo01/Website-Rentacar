function messageData() {
  fetch("http://localhost:8080/api/Message/all")
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
    // myTable += `<td> ${message.car.name} </td>`;
    // myTable += `<td> el Cliente: ${message.client.name} </td>`;
    myTable += `<td> comentó sobre este carro: ${message.messageText} </td>`;
    myTable += `<td><button onclick="updateMessage(${message.idMessage})"> Actualizar</button></td>`;
    myTable += `<td><button onclick="deleteMessage(${message.idMessage})"> Borrar</button></td>`;
    myTable += `</tr>`;
  });
  myTable += `</table>`;

  document.getElementById(`resultadoMessage`).innerHTML = myTable;
}

function saveMessage() {
  let messageText = document.getElementById("messageText").value;
  // let carro = document.getElementById("table-message-select-car").value;
  // let cliente = document.getElementById("table-message-select-client").value;
  let data = {
    messageText: messageText,
    // carro: carro,
    // client: cliente,
  };

  fetch("http://localhost:8080/api/Client/save", {
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
}

function updateMessage(idMessage) {
  let messageText = document.getElementById("messageText").value;

  let data = {
    idMessage: idMessage,
    messageText: messageText,
  };

  fetch("https://localhost:8080/api/Message/update", {
    method: "PUT",
    body: JSON.stringify(data),
    headers: { "Content-type": "application/json; charset=UTF-8" },
  })
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.log(error);
    });
}

function deleteMessage(idMessage) {
  fetch("http://localhost:8080/api/Message/delete/" + idMessage, {
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
