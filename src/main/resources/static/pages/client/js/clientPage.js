const urlOfApi = `http://localhost:8080/api/rentacar`;

function awd() {
  let key = {
    keyClient: "D3V3L0PRR",
  };

  const history = new Promise(function (resolve, reject) {
    fetch(`${urlOfApi}/reservation/my-reservation-history`, {
      method: "POST",
      body: JSON.stringify(key),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then((response) => {
        console.log(response.status);
        resolve(response.status);
      })
      .catch((err) => {
        console.log(err);
        reject("Problema");
      });
  });
  history.then((status) => {
    console.log("AWD");
    console.log(status);
  });
}

awd();
// localhost:8080/api/rentacar/test/reservation
// URLapi
// http://localhost:8080/api/rentacar/test/reservation
